package states;

// Each state decides what is allowed right now and which state comes next.
// The ATM is the only class that stores the returned state.
public interface ATMState {
    ATMState insertCard();        // returns the next state, or throws if not allowed now
    ATMState authenticate();
    ATMState ejectCard();
    void transact();              // throws unless withdraw / deposit / balance are allowed now
}
