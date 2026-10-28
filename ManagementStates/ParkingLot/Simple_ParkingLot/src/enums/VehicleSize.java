package enums;

public enum VehicleSize {
    SMALL(0),
    MEDIUM(1),
    LARGE(2);

    private final int size;

    VehicleSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
