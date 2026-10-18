package observer;

import enums.Direction;

// Anything that wants to hear when an elevator moves (displays, a dashboard, a logger)
public interface ElevatorObserver {
    void onElevatorStateChanged(int elevatorId, int floor, Direction direction);
}
