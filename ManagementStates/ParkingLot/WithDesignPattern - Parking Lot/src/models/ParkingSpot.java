package models;

import enums.VehicleSize;

public class ParkingSpot {
    private final int spotId;
    private final VehicleSize size;
    private Vehicle parkedVehicle;

    public ParkingSpot(int spotId, VehicleSize size){
        this.spotId = spotId;
        this.size = size;
    }

    public VehicleSize getSize(){
        return size;
    }

    public boolean isAvailable(){
        return parkedVehicle == null;
    }

    public boolean canFitVehicle(Vehicle vehicle){
        return vehicle.getVehicleSize().getSize() <= size.getSize();
    }

    public boolean parkVehicle(Vehicle vehicle){
        if(!isAvailable() || !canFitVehicle(vehicle)){
            return false;
        }
        parkedVehicle = vehicle;
        return true;
    }

    public Vehicle unParkVehicle(){
        Vehicle vehicle = parkedVehicle;
        parkedVehicle = null;
        return vehicle;
    }
}
