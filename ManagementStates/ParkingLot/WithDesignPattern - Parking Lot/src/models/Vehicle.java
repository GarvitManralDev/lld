package models;

import enums.VehicleSize;

public abstract class Vehicle {
    private final String licensePlate;
    private final VehicleSize size;

    Vehicle(String licensePlate, VehicleSize size){
        this.licensePlate = licensePlate;
        this.size = size;
    }

    public String getLicensePlate(){
        return licensePlate;
    }

    public VehicleSize getVehicleSize(){
        return size;
    }
}
