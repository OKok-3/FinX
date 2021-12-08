package tg.finx.backend.usecase;

import tg.finx.backend.entity.Account;
import tg.finx.backend.entity.Transaction;
import tg.finx.backend.exception.AccountActionException;

import java.util.ArrayList;

/**
 * THIS CLASS UTILIZES THE SINGLETON DESIGN PATTERN
 */
public class AccountManager {
    private static AccountManager AM_INSTANCE = null;
    private final AccountActionException invalidAccountException;
    private final AccountActionException invalidTransactionException;

    /**
     * This is a private constructor method to ensure that there is only one account manager at any given time
     */
    private AccountManager() {
        invalidAccountException = new AccountActionException("Invalid Account: Account Is NULL");
        invalidTransactionException = new AccountActionException("Invalid Transaction: Transaction Is NULL");
    }

    /**
     * This acts as the constructor
     *
     * @return an instance of AccountManager
     */
    public static AccountManager getInstance() {
        if (AM_INSTANCE == null) {
            AM_INSTANCE = new AccountManager();
        }
        return AM_INSTANCE;
    }

    /**
     * adds the Transaction t to Account acc
     *
     * @param acc the target Account object
     * @param t   the target Transaction object
     */
    public void addTransaction(Account acc, Transaction t) throws AccountActionException {
        // Check if account or transaction is invalid
        if (acc == null) {
            throw invalidAccountException;
        } else if (t == null) {
            throw invalidTransactionException;
        } if (!t.isPositiveFlow() || t.getTotalAmount() > acc.getLiquidity()) {
            throw new AccountActionException("Invalid Transaction: Not Enough Liquidity In Account");
        }
        // If transaction object already exists, create a new one with same information
        if (acc.getTransactions().contains(t)) {
            acc.addTransaction(
                    new Transaction(
                            t.getTime(),
                            t.getType(),
                            t.getTicker(),
                            t.getShares(),
                            t.getCurrency(),
                            t.getCostPerShare(),
                            t.getFxRate(),
                            t.getFee(),
                            t.getTotalAmount(),
                            t.isPositiveFlow()
                    )
            );
        } else {
            acc.addTransaction(t);
        }
        // Update relevant metrics of the account
        updateAccount(acc);
    }

    public void updateAccount(Account act) throws AccountActionException {
    }
}