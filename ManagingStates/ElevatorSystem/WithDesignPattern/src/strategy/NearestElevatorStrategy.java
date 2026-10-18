package strategy;

import core.Elevator;
import enums.Direction;
import enums.ElevatorState;
import exceptions.ElevatorException;

import java.util.List;

public class NearestElevatorStrategy implements DispatchStrategy {
    private final int totalFloors;

    public NearestElevatorStrategy(int totalFloors) {
        this.totalFloors = totalFloors;
    }

    @Override
    public Elevator selectElevator(List<Elevator> elevators, int floor, Direction direction) {
        Elevator best = null;
        int bestScore = -1;
        for (Elevator elevator : elevators) {
            if (elevator.getState() == ElevatorState.OUT_OF_SERVICE) {
                continue;
            }
            int score = score(elevator, floor, direction);
            if (score > bestScore) {
                best = elevator;
                bestScore = score;
            }
        }
        if (best == null) {
            throw new ElevatorException("No elevator available");
        }
        return best;
    }

    // Helper function for Selecting Optimal Elevator
    private int score(Elevator elevator, int floor, Direction direction) {
        int currentFloor = elevator.getCurrentFloor();
        int distance = Math.abs(currentFloor - floor);

        // 1. Idle elevator: closer is better
        if (elevator.getDirection() == Direction.IDLE) {
            return totalFloors - distance;
        }

        // 2. Check if the elevator is moving in the requested direction
        boolean sameDirection = elevator.getDirection() == direction;

        boolean onTheWay = false;

        if (direction == Direction.UP && currentFloor <= floor) {
            onTheWay = true;
        } else if (direction == Direction.DOWN && currentFloor >= floor) {
            onTheWay = true;
        }

        // 3. Prefer an elevator already going toward the requested floor
        if (sameDirection && onTheWay) {
            return totalFloors - distance + 1;
        }

        // 4. Otherwise, this elevator is a less suitable choice
        return 1;
    }
}
