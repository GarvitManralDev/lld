package states;

import exceptions.ATMException;

public class CardInsertedState implements ATMState {

    @Override
    public ATMState insertCard() {
        throw new ATMException("A card is already inserted");
    }

    @Override
    public ATMState authenticate() {
        return new AuthenticatedState();
    }

    @Override
    public ATMState ejectCard() {
        return new IdleState();
    }

    @Override
    public void transact() {
        throw new ATMException("Enter your PIN first");
    }
}
