package strategy;

import models.ParkingFloor;
import models.ParkingSpot;
import models.Vehicle;

import java.util.List;

public interface SpotAllocationStrategy {
    ParkingSpot findSpot(List<ParkingFloor> floors, Vehicle vehicle);
}
