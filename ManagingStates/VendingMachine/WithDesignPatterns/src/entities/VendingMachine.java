package entities;

import enums.Coin;
import states.IdleState;
import states.VendingMachineState;

// Singleton: one physical machine, one VendingMachine object.
// Context for the State pattern: every action goes to the current state, which returns the next one.
public class VendingMachine {
    private static VendingMachine INSTANCE;

    private final Inventory inventory = new Inventory();
    private VendingMachineState currentState;
    private int balance = 0;
    private String selectedItemCode;

    private VendingMachine() {
        currentState = new IdleState(this);
    }

    // synchronized: two threads can't both see null and create two machines
    public static synchronized VendingMachine getInstance() {
        if(INSTANCE == null){
            INSTANCE = new VendingMachine();
        }
        return INSTANCE;
    }

    // synchronized: one transaction at a time, the machine is locked while an action runs
    public synchronized Item addItem(String code, String name, int price, int quantity) {
        Item item = new Item(code, name, price);
        inventory.addItem(code, item, quantity);
        return item;
    }

    public synchronized void selectItem(String code) {
        currentState = currentState.selectItem(code);
    }

    public synchronized void insertCoin(Coin coin) {
        currentState = currentState.insertCoin(coin);
    }

    public synchronized void cancel() {
        currentState = currentState.cancel();
    }

    public boolean isAvailable(String code) {
        return inventory.isAvailable(code);
    }

    public void setSelectedItemCode(String code) {
        this.selectedItemCode = code;
    }

    public Item getSelectedItem() {
        return inventory.getItem(selectedItemCode);
    }

    public int addBalance(int value) {
        balance += value;
        return balance;
    }

    public int getBalance() {
        return balance;
    }

    public void dispenseItem() {
        Item item = inventory.getItem(selectedItemCode);
        inventory.reduceStock(selectedItemCode);
        balance -= item.getPrice();
        System.out.println("Dispensed: " + item.getName());
        if (balance > 0) {
            System.out.println("Returning change: " + balance);
        }
        reset();
    }

    public void refundBalance() {
        System.out.println("Refunding: " + balance);
        balance = 0;
    }

    // Clears the current purchase
    public void reset() {
        selectedItemCode = null;
        balance = 0;
    }
}
