package org.example;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class BankTest {
    private Bank bank;

    @Before
    public void setUp() {
        bank = new Bank();
    }

    @Test
    public void testCreateAccount() {
        BankAccount account = bank.createAccount("ТестовийАкаунт", 1000);
        assertNotNull(account);
    }

    @Test
    public void testFindAccount() throws AccountNotFoundException {
        BankAccount account = bank.createAccount("ТестовийАкаунт", 1000);
        int accountNumber = account.getAccountNumber();
        BankAccount foundAccount = bank.findAccount(accountNumber);
        assertEquals(account, foundAccount);
    }

    @Test(expected = AccountNotFoundException.class)
    public void testFindAccountNotFound() throws AccountNotFoundException {
        bank.findAccount(999);
    }

    @Test
    public void testTransferMoney() throws NegativeAmountException, InsufficientFundsException, AccountNotFoundException {
        BankAccount account1 = bank.createAccount("Акаунт1", 1000);
        BankAccount account2 = bank.createAccount("Акаунт2", 1000);
        bank.transferMoney(account1.getAccountNumber(), account2.getAccountNumber(), 500);
        assertEquals(500, account1.getBalance(), 0);
        assertEquals(1500, account2.getBalance(), 0);
    }
}