package states;

import entities.VendingMachine;
import enums.Coin;

// Each state decides what an action does right now and returns the next state.
// The VendingMachine is the only class that stores the returned state.
public abstract class VendingMachineState {
    protected final VendingMachine machine;

    protected VendingMachineState(VendingMachine machine) {
        this.machine = machine;
    }

    public abstract VendingMachineState selectItem(String code);
    public abstract VendingMachineState insertCoin(Coin coin);
    public abstract VendingMachineState cancel();
}
