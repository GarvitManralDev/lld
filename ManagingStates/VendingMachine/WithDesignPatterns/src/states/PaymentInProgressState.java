package states;

import entities.VendingMachine;
import enums.Coin;

// Item chosen: insert coins or cancel (refund if any money is in)
public class PaymentInProgressState extends VendingMachineState {

    public PaymentInProgressState(VendingMachine machine) {
        super(machine);
    }

    @Override
    public VendingMachineState selectItem(String code) {
        System.out.println("Item already selected");
        return this;
    }

    @Override
    public VendingMachineState insertCoin(Coin coin) {
        int balance = machine.addBalance(coin.getValue());
        System.out.println("Inserted " + coin.getValue() + ", balance: " + balance);

        int price = machine.getSelectedItem().getPrice();
        if (balance >= price) {
            return new DispensingState(machine).dispense();     // paid in full: dispense automatically
        }
        System.out.println("Insert " + (price - balance) + " more");
        return this;
    }

    @Override
    public VendingMachineState cancel() {
        if (machine.getBalance() > 0) {
            machine.refundBalance();
        }
        System.out.println("Cancelled");
        machine.reset();
        return new IdleState(machine);
    }
}
