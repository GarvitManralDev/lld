package entities;

import enums.Coin;
import enums.MachineState;

public class VendingMachine {
    private final Inventory inventory = new Inventory();
    private MachineState currentState;
    private int balance = 0;
    private String selectedItemCode;

    public VendingMachine() {
        currentState = MachineState.IDLE;
    }

    public Item addItem(String code, String name, int price, int quantity) {
        Item item = new Item(code, name, price);
        inventory.addItem(code, item, quantity);
        return item;
    }

    public void selectItem(String code) {
        if (currentState != MachineState.IDLE) {
            System.out.println("Item already selected");
            return;
        }
        if (!inventory.isAvailable(code)) {
            System.out.println("Item not available: " + code);
            return;
        }
        selectedItemCode = code;
        currentState = MachineState.PAYMENT_IN_PROGRESS;
        System.out.println("Selected: " + getSelectedItem().getName() + " (price " + getSelectedItem().getPrice() + ")");
    }

    public void insertCoin(Coin coin) {
        if (currentState == MachineState.IDLE) {
            System.out.println("Select item first");
            return;
        }
        if (currentState == MachineState.DISPENSING) {
            System.out.println("Please wait, dispensing");
            return;
        }

        balance += coin.getValue();
        System.out.println("Inserted " + coin.getValue() + ", balance: " + balance);

        int price = getSelectedItem().getPrice();
        if (balance >= price) {
            currentState = MachineState.DISPENSING;     // paid in full: dispense automatically
            dispenseItem();
        } else {
            System.out.println("Insert " + (price - balance) + " more");
        }
    }

    // One cancel button: refunds the money if some was inserted, then clears the selection
    public void cancel() {
        if (currentState == MachineState.IDLE) {
            System.out.println("Nothing to cancel");
            return;
        }
        if (currentState == MachineState.DISPENSING) {
            System.out.println("Please wait, dispensing");
            return;
        }
        if (balance > 0) {
            refundBalance();
        }
        System.out.println("Cancelled");
        reset();
    }

    private void dispenseItem() {
        Item item = inventory.getItem(selectedItemCode);
        inventory.reduceStock(selectedItemCode);
        balance -= item.getPrice();
        System.out.println("Dispensed: " + item.getName());
        if (balance > 0) {
            System.out.println("Returning change: " + balance);
        }
        reset();
    }

    private void refundBalance() {
        System.out.println("Refunding: " + balance);
        balance = 0;
    }

    // Clears the current purchase and goes back to IDLE
    private void reset() {
        selectedItemCode = null;
        balance = 0;
        currentState = MachineState.IDLE;
    }

    public Item getSelectedItem() {
        return inventory.getItem(selectedItemCode);
    }
}
