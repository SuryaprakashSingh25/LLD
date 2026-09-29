package ParkingLot.Vehicles;

import ParkingLot.enums.VehicleType;

public class Motorcycle extends Vehicle{
    public Motorcycle(String licensePlate){
        super(licensePlate, VehicleType.MOTORCYCLE);
    }
}
