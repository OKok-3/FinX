package tg.finx.test.backend.usecasetests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tg.finx.backend.entity.Account;
import tg.finx.backend.entity.Transaction;
import tg.finx.backend.exception.AccountActionException;
import tg.finx.backend.usecase.AccountManager;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AccountManagerTest {
    AccountManager AM_INSTANCE;
    Account act;
    Transaction t;

    @BeforeEach
    void setUp() {
        AM_INSTANCE = AccountManager.getInstance();
        act = AM_INSTANCE.createNewAccount("test");
        t = new Transaction(
                LocalDateTime.now(),
                "CONT",
                null,
                100.0,
                "CAD",
                1,
                1.0,
                0,
                100.0,
                true
        );
    }

    @Test
    void getInstance() {
        AccountManager NEW_AM_INSTANCE = AccountManager.getInstance();
        assertEquals(NEW_AM_INSTANCE, AM_INSTANCE);
    }

    @Test
    void moveTransaction() {
        try {
            AM_INSTANCE.moveTransaction(act, t, true);
        } catch (AccountActionException a) {
            fail();
        }

        assertEquals(100, act.getCash());
        assertEquals(100, act.getLiquidity());
        assertEquals(1, act.getTransactions().size());
        assertEquals(0, act.getAmountInvested());
        assertEquals(0, act.getCapitalReturn());

        try {
            AM_INSTANCE.moveTransaction(act, t, false);
        } catch (AccountActionException a) {
            fail();
        }

        assertEquals(0, act.getCash());
        assertEquals(0, act.getLiquidity());
        assertEquals(0, act.getTransactions().size());
        assertEquals(0, act.getAmountInvested());
        assertEquals(0, act.getCapitalReturn());
    }
}