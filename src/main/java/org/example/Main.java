package org.example;
import java.util.ArrayList;
import java.util.List;

class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

class NegativeAmountException extends Exception {
    public NegativeAmountException(String message) {
        super(message);
    }
}

class AccountNotFoundException extends Exception {
    public AccountNotFoundException(String message) {
        super(message);
    }
}

class BankAccount {
    private int accountNumber;
    private String accountName;
    private double balance;

    public BankAccount(int accountNumber, String accountName, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.balance = initialDeposit;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void deposit(double amount) throws NegativeAmountException {
        if (amount < 0) {
            throw new NegativeAmountException("Депозит не може бути від'ємним");
        }
        balance += amount;
    }

    public void withdraw(double amount) throws NegativeAmountException, InsufficientFundsException {
        if (amount < 0) {
            throw new NegativeAmountException("Виведення не може бути від’ємним");
        }
        if (amount > balance) {
            throw new InsufficientFundsException("Недостатньо коштів для зняття");
        }
        balance -= amount;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountSummary() {
        return "Номер: " + accountNumber + ", Назва: " + accountName + ", Баланс: " + getBalance();
    }
}

class Bank {
    private List<BankAccount> accounts = new ArrayList<>();

    public BankAccount createAccount(String accountName, double initialDeposit) {
        int newAccountNumber = accounts.size() + 1;
        BankAccount account = new BankAccount(newAccountNumber, accountName, initialDeposit);
        accounts.add(account);
        return account;
    }

    public BankAccount findAccount(int accountNumber) throws AccountNotFoundException {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        throw new AccountNotFoundException("Акаунт не знайдено");
    }

    public void transferMoney(int fromAccountNumber, int toAccountNumber, double amount)
            throws NegativeAmountException, InsufficientFundsException, AccountNotFoundException {
        BankAccount fromAccount = findAccount(fromAccountNumber);
        BankAccount toAccount = findAccount(toAccountNumber);
        fromAccount.withdraw(amount);
        toAccount.deposit(amount);
    }
}

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();

        BankAccount account1 = null;
        BankAccount account2 = null;

        try {
            account1 = bank.createAccount("Sergotsuki", 2000);
            account2 = bank.createAccount("Stranger", 3000);

            try {
                account1.deposit(200);
            } catch (NegativeAmountException e) {
                System.out.println("Помилка: " + e.getMessage());
            }

            try {
                account2.withdraw(100);
            } catch (NegativeAmountException | InsufficientFundsException e) {
                System.out.println("Помилка: " + e.getMessage());
            }

            try {
                bank.transferMoney(account1.getAccountNumber(), account2.getAccountNumber(), 500);
            } catch (NegativeAmountException | InsufficientFundsException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        } catch (AccountNotFoundException e) {
            System.out.println("Помилка: " + e.getMessage());
        } finally {
            try {
                if (account1 != null) {
                    System.out.println(account1.getAccountSummary());
                }
                if (account2 != null) {
                    System.out.println(account2.getAccountSummary());
                }
            } catch (Exception e) {
                System.out.println("Помилка в finally: " + e.getMessage());
            }
        }
    }
}