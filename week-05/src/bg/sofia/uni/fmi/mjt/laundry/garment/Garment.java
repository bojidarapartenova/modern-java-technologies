package bg.sofia.uni.fmi.mjt.laundry.garment;

public abstract class Garment {
    private String brand;
    private double weightKg;
    private boolean clean;
    public Garment(String brand, double weightKg) {
        if (brand == null || brand.isBlank()) {
            throw new IllegalArgumentException("Brand cannot be null or blank");
        }

        if (weightKg <= 0) {
            throw new IllegalArgumentException("Weight must be positive");
        }

        this.brand = brand;
        this.weightKg = weightKg;
        this.clean = false;
    }

    /**
     * @return the brand of the garment
     */
    public String getBrand() {
        return this.brand;
    }

    /**
     * @return the weight of the garment in kilograms
     */
    public double getWeightKg() {
        return this.weightKg;
    }

    /**
     * @return true if the garment has been washed, false otherwise. A newly created garment is not clean.
     */
    public boolean isClean() {
        return this.clean;
    }

    /**
     * Marks the garment as clean. Called by the wash programs.
     */
    public void markClean() {
        this.clean = true;
    }

}