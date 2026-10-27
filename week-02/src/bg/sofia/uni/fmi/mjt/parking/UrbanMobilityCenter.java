package bg.sofia.uni.fmi.mjt.parking;

import bg.sofia.uni.fmi.mjt.parking.location.Location;
import bg.sofia.uni.fmi.mjt.parking.sanction.Sanction;
import bg.sofia.uni.fmi.mjt.parking.sanction.TowAway;
import bg.sofia.uni.fmi.mjt.parking.sanction.WheelClamp;
import bg.sofia.uni.fmi.mjt.parking.vehicle.LicensePlate;
import bg.sofia.uni.fmi.mjt.parking.vehicle.Vehicle;
import bg.sofia.uni.fmi.mjt.parking.zone.BlueZone;
import bg.sofia.uni.fmi.mjt.parking.zone.GreenZone;
import bg.sofia.uni.fmi.mjt.parking.zone.ParkingZone;

import java.time.LocalDateTime;
import java.util.Arrays;

public class UrbanMobilityCenter implements ParkingClientAPI, ParkingInspectorAPI {

    private static final int capacity = 10;

    private static final class Payment {
        private final LicensePlate plate;
        private final ParkingZone zone;
        private final LocalDateTime from;
        private final LocalDateTime until;

        private Payment(LicensePlate plate, ParkingZone zone, LocalDateTime from, LocalDateTime until) {
            this.plate = plate;
            this.zone = zone;
            this.from = from;
            this.until = until;
        }

        private boolean covers(LicensePlate plate, ParkingZone zone, LocalDateTime moment) {
            return this.plate.equals(plate) && this.zone == zone
                    && !moment.isBefore(from) && !moment.isAfter(until);
        }
    }

    private final BlueZone blueZone;
    private final GreenZone greenZone;

    private Payment[] payments;
    private int paymentCount;

    public UrbanMobilityCenter(BlueZone blueZone, GreenZone greenZone) {
        this.blueZone = blueZone;
        this.greenZone = greenZone;
        this.payments = new Payment[capacity];
        this.paymentCount = 0;
    }

    @Override
    public boolean sendSms(String smsNumber, String plate, LocalDateTime moment) {
        if (smsNumber == null || plate == null || moment == null) {
            return false;
        }

        ParkingZone zone = findZoneBySmsNumber(smsNumber);
        LicensePlate parsedPlate = LicensePlate.parse(plate);
        if (zone == null || parsedPlate == null) {
            return false;
        }

        if (paymentCount == payments.length) {
            payments = Arrays.copyOf(payments, payments.length * 2);
        }

        // Every SMS pays exactly one hour from its own moment; payments are never merged.
        payments[paymentCount++] = new Payment(parsedPlate, zone, moment, moment.plusHours(1));
        return true;
    }

    @Override
    public Sanction inspect(Vehicle vehicle, Location location, LocalDateTime moment) {
        if (vehicle == null || vehicle.getLicensePlate() == null|| location == null || moment == null) {
            return null;
        }

        ParkingZone zone = findZoneByLocation(location);
        if (zone == null || isPaid(vehicle.getLicensePlate(), zone, moment)) {
            return null;
        }

        if (zone == blueZone) {
            return new TowAway(vehicle, zone);
        }
        return new WheelClamp(vehicle, zone);
    }

    private ParkingZone findZoneBySmsNumber(String smsNumber) {
        if (smsNumber.equals(blueZone.getSmsNumber())) {
            return blueZone;
        }
        if (smsNumber.equals(greenZone.getSmsNumber())) {
            return greenZone;
        }
        return null;
    }

    private ParkingZone findZoneByLocation(Location location) {
        if (blueZone.contains(location)) {
            return blueZone;
        }
        if (greenZone.contains(location)) {
            return greenZone;
        }
        return null;
    }

    private boolean isPaid(LicensePlate plate, ParkingZone zone, LocalDateTime moment) {
        for (int i = 0; i < paymentCount; i++) {
            if (payments[i].covers(plate, zone, moment)) {
                return true;
            }
        }
        return false;
    }
}
