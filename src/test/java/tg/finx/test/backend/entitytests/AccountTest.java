package tg.finx.test.backend.entitytests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tg.finx.backend.entity.Account;
import tg.finx.backend.entity.Transaction;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    Account account;
    Transaction t;

    @BeforeEach
    void setUp() {
        account = new Account("test");
        t = new Transaction(
                LocalDateTime.now(),
                "BUY",
                "AAPL",
                100.0,
                "USD",
                100,
                1.3112,
                0.0,
                10000,
                false);
    }

    @Test
    void TestGetTransactions() {
        assertEquals(0, account.getTransactions().size());
    }

    @Test
    void TestAddTransaction() {
        account.addTransaction(t);
        assertEquals(t, account.getTransactions().get(0));
        assertEquals(1, account.getTransactions().size());
    }

    @Test
    void TestDeleteTransaction() {
        account.addTransaction(t);
        account.deleteTransaction(t);
        assertEquals(0, account.getTransactions().size());
    }

    @Test
    void TestIsMargin() {
        assertFalse(account.isMargin());
    }

    @Test
    void TestSetMargin() {
        account.setMargin(true);
        assertTrue(account.isMargin());
    }

    @Test
    void TestGetMarginAmount() {
        assertEquals(0.0, account.getMarginAmount());
    }

    @Test
    void TestSetMarginAmount() {
        account.setMarginAmount(100);
        assertEquals(100, account.getMarginAmount());
    }

    @Test
    void TestGetLiquidity() {
        assertEquals(0, account.getLiquidity());
    }

    @Test
    void TestSetLiquidity() {
        account.setLiquidity(1000);
        assertEquals(1000, account.getLiquidity());
    }

    @Test
    void TestGetCash() {
        assertEquals(0, account.getCash());
    }

    @Test
    void TestSetCash() {
        account.setCash(10);
        assertEquals(10, account.getCash());
    }

    @Test
    void TestGetAmountInvested() {
        assertEquals(0, account.getAmountInvested());
    }

    @Test
    void TestSetAmountInvested() {
        account.setAmountInvested(100);
        assertEquals(100, account.getAmountInvested());
    }

    @Test
    void TestGetTotalDividend() {
        assertEquals(0, account.getTotalDividend());
    }

    @Test
    void TestSetTotalDividend() {
        account.setTotalDividend(1);
        assertEquals(1, account.getTotalDividend());
    }

    @Test
    void TestGetName() {
        assertEquals("test", account.getName());
    }

    @Test
    void TestSetName() {
        account.setName("test 2");
        assertEquals("test 2", account.getName());
    }
}