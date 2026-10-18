package models;

public class Floor {
    private final int floorNumber;
    private boolean upButtonPressed;
    private boolean downButtonPressed;
    private final FloorDisplay display;         // the screen on this floor

    public Floor(int floorNumber) {
        this.floorNumber = floorNumber;
        this.display = new FloorDisplay(floorNumber);
    }

    public void pressUpButton() {
        upButtonPressed = true;
    }

    public void pressDownButton() {
        downButtonPressed = true;
    }

    // Called when an elevator stops here
    public void resetButtons() {
        upButtonPressed = false;
        downButtonPressed = false;
    }

    public FloorDisplay getDisplay() {
        return display;
    }
}
