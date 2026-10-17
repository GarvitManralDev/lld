import entities.VendingMachine;
import enums.Coin;

void main() {
    VendingMachine machine = new VendingMachine();

    // Setup: code, name, price (cents), quantity
    machine.addItem("A1", "Coke", 35, 2);
    machine.addItem("B1", "Chips", 50, 1);
    machine.addItem("C1", "Gum", 25, 5);

    // Scenario 1: Exact payment
    System.out.println("========== SCENARIO 1: Exact payment ==========");
    machine.selectItem("C1");
    machine.insertCoin(Coin.QUARTER);              // 25 = price -> dispensed

    // Scenario 2: Overpayment, change returned
    System.out.println("\n========== SCENARIO 2: Change returned ==========");
    machine.selectItem("A1");
    machine.insertCoin(Coin.QUARTER);              // 25, insert 10 more
    machine.insertCoin(Coin.QUARTER);              // 50 -> dispensed, change 15

    // Scenario 3: Cancel midway, full refund
    System.out.println("\n========== SCENARIO 3: Cancel with refund ==========");
    machine.selectItem("B1");
    machine.insertCoin(Coin.QUARTER);
    machine.insertCoin(Coin.DIME);
    machine.cancel();                              // refunds 35

    // Scenario 4: Error cases
    System.out.println("\n========== SCENARIO 4: Error cases ==========");
    machine.insertCoin(Coin.QUARTER);              // no item selected
    machine.cancel();                              // nothing to cancel
    machine.selectItem("Z9");                      // unknown item
    machine.selectItem("A1");
    machine.selectItem("C1");                      // item already selected
    machine.cancel();                              // cancel with no money in

    // Scenario 5: Out of stock
    System.out.println("\n========== SCENARIO 5: Out of stock ==========");
    machine.selectItem("A1");
    machine.insertCoin(Coin.QUARTER);
    machine.insertCoin(Coin.DIME);                 // 35 -> last Coke dispensed
    machine.selectItem("A1");                      // now sold out
}
