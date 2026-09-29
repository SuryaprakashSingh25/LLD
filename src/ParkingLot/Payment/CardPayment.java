package ParkingLot.Payment;

public class CardPayment extends Payment{
    public CardPayment(double amount) { super(amount); }
    @Override
    public boolean process() {
        System.out.printf("[Card] Processed payment of $%.2f via transaction: %s\n", amount, getTransactionId());
        return true;
    }
}
