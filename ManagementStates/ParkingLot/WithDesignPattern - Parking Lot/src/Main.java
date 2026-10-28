import enums.VehicleSize;
import exceptions.ParkingException;
import models.*;
import strategy.FCFSSpotAllocationStrategy;
import strategy.HourlyPricingStrategy;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ParkingFloor floor1 = new ParkingFloor(1, List.of(
                new ParkingSpot(101, VehicleSize.SMALL),
                new ParkingSpot(102, VehicleSize.MEDIUM),
                new ParkingSpot(103, VehicleSize.LARGE)
        ));
        ParkingFloor floor2 = new ParkingFloor(2, List.of(
                new ParkingSpot(201, VehicleSize.MEDIUM)
        ));
        ParkingLot parkingLot = ParkingLot.getInstance(List.of(floor1, floor2), new HourlyPricingStrategy(50), new FCFSSpotAllocationStrategy());

        Vehicle bike = new Bike("BIKE-001");
        Vehicle car1 = new Car("CAR-001");
        Vehicle truck1 = new Truck("TRUCK-001");
        Vehicle car2 = new Car("CAR-002");
        Vehicle truck2 = new Truck("TRUCK-002");

        park(parkingLot, bike);
        park(parkingLot, car1);
        park(parkingLot, truck1);
        park(parkingLot, car2);
        park(parkingLot, truck2);

        parkingLot.getTicket("CAR-001");
        System.out.println("Ticket found for CAR-001");

        unPark(parkingLot, "TRUCK-001");
        park(parkingLot, truck2);

        unPark(parkingLot, "CAR-999");
    }

    private static void park(ParkingLot parkingLot, Vehicle vehicle) {
        try {
            parkingLot.parkVehicle(vehicle);
            System.out.println("Parked " + vehicle.getLicensePlate());
        } catch (ParkingException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void unPark(ParkingLot parkingLot, String licensePlate) {
        try {
            parkingLot.unParkVehicle(licensePlate);
            System.out.println("Unparked " + licensePlate);
        } catch (ParkingException e) {
            System.out.println(e.getMessage());
        }
    }
}
