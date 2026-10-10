package ParkingLot.strategy.fee;

import ParkingLot.entities.ParkingTicket;

public interface FeeStrategy {
    double calculateFee(ParkingTicket parkingTicket);
}
