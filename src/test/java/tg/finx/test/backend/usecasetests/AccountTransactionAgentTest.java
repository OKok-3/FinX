package tg.finx.test.backend.usecasetests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static tg.finx.backend.usecase.AccountTransactionAgent.getInstance;

import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tg.finx.backend.entity.Account;
import tg.finx.backend.usecase.AccountManager;
import tg.finx.backend.usecase.AccountTransactionAgent;
import tg.finx.backend.entity.Transaction;
import tg.finx.backend.exception.AccountActionException;

public class AccountTransactionAgentTest {
    static AccountTransactionAgent ATA;
    static AccountManager AM;
    Account act;
    static Random rand;

    @BeforeAll
    static void setUp() {
        ATA = getInstance();
        AM = AccountManager.getInstance();
        rand = new Random();
    }

    @BeforeEach
    void setUpTest() {
        act = AM.createAccount("test account");
    }

    @Test
    void TestSingleton() {
        AccountTransactionAgent ATA_2 = getInstance();
        assertEquals(ATA, ATA);
    }

    @Test
    void TestDepositWithdrawal() {
        int iteration = rand.nextInt(10) + 25;
        double expectedCash = 0.0;
        double expectedLiquidity = 0.0;

        for (int i = 0; i < iteration; i++) {
            double amt = rand.nextDouble() * (rand.nextInt(1000) * 10);
            if (rand.nextInt(1) == 1) {
                try {
                    ATA.depositToAccount(act, amt);
                    expectedCash += amt;
                    expectedLiquidity += amt;
                } catch (AccountActionException e) {
                    fail();
                }
            } else {
                try {
                    ATA.withdrawFromAccount(act, amt);
                    expectedCash -= amt;
                    expectedLiquidity -= amt;
                } catch (AccountActionException e) {
                    if (!(act.getCash() < amt) || !(act.getLiquidity() < amt)) {
                        fail();
                    }
                }
            }
        }

        try {
            assertEquals(expectedCash, AM.getCashLvlOfAct(act));
            assertEquals(expectedLiquidity, AM.getLiquidityOfAct(act));
        } catch (AccountActionException e) {
            fail();
        }

    }
}
