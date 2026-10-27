package bg.sofia.uni.fmi.mjt.parking.sanction;

import bg.sofia.uni.fmi.mjt.parking.vehicle.AbstractVehicle;
import bg.sofia.uni.fmi.mjt.parking.vehicle.Vehicle;
import bg.sofia.uni.fmi.mjt.parking.zone.ParkingZone;

public abstract class AbstractSanction implements Sanction{

    private final Vehicle vehicle;
    private final ParkingZone zone;
    private final int baseAmount;

    protected AbstractSanction(Vehicle vehicle, ParkingZone zone, int baseAmount)
    {
        this.vehicle=vehicle;
        this.zone=zone;
        this.baseAmount=baseAmount;
    }

    @Override
    public Vehicle getVehicle() {
        return vehicle;
    }

    @Override
    public ParkingZone getZone() {
        return zone;
    }

    @Override
    public int getAmount() {
        return baseAmount+vehicle.getSurcharge();
    }
}
