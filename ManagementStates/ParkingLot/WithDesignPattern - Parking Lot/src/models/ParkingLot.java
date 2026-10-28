package models;

import exceptions.ParkingException;
import strategy.PricingStrategy;
import strategy.SpotAllocationStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ParkingLot {
    private static ParkingLot instance;
    private final List<ParkingFloor> floors;
    private final Map<String, ParkingTicket> activeTickets;
    private final PricingStrategy pricingStrategy;
    private final SpotAllocationStrategy spotAllocationStrategy;

    private ParkingLot(List<ParkingFloor> floors, PricingStrategy pricingStrategy, SpotAllocationStrategy spotAllocationStrategy){
        this.floors = floors;
        this.pricingStrategy = pricingStrategy;
        this.spotAllocationStrategy = spotAllocationStrategy;
        this.activeTickets = new HashMap<>();
    }

    public static synchronized ParkingLot getInstance(List<ParkingFloor> floors, PricingStrategy pricingStrategy, SpotAllocationStrategy spotAllocationStrategy){
        if(instance == null){
            instance = new ParkingLot(floors, pricingStrategy, spotAllocationStrategy);
        }
        return instance;
    }

    public synchronized ParkingTicket parkVehicle(Vehicle vehicle){
        if(activeTickets.containsKey(vehicle.getLicensePlate())){
            throw new ParkingException(vehicle.getLicensePlate() + " is already parked");
        }
        ParkingSpot spot = spotAllocationStrategy.findSpot(floors, vehicle);
        if(spot == null){
            throw new ParkingException("No spot available for " + vehicle.getLicensePlate());
        }
        spot.parkVehicle(vehicle);
        ParkingTicket ticket = new ParkingTicket(UUID.randomUUID().toString(), vehicle, spot);
        activeTickets.put(vehicle.getLicensePlate(), ticket);
        return ticket;
    }

    public synchronized ParkingTicket unParkVehicle(String licensePlate){
        ParkingTicket ticket = activeTickets.remove(licensePlate);
        if(ticket == null){
            throw new ParkingException("No ticket found for " + licensePlate);
        }
        ticket.getSpot().unParkVehicle();
        ticket.setExitTime();
        System.out.println("Fee for " + licensePlate + ": " + pricingStrategy.calculateFee(ticket));
        return ticket;
    }

    public synchronized ParkingTicket getTicket(String licensePlate){
        ParkingTicket ticket = activeTickets.get(licensePlate);
        if(ticket == null){
            throw new ParkingException("No ticket found for " + licensePlate);
        }
        return ticket;
    }
}
