package strategy;

import models.ParkingFloor;
import models.ParkingSpot;
import models.Vehicle;

import java.util.List;

public class BestFitSpotAllocationStrategy implements SpotAllocationStrategy {
    @Override
    public ParkingSpot findSpot(List<ParkingFloor> floors, Vehicle vehicle){
        ParkingSpot bestSpot = null;
        for(ParkingFloor floor : floors){
            for(ParkingSpot spot : floor.getParkingSpots()){
                if(spot.isAvailable() && spot.canFitVehicle(vehicle)
                        && (bestSpot == null || spot.getSize().getSize() < bestSpot.getSize().getSize())){
                    bestSpot = spot;
                }
            }
        }
        return bestSpot;
    }
}
