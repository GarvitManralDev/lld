package strategy;

import models.ParkingFloor;
import models.ParkingSpot;
import models.Vehicle;

import java.util.List;

public class FCFSSpotAllocationStrategy implements SpotAllocationStrategy {
    @Override
    public ParkingSpot findSpot(List<ParkingFloor> floors, Vehicle vehicle){
        for(ParkingFloor floor : floors){
            ParkingSpot spot = floor.findAvailableSpot(vehicle);
            if(spot != null){
                return spot;
            }
        }
        return null;
    }
}
