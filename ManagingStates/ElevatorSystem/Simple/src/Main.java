import core.Elevator;
import core.ElevatorSystem;
import enums.Direction;
import exceptions.ElevatorException;

// Moves the car stop by stop until it has nothing left to do
void serveAllStops(ElevatorSystem system, Elevator elevator) {
    while (elevator.nextStop() != null) {
        system.moveToNextStop(elevator);
        system.handleFloorArrival(elevator);
    }
    System.out.println("Elevator " + elevator.getId() + " is " + elevator.getState() + " at floor " + elevator.getCurrentFloor());
}

void main() {
    ElevatorSystem system = new ElevatorSystem(3, 10);      // 3 elevators, 10 floors, all start at floor 1
    Elevator e1 = system.getElevator(1);
    Elevator e2 = system.getElevator(2);

    // Scenario 1: Hall request, nearest idle car is sent
    System.out.println("========== SCENARIO 1: Hall request ==========");
    system.requestElevator(5, Direction.UP);
    serveAllStops(system, e1);

    // Scenario 2: Cabin requests served in LOOK order (up first, then down)
    System.out.println("\n========== SCENARIO 2: LOOK order ==========");
    system.requestFloor(e1, 8);
    system.requestFloor(e1, 2);
    system.requestFloor(e1, 7);
    serveAllStops(system, e1);                              // 7 -> 8 -> 2

    // Scenario 3: Dispatch picks the closest idle car
    System.out.println("\n========== SCENARIO 3: Dispatch ==========");
    system.requestElevator(1, Direction.UP);                // car 1 is at 2, cars 2 and 3 are at 1
    serveAllStops(system, e2);

    // Scenario 4: Error cases
    System.out.println("\n========== SCENARIO 4: Error cases ==========");
    try {
        system.requestFloor(e1, 11);                        // building has 10 floors
    } catch (ElevatorException e) {
        System.out.println("Error: " + e.getMessage());
    }
    try {
        system.getElevator(4);                              // only 3 elevators: ids 1, 2, 3
    } catch (ElevatorException e) {
        System.out.println("Error: " + e.getMessage());
    }
}
