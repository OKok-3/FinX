package tg.finx.test.backend.usecasetests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
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

    @Test
    void TestGetLiquidityOfAct() throws AccountActionException {
        assertEquals(0.0, AM_INSTANCE.getLiquidityOfAct(act));
    }

    @Test
    void TestGetCashLvlOfAct() throws AccountActionException {
        assertEquals(0.0, AM_INSTANCE.getCashLvlOfAct(act));
    }

    @Test
    void TestGetTotalDivOfAct() throws AccountActionException {
        assertEquals(0.0, AM_INSTANCE.getTotalDivOfAct(act));
    }

    @Test
    void TestGetCapitalRetOfActt() throws AccountActionException {
        assertEquals(0.0, AM_INSTANCE.getCapitalRetOfAct(act));
    }

    @Test
    void TestGetTotalRetOfActt() throws AccountActionException {
        assertEquals(0.0, AM_INSTANCE.getTotalRetOfAct(act));
    }

    @Test
    void TestIncreaseActMarginByAmt() throws AccountActionException {
        AM_INSTANCE.convertActToMargin(act, 100);
        AM_INSTANCE.increaseActMarginByAmt(act, 100);
        assertEquals(200.0, AM_INSTANCE.getMarginAmtOfAct(act));
    }

    @Test
    void TestDecreaseActMarginByAmt() {
        try {
            AM_INSTANCE.convertActToMargin(act, 100);
            AM_INSTANCE.decreaseActMarginByAmt(act, 100);
            assertEquals(0.0, AM_INSTANCE.getMarginAmtOfAct(act));
            // The line below should not run without errors, if it doesn the test fails
            AM_INSTANCE.decreaseActMarginByAmt(act, 1);
            fail();
        } catch (AccountActionException a) {

        }

    }

    @Test
    void TestConvertActToMargin() throws AccountActionException {
        AM_INSTANCE.convertActToMargin(act, 1000.0);
        assertEquals(true, AM_INSTANCE.accountIsMargin(act));
    }

    @Test
    void TestConverActToNonMargin() {
        try {
            AM_INSTANCE.convertActToMargin(act, 100.0);
            act.setLiquidity(99);
            AM_INSTANCE.convertActToNonMargin(act);
            // It should never reach the fail line If it doesn, it means that the account
            // now has a negative liquidity, which is not allowed
            fail();
        } catch (AccountActionException a) {
        }
    }
}