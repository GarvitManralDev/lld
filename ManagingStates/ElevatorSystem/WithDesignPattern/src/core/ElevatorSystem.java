package core;

import enums.Direction;
import enums.ElevatorState;
import exceptions.ElevatorException;
import models.Display;
import models.Floor;
import strategy.DispatchStrategy;
import strategy.NearestElevatorStrategy;

import java.util.ArrayList;
import java.util.List;

// The building: owns every elevator and floor, and decides which car takes each hall request
public class ElevatorSystem {
    private final List<Elevator> elevators = new ArrayList<>();
    private final List<Floor> floors = new ArrayList<>();
    private final int totalFloors;
    private DispatchStrategy dispatchStrategy;

    public ElevatorSystem(int numElevators, int numFloors) {
        this.totalFloors = numFloors;

        for (int number = 1; number <= numFloors; number++) {
            floors.add(new Floor(number));
        }
        for (int id = 1; id <= numElevators; id++) {         // numbered from 1, like the floors
            Elevator elevator = new Elevator(id, numFloors);
            elevator.addObserver(new Display("Cabin " + id));  // the system wires displays, not the elevator
            for (Floor floor : floors) {
                elevator.addObserver(floor.getDisplay());      // every floor screen follows every car
            }
            elevators.add(elevator);
        }
        this.dispatchStrategy = new NearestElevatorStrategy(numFloors);
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

        Elevator elevator = dispatchStrategy.selectElevator(elevators, floor, direction);
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

    // Swap the dispatch algorithm at runtime
    public void setDispatchStrategy(DispatchStrategy dispatchStrategy) {
        this.dispatchStrategy = dispatchStrategy;
    }

    public Elevator getElevator(int elevatorId) {
        if (elevatorId < 1 || elevatorId > elevators.size()) {
            throw new ElevatorException("Invalid elevator: " + elevatorId);
        }
        return elevators.get(elevatorId - 1);
    }
}
