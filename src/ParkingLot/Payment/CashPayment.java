package ParkingLot.Payment;

public class CashPayment extends Payment{
    public CashPayment(double amount){
        super(amount);
    }

    @Override
    public boolean process() {
        System.out.printf("[Cash] Processed payment of $%.2f via transaction: %s\n", amount, getTransactionId());
        return true;
    }
}
