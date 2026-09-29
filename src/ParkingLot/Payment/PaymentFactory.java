package ParkingLot.Payment;

import ParkingLot.enums.PaymentType;

public class PaymentFactory {
    public static Payment createPayment(PaymentType type, double amount){
        switch (type){
            case CASH: return new CashPayment(amount);
            case CARD: return new CardPayment(amount);
            case UPI: return new UPIPayment(amount);
            default: throw new IllegalArgumentException("Unknown Payment type");
        }
    }
}
