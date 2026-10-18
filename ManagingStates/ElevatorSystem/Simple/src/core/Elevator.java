package core;

import enums.Direction;
import enums.ElevatorState;
import exceptions.ElevatorException;
import models.Display;

import java.util.TreeSet;

public class Elevator {
    private final int id;
    private final int totalFloors;
    private int currentFloor = 1;
    private Direction direction = Direction.IDLE;
    private ElevatorState state = ElevatorState.IDLE;
    private final TreeSet<Integer> upRequests = new TreeSet<>();
    private final TreeSet<Integer> downRequests = new TreeSet<>();
    private final Display display;                 // the screen inside this car

    public Elevator(int id, int totalFloors) {
        this.id = id;
        this.totalFloors = totalFloors;
        this.display = new Display("Cabin " + id);
    }

    public void addStop(int floor) {
        if (floor < 1 || floor > totalFloors) {
            throw new ElevatorException("Invalid floor: " + floor);
        }
        if (floor > currentFloor) {
            upRequests.add(floor);
        } else if (floor < currentFloor) {
            downRequests.add(floor);
        } else if (direction == Direction.DOWN) {
            downRequests.add(floor);           // already here: served on the next check
        } else {
            upRequests.add(floor);
        }
    }

    // After the last stop is served, the car is idle again
    public void removeStop(int floor) {
        upRequests.remove(floor);
        downRequests.remove(floor);
        if (nextStop() == null) {
            state = ElevatorState.IDLE;
            direction = Direction.IDLE;
        }
    }

    public Integer nextStop() {
        if (upRequests.contains(currentFloor) || downRequests.contains(currentFloor)) {
            return currentFloor;                // a stop right here (higher/lower would skip it)
        }

        Integer next;
        if (direction == Direction.DOWN) {
            next = downRequests.lower(currentFloor);                  // next stop below
            if (next == null) next = upRequests.higher(currentFloor); // nothing below: turn around
        } else {
            next = upRequests.higher(currentFloor);                   // next stop above
            if (next == null) next = downRequests.lower(currentFloor);// nothing above: turn around
        }

        return next;                            // null = nothing to do
    }

    public void moveTo(int floor) {
        direction = floor > currentFloor ? Direction.UP : Direction.DOWN;
        state = floor > currentFloor ? ElevatorState.MOVING_UP : ElevatorState.MOVING_DOWN;
        currentFloor = floor;
        display.update(id, currentFloor, direction);
    }


    public int getId() {
        return id;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    public ElevatorState getState() {
        return state;
    }
}
