package MovieBooking.Payment;

import MovieBooking.enums.SeatCategory;

public class DynamicPricingStrategy implements PricingStrategy{

    @Override
    public double calculatePrice(SeatCategory category, boolean isWeekend) {
        double base=switch (category){
            case SILVER -> 100.0;
            case GOLD -> 150.0;
            case VIP -> 250.0;
        };
        return isWeekend?base*1.25:base;
    }
}
