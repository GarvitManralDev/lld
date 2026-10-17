package states;

import exceptions.ATMException;

public class AuthenticatedState implements ATMState {

    @Override
    public ATMState insertCard() {
        throw new ATMException("A card is already inserted");
    }

    @Override
    public ATMState authenticate() {
        throw new ATMException("Already authenticated");
    }

    @Override
    public ATMState ejectCard() {
        return new IdleState();
    }

    @Override
    public void transact() {
        // allowed: withdraw, deposit and balance enquiry
    }
}
