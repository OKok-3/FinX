package tg.finx.backend.tests.entity;

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
                10000
        );
    }

    @Test
    void getTransactions() {
        assertEquals(0, account.getTransactions().size());
    }

    @Test
    void addTransaction() {
        account.addTransaction(t);
        assertEquals(t, account.getTransactions().get(0));
        assertEquals(1, account.getTransactions().size());
    }

    @Test
    void deleteTransaction() {
        account.addTransaction(t);
        account.deleteTransaction(t);
        assertEquals(0, account.getTransactions().size());
    }

    @Test
    void isMargin() {
        assertFalse(account.isMargin());
    }

    @Test
    void setMargin() {
        account.setMargin(true);
        assertTrue(account.isMargin());
    }

    @Test
    void getMarginAmount() {
        assertEquals(0.0, account.getMarginAmount());
    }

    @Test
    void setMarginAmount() {
        account.setMarginAmount(100);
        assertEquals(100, account.getMarginAmount());
    }

    @Test
    void getLiquidity() {
        assertEquals(0, account.getLiquidity());
    }

    @Test
    void setLiquidity() {
        account.setLiquidity(1000);
        assertEquals(1000, account.getLiquidity());
    }

    @Test
    void getCash() {
        assertEquals(0, account.getCash());
    }

    @Test
    void setCash() {
        account.setCash(10);
        assertEquals(10, account.getCash());
    }

    @Test
    void getAmountInvested() {
        assertEquals(0, account.getAmountInvested());
    }

    @Test
    void setAmountInvested() {
        account.setAmountInvested(100);
        assertEquals(100, account.getAmountInvested());
    }

    @Test
    void getTotalDividend() {
        assertEquals(0, account.getTotalDividend());
    }

    @Test
    void setTotalDividend() {
        account.setTotalDividend(1);
        assertEquals(1, account.getTotalDividend());
    }

    @Test
    void getName() {
        assertEquals("test", account.getName());
    }

    @Test
    void setName() {
        account.setName("test 2");
        assertEquals("test 2", account.getName());
    }
}