package models;

public class Floor {
    private final int floorNumber;
    private boolean upButtonPressed;
    private boolean downButtonPressed;

    public Floor(int floorNumber) {
        this.floorNumber = floorNumber;
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

}
