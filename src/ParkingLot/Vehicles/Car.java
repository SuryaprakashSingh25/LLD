package ParkingLot.Vehicles;

import ParkingLot.enums.VehicleType;

public class Car extends Vehicle{
    public Car(String licensePlate){
        super(licensePlate, VehicleType.CAR);
    }
}
