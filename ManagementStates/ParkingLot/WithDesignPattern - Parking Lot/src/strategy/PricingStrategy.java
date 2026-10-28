package strategy;

import models.ParkingTicket;

public interface PricingStrategy {
    double calculateFee(ParkingTicket ticket);
}
