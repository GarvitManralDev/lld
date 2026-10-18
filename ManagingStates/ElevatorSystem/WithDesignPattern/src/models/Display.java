package models;

import enums.Direction;
import observer.ElevatorObserver;

// A screen in a cabin or on a floor; it is told about moves, the elevator never knows it exists
public class Display implements ElevatorObserver {
    private final String location;      // where the screen is mounted, e.g. "Cabin 1", "Floor 3"

    public Display(String location) {
        this.location = location;
    }

    @Override
    public void onElevatorStateChanged(int elevatorId, int floor, Direction direction) {
        System.out.println("[" + location + "] Elevator " + elevatorId + " at floor " + floor + " going " + direction);
    }
}
