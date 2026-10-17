package core;

import enums.Denomination;
import enums.TransactionType;
import exceptions.ATMException;
import states.ATMState;
import states.IdleState;

import java.util.Map;

// Singleton + Facade: one ATM object, the single entry point over the states, bank and cash dispenser
public class ATM {
    private static volatile ATM instance;

    private final BankService bankService;
    private final CashDispenser cashDispenser;
    private ATMState state;

    // Set while a card is in the machine, cleared on eject
    private String currentCardNumber;
    private String currentAccountNumber;

    private ATM(){
        this.bankService = new BankService();
        this.cashDispenser = new CashDispenser();
        this.state = new IdleState();
    }

    // Thread-safe lazy init: only the first caller creates the ATM
    public static ATM getInstance(){
        if(instance == null){
            instance = new ATM();
        }
        return instance;
    }

    public synchronized void insertCard(String cardNumber){
        ATMState next = state.insertCard();        // throws if a card is already inserted
        this.currentCardNumber = cardNumber;
        this.state = next;
    }

    public synchronized void authenticate(String pin){
        ATMState next = state.authenticate();      // throws if there is no card / already logged in
        this.currentAccountNumber = bankService.authenticate(currentCardNumber, pin);   // wrong PIN throws, state stays
        this.state = next;
    }

    public synchronized void deposit(double amount){
        state.transact();
        bankService.credit(currentAccountNumber, amount);
        bankService.recordTransaction(TransactionType.DEPOSIT, amount, currentAccountNumber);
    }

    public synchronized Map<Denomination, Integer> withdraw(int amount){
        state.transact();
        if(amount <= 0){
            throw new ATMException("Amount must be positive");
        }

        bankService.debit(currentAccountNumber, amount);
        try{
            Map<Denomination, Integer> bills = cashDispenser.dispense(amount);
            bankService.recordTransaction(TransactionType.WITHDRAW, amount, currentAccountNumber);
            return bills;
        }catch(ATMException e){
            bankService.credit(currentAccountNumber, amount);    // refund, the cash never came out
            throw e;
        }
    }

    public synchronized double checkBalance(){
        state.transact();
        bankService.recordTransaction(TransactionType.BALANCE_ENQUIRY, 0, currentAccountNumber);
        return bankService.getBalance(currentAccountNumber);
    }

    public synchronized void ejectCard(){
        this.state = state.ejectCard();
        this.currentCardNumber = null;
        this.currentAccountNumber = null;
    }

    // For the demo: set up accounts and cards
    public BankService getBankService(){
        return bankService;
    }
}
