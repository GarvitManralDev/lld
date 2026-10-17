package core;

import enums.TransactionType;
import exceptions.ATMException;
import models.Account;
import models.Card;
import models.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BankService {
    // Why maps: O(1) average lookup of the right object from an ID (card number / account number)
    // instead of an O(n) search through a list.

    // key: account number -> value: that Account (holds the balance)
    private final Map<String, Account> accounts;
    // key: card number -> value: that Card (holds the PIN and its linked account number)
    private final Map<String, Card> cards;
    // key: account number -> value: list of all transactions on that account
    private final Map<String, List<Transaction>> transactionHistory;
    private int transactionCounter = 0;

    public BankService() {
        this.accounts = new ConcurrentHashMap<>();
        this.cards = new ConcurrentHashMap<>();
        this.transactionHistory = new ConcurrentHashMap<>();
    }

    public void createAccount(String accountNumber, double initialBalance) {
        accounts.put(accountNumber, new Account(accountNumber, initialBalance));
        transactionHistory.put(accountNumber, new ArrayList<>());
    }

    public void createCard(String cardNumber, String pin, String accountNumber) {
        if (!accounts.containsKey(accountNumber)) {
            throw new ATMException("Account " + accountNumber + " does not exist");
        }
        cards.put(cardNumber, new Card(cardNumber, pin, accountNumber));
    }

    // Returns only the account number: Account objects never leave the bank
    public String authenticate(String cardNumber, String pin) {
        Card card = cards.get(cardNumber);
        if (card == null) {
            throw new ATMException("Card not recognized");
        }
        if (!card.getPin().equals(pin)) {
            throw new ATMException("Incorrect PIN");
        }
        if (!accounts.containsKey(card.getAccountNumber())) {
            throw new ATMException("Account not found for card " + cardNumber);
        }
        return card.getAccountNumber();
    }

    public double getBalance(String accountNumber) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            throw new ATMException("Account " + accountNumber + " not found");
        }
        return account.getBalance();
    }

    public void debit(String accountNumber, double amount) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            throw new ATMException("Account " + accountNumber + " not found");
        }
        account.debit(amount);
    }

    public void credit(String accountNumber, double amount) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            throw new ATMException("Account " + accountNumber + " not found");
        }
        account.credit(amount);
    }

    public synchronized void recordTransaction(TransactionType type, double amount,
                                                      String accountNumber) {
        String id = "TXN" + (++transactionCounter);
        Transaction transaction = new Transaction(id, type, amount, accountNumber);
        transactionHistory.get(accountNumber).add(transaction);
    }

    public List<Transaction> getTransactionHistory(String accountNumber) {
        return transactionHistory.get(accountNumber);
    }
}
