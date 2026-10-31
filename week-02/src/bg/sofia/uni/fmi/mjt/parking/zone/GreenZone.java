package bg.sofia.uni.fmi.mjt.parking.zone;

import bg.sofia.uni.fmi.mjt.parking.location.Location;

public class GreenZone extends AbstractZone {

    private final double innerRadius;
    private final double outerRadius;

    public GreenZone(String name, Location center, double innerRadius, double outerRadius)
    {
        super(name, center);
        this.innerRadius=innerRadius;
        this.outerRadius=outerRadius;
    }

    @Override
    public String getSmsNumber() {
        return "1303";
    }

    @Override
    public boolean contains(Location location)
    {
        if(location == null)
        {
            return false;
        }

        double distance = Math.sqrt(Math.pow((location.x()-center.x()), 2) + Math.pow((location.y()-center.y()), 2));

        return distance>innerRadius && distance<=outerRadius;
    }
}
