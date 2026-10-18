package core;

import enums.Direction;
import enums.ElevatorState;
import exceptions.ElevatorException;
import models.Floor;

import java.util.ArrayList;
import java.util.List;

// The building: owns every elevator and floor, and decides which car takes each hall request
public class ElevatorSystem {
    private final List<Elevator> elevators = new ArrayList<>();
    private final List<Floor> floors = new ArrayList<>();
    private final int totalFloors;

    public ElevatorSystem(int numElevators, int numFloors) {
        this.totalFloors = numFloors;

        for (int number = 1; number <= numFloors; number++) {
            floors.add(new Floor(number));
        }
        for (int id = 1; id <= numElevators; id++) {         // numbered from 1, like the floors
            elevators.add(new Elevator(id, numFloors));
        }
    }

    // Floor button Pressed
    public void requestElevator(int floor, Direction direction) {
        if (floor < 1 || floor > totalFloors) {
            throw new ElevatorException("Invalid floor: " + floor);
        }
        Floor hallFloor = floors.get(floor - 1);
        if (direction == Direction.UP) {
            hallFloor.pressUpButton();
        } else {
            hallFloor.pressDownButton();
        }

        Elevator elevator = selectOptimalElevator(floor, direction);
        elevator.addStop(floor);
        System.out.println("Floor " + floor + " " + direction + " -> Elevator " + elevator.getId());
    }

    // Cabin button Pressed: the car is already known
    public void requestFloor(Elevator elevator, int floor) {
        if (elevator.getState() == ElevatorState.OUT_OF_SERVICE) {
            throw new ElevatorException("Elevator " + elevator.getId() + " is out of service");
        }
        elevator.addStop(floor);
    }

    // Elevator moving to next stop
    public void moveToNextStop(Elevator elevator){
        Integer nextStop = elevator.nextStop();
        if (nextStop == null || nextStop == elevator.getCurrentFloor()) {
            return;                             // no stops, or already at the stop: nothing to move
        }
        elevator.moveTo(nextStop);
    }

    // Elevator arrived to a destination
    public void handleFloorArrival(Elevator elevator) {
        int floor = elevator.getCurrentFloor();
        System.out.println("Elevator " + elevator.getId() + " stopped at floor " + floor);
        elevator.removeStop(floor);
        floors.get(floor - 1).resetButtons();
    }

    public Elevator getElevator(int elevatorId) {
        if (elevatorId < 1 || elevatorId > elevators.size()) {
            throw new ElevatorException("Invalid elevator: " + elevatorId);
        }
        return elevators.get(elevatorId - 1);
    }

    private Elevator selectOptimalElevator(int floor, Direction direction) {
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
