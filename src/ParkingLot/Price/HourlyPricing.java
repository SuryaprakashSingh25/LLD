package ParkingLot.Price;

public class HourlyPricing implements PricingStrategy{

    private final double hourlyRate;

    public HourlyPricing(double rate){
        this.hourlyRate=rate;
    }

    @Override
    public double calculateFee(long durationMs) {
        double hours=Math.ceil(durationMs/3600000.0);
        return Math.max(1.0,hours)*hourlyRate;
    }
}
