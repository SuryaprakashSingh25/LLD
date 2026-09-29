package ParkingLot;

import ParkingLot.Parking.ParkingFloor;
import ParkingLot.Parking.ParkingSpot;
import ParkingLot.Vehicles.Car;
import ParkingLot.Vehicles.Motorcycle;
import ParkingLot.Vehicles.Vehicle;
import ParkingLot.enums.PaymentType;
import ParkingLot.enums.SpotType;

public class Main {
    public static void main(String[] args) {
        ParkingLot lot=ParkingLot.getInstance("Grand Arena");
        ParkingFloor f1=new ParkingFloor("Floor 1");
        f1.addSpot(new ParkingSpot("F1-M1", SpotType.MOTORCYCLE));
        f1.addSpot(new ParkingSpot("F1-C1",SpotType.COMPACT));
        f1.addSpot(new ParkingSpot("F1-L1", SpotType.LARGE));
        lot.addFloor(f1);

        Vehicle car1 = new Car("KA-01-AB-1234");
        Vehicle moto1 = new Motorcycle("KA-01-XYZ-567");

        Ticket t1=lot.parkVehicle(car1);
        Ticket t2=lot.parkVehicle(moto1);

        lot.checkoutVehicle(t1.getTicketId(), PaymentType.UPI);
        lot.checkoutVehicle(t2.getTicketId(), PaymentType.CARD);
    }
}
