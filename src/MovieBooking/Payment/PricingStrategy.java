package MovieBooking.Payment;

import MovieBooking.enums.SeatCategory;

public interface PricingStrategy {
    double calculatePrice(SeatCategory category, boolean isWeekend);
}
