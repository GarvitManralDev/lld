import core.ATM;
import core.BankService;
import exceptions.ATMException;
import models.Transaction;

void main() {
    ATM atm = ATM.getInstance();

    // Setup bank service with test accounts
    BankService bankService = atm.getBankService();
    bankService.createAccount("ACC001", 5000.00);
    bankService.createAccount("ACC002", 1000.00);
    bankService.createCard("CARD001", "1234", "ACC001");
    bankService.createCard("CARD002", "5678", "ACC002");

    // Scenario 1: Successful withdrawal
    System.out.println("\n========== SCENARIO 1: Withdrawal ==========");
    atm.insertCard("CARD001");
    atm.authenticate("1234");
    System.out.println("Balance: $" + atm.checkBalance());
    System.out.println("Dispensed (170): " + atm.withdraw(170));
    System.out.println("Balance after withdrawal: $" + atm.checkBalance());
    atm.ejectCard();

    // Scenario 2: Deposit
    System.out.println("\n========== SCENARIO 2: Deposit ==========");
    atm.insertCard("CARD002");
    atm.authenticate("5678");
    System.out.println("Balance: $" + atm.checkBalance());
    System.out.println("Deposit 500");
    atm.deposit(500);
    System.out.println("Balance after deposit: $" + atm.checkBalance());
    atm.ejectCard();

    // Scenario 3: Error handling
    System.out.println("\n========== SCENARIO 3: Error Cases ==========");
    try {
        atm.withdraw(100);                       // no card inserted
    } catch (ATMException e) {
        System.out.println("Error: " + e.getMessage());
    }

    atm.insertCard("CARD001");
    try {
        atm.authenticate("0000");                // wrong PIN
    } catch (ATMException e) {
        System.out.println("Error: " + e.getMessage());
    }
    atm.authenticate("1234");

    try {
        atm.withdraw(100000);                    // insufficient funds
    } catch (ATMException e) {
        System.out.println("Error: " + e.getMessage());
    }
    atm.ejectCard();

    System.out.println("\n========== Transaction History (ACC001) ==========");
    for (Transaction transaction : bankService.getTransactionHistory("ACC001")) {
        System.out.println(transaction.getId() + " | " + transaction.getType()
            + " | $" + transaction.getAmount());
    }
}
