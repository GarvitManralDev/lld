package states;

import entities.VendingMachine;
import enums.Coin;

// Nothing happening, waiting for a selection
public class IdleState extends VendingMachineState {

    public IdleState(VendingMachine machine) {
        super(machine);
    }

    @Override
    public VendingMachineState selectItem(String code) {
        if (!machine.isAvailable(code)) {
            System.out.println("Item not available: " + code);
            return this;
        }
        machine.setSelectedItemCode(code);
        System.out.println("Selected: " + machine.getSelectedItem().getName() + " (price " + machine.getSelectedItem().getPrice() + ")");
        return new PaymentInProgressState(machine);
    }

    @Override
    public VendingMachineState insertCoin(Coin coin) {
        System.out.println("Select item first");
        return this;
    }

    @Override
    public VendingMachineState cancel() {
        System.out.println("Nothing to cancel");
        return this;
    }
}
