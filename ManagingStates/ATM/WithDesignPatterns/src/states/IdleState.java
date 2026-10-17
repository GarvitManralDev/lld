package states;

import exceptions.ATMException;

public class IdleState implements ATMState {

    @Override
    public ATMState insertCard() {
        return new CardInsertedState();
    }

    @Override
    public ATMState authenticate() {
        throw new ATMException("Insert a card first");
    }

    @Override
    public ATMState ejectCard() {
        return this;                  // no card, nothing to eject
    }

    @Override
    public void transact() {
        throw new ATMException("Insert a card first");
    }
}
