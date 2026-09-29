package ParkingLot.Payment;

public class UPIPayment extends Payment{
    public UPIPayment(double amount) { super(amount); }
    @Override
    public boolean process() {
        System.out.printf("[UPI] Processed payment of $%.2f via transaction: %s\n", amount, getTransactionId());
        return true;
    }
}
