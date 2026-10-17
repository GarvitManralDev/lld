package states;

import entities.VendingMachine;
import enums.Coin;

// Giving out the item and change: every action is blocked until it's done
public class DispensingState extends VendingMachineState {

    public DispensingState(VendingMachine machine) {
        super(machine);
    }

    // Hands over the item and change, then the machine goes back to Idle
    public VendingMachineState dispense() {
        machine.dispenseItem();
        return new IdleState(machine);
    }

    @Override
    public VendingMachineState selectItem(String code) {
        System.out.println("Please wait, dispensing");
        return this;
    }

    @Override
    public VendingMachineState insertCoin(Coin coin) {
        System.out.println("Please wait, dispensing");
        return this;
    }

    @Override
    public VendingMachineState cancel() {
        System.out.println("Please wait, dispensing");
        return this;
    }
}
