package strategy;

import core.Elevator;
import enums.Direction;

import java.util.List;

// Decides which car takes a hall request; a new algorithm is a new class
public interface DispatchStrategy {
    Elevator selectElevator(List<Elevator> elevators, int floor, Direction direction);
}
