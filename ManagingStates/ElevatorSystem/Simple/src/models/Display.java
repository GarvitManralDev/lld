package models;

import enums.Direction;

// A screen in a cabin or on a floor, showing where an elevator is
public class Display {
    private final String location;      // where the screen is mounted, e.g. "Cabin 1", "Floor 3"

    public Display(String location) {
        this.location = location;
    }

    public void update(int elevatorId, int floor, Direction direction) {
        System.out.println("[" + location + "] Elevator " + elevatorId + " at floor " + floor + " going " + direction);
    }
}
