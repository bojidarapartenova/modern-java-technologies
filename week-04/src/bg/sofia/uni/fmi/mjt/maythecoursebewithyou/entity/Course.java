package bg.sofia.uni.fmi.mjt.maythecoursebewithyou.entity;

public record Course(String code, String title, Department department, int capacity) {
    public Course {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Code cannot be null or blank");
        }

        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or blank");
        }

        if (department == null) {
            throw new IllegalArgumentException("Department cannot be null");
        }

        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be a positive number");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Course other)) {
            return false;
        }

        return code.equals(other.code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}
