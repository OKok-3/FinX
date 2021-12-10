package tg.finx.test.backend.usecasetests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tg.finx.backend.usecase.AccountManager.getInstance;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tg.finx.backend.usecase.AccountManager;
import tg.finx.backend.entity.Account;
import tg.finx.backend.exception.AccountActionException;

class AccountManagerTest {
    static AccountManager AM_INSTANCE;
    Account act;

    @BeforeAll
    static void setUp() {
        AM_INSTANCE = getInstance();
    }

    @BeforeEach
    void beforeTest() {
        act = new Account("test account");
    }

    @Test
    void TestSingleton() {
        assertEquals(AM_INSTANCE, getInstance());
    }

    @Test
    void TestSetNameOfAc() throws AccountActionException {
        AM_INSTANCE.setNameOfAct(act, "test account 2");
        assertEquals("test account 2", AM_INSTANCE.getNameOfAct(act));
    }

    @Test
    void TestAccountIsMargin() throws AccountActionException {
        assertEquals(false, AM_INSTANCE.accountIsMargin(act));
    }
}