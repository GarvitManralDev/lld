package enums;

public enum MachineState {
    IDLE,                   // nothing happening, waiting for a selection
    PAYMENT_IN_PROGRESS,    // item chosen: insert coins or cancel (refund if any money is in)
    DISPENSING              // giving out the item and change
}
