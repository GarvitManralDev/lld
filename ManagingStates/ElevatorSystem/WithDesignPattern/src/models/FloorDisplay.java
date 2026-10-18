package models;

import enums.Direction;
import observer.ElevatorObserver;

import java.util.Map;
import java.util.TreeMap;

// The screen on a floor: listens to every elevator and shows all of them on one line
public class FloorDisplay implements ElevatorObserver {
    private final int floorNumber;
    private final Map<Integer, String> positions = new TreeMap<>();     // elevatorId -> "5↑", sorted by id

    public FloorDisplay(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    @Override
    public void onElevatorStateChanged(int elevatorId, int floor, Direction direction) {
        positions.put(elevatorId, floor + arrow(direction));
        StringBuilder line = new StringBuilder("[Floor " + floorNumber + "]");
        for (Map.Entry<Integer, String> entry : positions.entrySet()) {
            line.append(" E").append(entry.getKey()).append(":").append(entry.getValue());
        }
        System.out.println(line);
    }

    private String arrow(Direction direction) {
        if (direction == Direction.UP) return "↑";
        if (direction == Direction.DOWN) return "↓";
        return "";
    }
}
