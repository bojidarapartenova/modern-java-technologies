package bg.sofia.uni.fmi.mjt.parking.zone;

import bg.sofia.uni.fmi.mjt.parking.location.Location;

public class BlueZone extends AbstractZone{

    private final double radius;

    public BlueZone(String name, Location center, double radius)
    {
        super(name, center);
        this.radius=radius;
    }


    @Override
    public String getSmsNumber() {
        return "1302";
    }

    @Override
    public boolean contains(Location location)
    {
        if(location == null)
        {
            return false;
        }

        double distance = Math.sqrt(Math.pow((location.getX()-center.getX()), 2) + Math.pow((location.getY()-center.getY()), 2));

        return distance<=radius;
    }
}
