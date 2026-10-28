package models;

import java.time.LocalDateTime;

public class ParkingTicket {
    private final String ticketId;
    private final Vehicle vehicle;
    private final ParkingSpot spot;
    private final LocalDateTime entryTime;
    private LocalDateTime exitTime;

    public ParkingTicket(String ticketId, Vehicle vehicle, ParkingSpot spot){
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.spot = spot;
        this.entryTime = LocalDateTime.now();
    }

    public LocalDateTime getEntryTime(){
        return entryTime;
    }

    public LocalDateTime getExitTime(){
        return exitTime;
    }

    public Vehicle getVehicle(){
        return vehicle;
    }

    public ParkingSpot getSpot(){
        return spot;
    }

    public void setExitTime(){
        this.exitTime = LocalDateTime.now();
    }
}
