package core;

import enums.ATMState;
import enums.Denomination;
import enums.TransactionType;
import exceptions.ATMException;

import java.util.Map;

public class ATM {
    private final BankService bankService;
    private final CashDispenser cashDispenser;
    private ATMState currentState;

    // Set while a card is in the machine, cleared on eject
    private String currentCardNumber;
    private String currentAccountNumber;

    public ATM(){
        this.bankService = new BankService();
        this.cashDispenser = new CashDispenser();
        this.currentState = ATMState.IDLE;
    }

    public synchronized void insertCard(String cardNumber){
        ensureState(ATMState.IDLE, "A card is already inserted");
        this.currentCardNumber = cardNumber;
        this.currentState = ATMState.CARD_INSERTED;
    }

    public synchronized void authenticate(String pin){
        ensureState(ATMState.CARD_INSERTED, "Insert a card first");
        this.currentAccountNumber = bankService.authenticate(currentCardNumber, pin);
        this.currentState = ATMState.AUTHENTICATED;
    }

    public synchronized void deposit(double amount){
        ensureState(ATMState.AUTHENTICATED, "Enter your PIN first");
        bankService.credit(currentAccountNumber, amount);
        bankService.recordTransaction(TransactionType.DEPOSIT, amount, currentAccountNumber);
    }

    public synchronized Map<Denomination, Integer> withdraw(int amount){
        ensureState(ATMState.AUTHENTICATED, "Enter your PIN first");
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
        ensureState(ATMState.AUTHENTICATED, "Enter your PIN first");
        bankService.recordTransaction(TransactionType.BALANCE_ENQUIRY, 0, currentAccountNumber);
        return bankService.getBalance(currentAccountNumber);
    }

    public synchronized void ejectCard(){
        this.currentCardNumber = null;
        this.currentAccountNumber = null;
        this.currentState = ATMState.IDLE;
    }

    // For the demo: set up accounts and cards
    public BankService getBankService(){
        return bankService;
    }

    // Throws if the ATM isn't in the state this action needs
    private void ensureState(ATMState expected, String message){
        if(currentState != expected){
            throw new ATMException(message);
        }
    }
}
