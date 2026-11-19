package bg.sofia.uni.fmi.mjt.laundry.program;

import bg.sofia.uni.fmi.mjt.laundry.garment.Wool;

public class WoolProgram implements WashProgram<Wool> {
    private final int woolTemperature = 40;
    public WoolProgram() {

    }

    @Override
    public int getTemperature() {
        return woolTemperature;
    }

    @Override
    public void wash(Wool garment) {
        if (garment == null) {
            throw new IllegalArgumentException("Wool cannot be null");
        }

        garment.markClean();
    }
}
