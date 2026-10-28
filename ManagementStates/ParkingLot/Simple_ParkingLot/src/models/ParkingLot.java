package models;

import exceptions.ParkingException;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ParkingLot {
    private final List<ParkingFloor> floors;
    private final Map<String, ParkingTicket> activeTickets;
    private final double hourlyRate;

    public ParkingLot(List<ParkingFloor> floors, double hourlyRate){
        this.floors = floors;
        this.hourlyRate = hourlyRate;
        this.activeTickets = new HashMap<>();
    }

    public ParkingTicket parkVehicle(Vehicle vehicle){
        if(activeTickets.containsKey(vehicle.getLicensePlate())){
            throw new ParkingException(vehicle.getLicensePlate() + " is already parked");
        }
        for(ParkingFloor floor : floors){
            ParkingSpot spot = floor.findAvailableSpot(vehicle);
            if(spot != null){
                spot.parkVehicle(vehicle);
                ParkingTicket ticket = new ParkingTicket(UUID.randomUUID().toString(), vehicle, spot);
                activeTickets.put(vehicle.getLicensePlate(), ticket);
                return ticket;
            }
        }
        throw new ParkingException("No spot available for " + vehicle.getLicensePlate());
    }

    public ParkingTicket unParkVehicle(String licensePlate){
        ParkingTicket ticket = activeTickets.remove(licensePlate);
        if(ticket == null){
            throw new ParkingException("No ticket found for " + licensePlate);
        }
        ticket.getSpot().unParkVehicle();
        ticket.setExitTime();
        System.out.println("Fee for " + licensePlate + ": " + calculateFee(ticket));
        return ticket;
    }

    private double calculateFee(ParkingTicket ticket){
        long minutes = Duration.between(ticket.getEntryTime(), ticket.getExitTime()).toMinutes();
        long hours = Math.max(1, (minutes + 59) / 60);
        return hours * hourlyRate;
    }

    public ParkingTicket getTicket(String licensePlate){
        ParkingTicket ticket = activeTickets.get(licensePlate);
        if(ticket == null){
            throw new ParkingException("No ticket found for " + licensePlate);
        }
        return ticket;
    }
}
