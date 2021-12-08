package tg.finx.backend.usecase;

import tg.finx.backend.entity.Account;
import tg.finx.backend.entity.Transaction;
import tg.finx.backend.exception.AccountActionException;

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
     * @param act        the target Account object
     * @param t          the target Transaction object
     * @param isAddition true if adding to account, false otherwise
     */
    public void moveTransaction(Account act, Transaction t, boolean isAddition) throws AccountActionException {
        // Check if account or transaction is invalid
        if (act == null) {
            throw invalidAccountException;
        } else if (t == null) {
            throw invalidTransactionException;
        }
        if (!t.isPositiveFlow() || t.getTotalAmount() > act.getLiquidity()) {
            throw new AccountActionException("Invalid Transaction: Not Enough Liquidity In Account");
        }
        // Add or delete transaction depending on user action
        if (isAddition) {
            act.addTransaction(t);
        } else {
            act.deleteTransaction(t);
        }

        // Update relevant metrics of the account
        // Convert total amount to negative if it's a negative cash flow
        double totalAmt = t.getTotalAmount();
        if (!t.isPositiveFlow()) {
            totalAmt = t.getTotalAmount() * -1;
        }
        // If it's deleting a transaction
        if (!isAddition) {
            totalAmt = totalAmt * -1;
        }

        // Update Liquidity and cash level of account
        act.setLiquidity(act.getLiquidity() + totalAmt);
        act.setCash(act.getCash() + totalAmt);
        // Update amount invested based on transaction type
        String type = t.getType();
        if (type.equals("BUY") || type.equals("SELL")) {
            act.setAmountInvested(act.getAmountInvested() + totalAmt);
        }
        // Update total dividend
        if (t.getType().equals("DIV")) {
            act.setTotalDividend(act.getTotalDividend() + totalAmt);
        }
        refreshAccountReturns(act);
    }

    /**
     * refreshes the account's total return and capital return
     *
     * @param act target Account
     */
    public void refreshAccountReturns(Account act) throws AccountActionException {
        // Check if account or transaction is invalid
        if (act == null) {
            throw invalidAccountException;
        }
        // Update capital return
        double totalBuyAmt = 0.0;
        double totalSellAmt = 0.0;
        for (Transaction t : act.getTransactions()) {
            switch (t.getType()) {
                case "BUY":
                    totalBuyAmt += t.getTotalAmount();
                case "SELL":
                    totalSellAmt += t.getTotalAmount();
            }
        }
        act.setCapitalReturn(totalSellAmt - totalBuyAmt);
        // Update total return
        act.setTotalReturn(act.getCapitalReturn() + act.getTotalDividend());
    }
}