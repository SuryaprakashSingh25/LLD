package VendingMachine.state;

import VendingMachine.VendingMachine;
import VendingMachine.enums.Coin;

public class DispensingState extends VendingMachineState{

    public DispensingState(VendingMachine machine){
        super(machine);
    }

    @Override
    public void insertCoin(Coin coin) {
        System.out.println("Currently dispensing. Please wait.");
    }

    @Override
    public void selectItem(String code) {
        System.out.println("Currently dispensing. Please wait.");
    }

    @Override
    public void dispense() {

    }

    @Override
    public void refund() {
        System.out.println("Dispensing in progress. Refund not allowed.");
    }
}
