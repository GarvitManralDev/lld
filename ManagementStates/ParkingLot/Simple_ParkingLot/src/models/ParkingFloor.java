package models;

import java.util.List;

public class ParkingFloor {
    private final int floorNumber;
    private final List<ParkingSpot> parkingSpots;

    public ParkingFloor(int floorNumber, List<ParkingSpot> parkingSpots){
        this.floorNumber = floorNumber;
        this.parkingSpots = parkingSpots;
    }

    public ParkingSpot findAvailableSpot(Vehicle vehicle){
        for(ParkingSpot spot : parkingSpots){
            if(spot.isAvailable() && spot.canFitVehicle(vehicle)){
                return spot;
            }
        }
        return null;
    }
}
