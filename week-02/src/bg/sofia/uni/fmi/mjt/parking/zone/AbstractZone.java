package bg.sofia.uni.fmi.mjt.parking.zone;

import bg.sofia.uni.fmi.mjt.parking.location.Location;

public abstract class AbstractZone implements ParkingZone{

    private final String name;
    protected final Location center;

    protected AbstractZone(String name, Location center)
    {
        this.name=name;
        this.center=center;
    }

    @Override
    public String getName() {
        return name;
    }
}
