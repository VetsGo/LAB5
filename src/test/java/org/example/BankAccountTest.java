package org.example;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class BankAccountTest {
    private BankAccount account;

    @Before
    public void setUp() {
        account = new BankAccount(1, "ТестовийАкаунт", 1000);
    }

    @Test
    public void testDeposit() throws NegativeAmountException {
        account.deposit(500);
        assertEquals(1500, account.getBalance(), 0);
    }

    @Test(expected = NegativeAmountException.class)
    public void testDepositWithNegativeAmount() throws NegativeAmountException {
        account.deposit(-100);
    }

    @Test
    public void testWithdraw() throws NegativeAmountException, InsufficientFundsException {
        account.withdraw(500);
        assertEquals(500, account.getBalance(), 0);
    }

    @Test(expected = NegativeAmountException.class)
    public void testWithdrawWithNegativeAmount() throws NegativeAmountException, InsufficientFundsException {
        account.withdraw(-100);
    }

    @Test(expected = InsufficientFundsException.class)
    public void testWithdrawWithInsufficientFunds() throws NegativeAmountException, InsufficientFundsException {
        account.withdraw(1500);
    }
}