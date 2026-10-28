package strategy;

import enums.VehicleSize;
import models.ParkingTicket;

import java.time.Duration;
import java.util.Map;

public class VehicleSizePricingStrategy implements PricingStrategy {
    private final Map<VehicleSize, Double> hourlyRates;

    public VehicleSizePricingStrategy(Map<VehicleSize, Double> hourlyRates){
        this.hourlyRates = hourlyRates;
    }

    @Override
    public double calculateFee(ParkingTicket ticket){
        long minutes = Duration.between(ticket.getEntryTime(), ticket.getExitTime()).toMinutes();
        long hours = Math.max(1, (minutes + 59) / 60);
        return hours * hourlyRates.get(ticket.getVehicle().getVehicleSize());
    }
}
