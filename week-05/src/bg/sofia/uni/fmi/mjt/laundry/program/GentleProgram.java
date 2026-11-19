package bg.sofia.uni.fmi.mjt.laundry.program;

import bg.sofia.uni.fmi.mjt.laundry.garment.Garment;

public class GentleProgram implements WashProgram<Garment> {
    private final int garmenTemperature = 30;
    public GentleProgram() {

    }

    @Override
    public int getTemperature() {
        return garmenTemperature;
    }

    @Override
    public void wash(Garment garment) {
        if (garment == null) {
            throw new IllegalArgumentException("Garment cannot be null");
        }

        garment.markClean();
    }
}
