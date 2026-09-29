package ParkingLot.Price;

public interface PricingStrategy {
    double calculateFee(long durationMs);
}
