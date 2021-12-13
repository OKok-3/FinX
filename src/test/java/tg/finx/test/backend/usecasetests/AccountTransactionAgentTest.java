package tg.finx.test.backend.usecasetests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static tg.finx.backend.usecase.AccountTransactionAgent.getInstance;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tg.finx.backend.entity.Account;
import tg.finx.backend.usecase.AccountManager;
import tg.finx.backend.usecase.AccountTransactionAgent;
import tg.finx.backend.entity.Transaction;
import tg.finx.backend.exception.AccountActionException;

class AccountTransactionAgentTest {
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
        assertEquals(ATA, ATA_2);
    }

    @Test
    void TestDepositWithdrawal() {
        int iteration = rand.nextInt(1000) + 150;
        double expectedCash = 0.0;
        double expectedLiquidity = 0.0;

        for (int i = 0; i < iteration; i++) {
            double amt = rand.nextDouble() * (rand.nextInt(1000) * 10);
            if (rand.nextInt(2) == 1) {
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

    @Test
    void TestGetTtlSharesOwnedInAct() {
        // Random number of iterations
        int iteration = rand.nextInt(200) + 50;
        // Give Account infinite liquidity
        act.setLiquidity(10000000000000000000.0);

        // ArrayList for the algorithm to randomly choose between selling and buying
        ArrayList<String> options = new ArrayList<>(Arrays.asList("BUY", "SELL"));
        // Expected values
        double sharesBought = 0.0;
        double sharesSold = 0.0;

        try {
            for (int i = 0; i < iteration; i++) {
                // Generate random values to create a Transaction
                LocalDateTime time = LocalDateTime.of(
                        2021,
                        rand.nextInt(11) + 1,
                        rand.nextInt(27) + 1,
                        0,
                        0,
                        0
                );
                String type = options.get(rand.nextInt(1));
                String ticker = "ABC";
                double shares;
                // If statement to make sure we are not selling more than we own
                if (type.equals("SELL")) {
                    shares = Math.min(rand.nextDouble() * 100 + 1, sharesBought);
                } else {
                    shares = rand.nextDouble() * 200 + 5;
                }
                String currency = "CAD";
                double fxRate = 1.0;
                double totalAmt = rand.nextDouble() * 1000 + 200;
                double costPerShare = totalAmt / shares;
                // Create the Transaction
                Transaction t = new Transaction(
                        time,
                        type,
                        ticker,
                        shares,
                        currency,
                        costPerShare,
                        fxRate,
                        0.0,
                        totalAmt,
                        type.equals("SELL")
                );
                if (type.equals("SELL")) {
                    sharesSold += shares;
                } else {
                    sharesBought += shares;
                }
                ATA.addTransaction(act, t);
            }
            assertEquals(sharesBought - sharesSold, ATA.getTtlSharesOwnedInAct(act, "ABC"));
        } catch (AccountActionException a) {
            fail();
        }

    }
}
