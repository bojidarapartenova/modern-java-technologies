package bg.sofia.uni.fmi.mjt.parking.sanction;

import bg.sofia.uni.fmi.mjt.parking.vehicle.Vehicle;
import bg.sofia.uni.fmi.mjt.parking.zone.ParkingZone;

public class WheelClamp extends AbstractSanction {

    public static final int SANCTION_TAX = 15;
    public WheelClamp(Vehicle vehicle, ParkingZone zone)
    {
        super(vehicle, zone, SANCTION_TAX);
    }
}
