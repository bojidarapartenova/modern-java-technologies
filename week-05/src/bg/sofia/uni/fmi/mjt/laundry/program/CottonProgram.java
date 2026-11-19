package bg.sofia.uni.fmi.mjt.laundry.program;

import bg.sofia.uni.fmi.mjt.laundry.garment.Cotton;

public class CottonProgram implements WashProgram<Cotton> {
    private final int cottonTemperature = 90;
    public CottonProgram() {

    }

    @Override
    public int getTemperature() {
        return cottonTemperature;
    }

    @Override
    public void wash(Cotton garment) {
        if (garment == null) {
            throw new IllegalArgumentException("Cotton cannot be null");
        }

        garment.markClean();
    }
}
