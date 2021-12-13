package tg.finx.backend.usecase;

import tg.finx.backend.entity.Account;
import tg.finx.backend.entity.Transaction;
import tg.finx.backend.exception.AccountActionException;

public class AccountTransactionAgent {
    private static AccountTransactionAgent ATA_INSTANCE = null;
    private final AccountActionException invalidActException;

    /**
     * Private constructor to forbid the use of "new" keyword
     */
    private AccountTransactionAgent() {
        invalidActException = new AccountActionException("Account Is Null");
    }

    /**
     * Get an instance of AccountTransactionManager
     *
     * @return an instance of AccountTransactionManager
     */
    public static AccountTransactionAgent getInstance() {
        if (ATA_INSTANCE == null) {
            ATA_INSTANCE = new AccountTransactionAgent();
        }
        return ATA_INSTANCE;
    }

    /**
     * Deposits cash to Account
     *
     * @param act target Account
     * @param amt deposit amount
     */
    public void depositToAccount(Account act, double amt) throws AccountActionException {
        if (act == null) {
            throw invalidActException;
        } else if (amt < 0) {
            throw new AccountActionException("Deposit Amount Cannot Be Negative");
        }
        act.setCash(act.getCash() + amt);
        act.setLiquidity(act.getLiquidity() + amt);
    }

    /**
     * Withdraws cash from Account
     *
     * @param act target Account
     * @param amt withdrawal amount
     */
    public void withdrawFromAccount(Account act, double amt) throws AccountActionException {
        if (act == null) {
            throw invalidActException;
        } else if (amt < 0) {
            throw new AccountActionException("Withdrawal Amount Cannot Be Negative");
        } else if (amt > act.getCash()) {
            throw new AccountActionException("Not Enough Cash");
        }
        act.setCash(act.getCash() - amt);
        act.setLiquidity(act.getLiquidity() - amt);
    }

    /**
     * Get the total shares of a stock owned in the Account
     *
     * @param act    target Account
     * @param ticker ticker the stock
     * @return total number of shares of stock owned, in a double object
     */
    public double getTtlSharesOwnedInAct(Account act, String ticker) throws AccountActionException {
        if (act == null) {
            throw invalidActException;
        }
        // Calculate the amount of shares available to sell
        double totalSharesOfStock = 0.0;
        for (Transaction trans : act.getTransactions()) {
            // If the transaction's ticker is the same as the required ticker
            if (trans.getTicker().equals(ticker)) {
                if (trans.getType().equals("BUY")) {
                    // Increase count if it's a buy
                    totalSharesOfStock += trans.getShares();
                } else if (trans.getType().equals("SELL")) {
                    // Decrease count if it's a sell
                    totalSharesOfStock -= trans.getShares();
                }
            }
        }

        return totalSharesOfStock;
    }

    /**
     * Updates the metrics of the account based on user action on a Transaction
     *
     * @param act        target Account
     * @param t          Transaction of concern
     * @param isAddition whether it's addint the Transaction into Account or
     *                   deleting it from Account
     */
    private void updateActMetrics(Account act, Transaction t, boolean isAddition) throws AccountActionException {
        int factor = 1;
        if (!isAddition) {
            factor = -1;
        }

        double totalAmt = t.getTotalAmount() * factor;

        // Check if there is sufficient shares to complete the transaction
        if (isAddition && t.getType().equals("SELL")) {
            // Check if there are enough shares to be sold
            if (t.getShares() > this.getTtlSharesOwnedInAct(act, t.getTicker())) {
                throw new AccountActionException("Insufficient Shares of " + t.getTicker());
            }
        } else if (!isAddition && t.getType().equals("BUY")) {
            // Throws an exception if somehow there isn't enough shares left
            if (t.getShares() > this.getTtlSharesOwnedInAct(act, t.getTicker())) {
                throw new AccountActionException("Insufficient Shares of " + t.getTicker());
            }
        }

        // Switch case to deal with various types of transactions
        switch (t.getType()) {
            case "BUY":
                // Intentional fall through
                act.setAmountInvested(act.getAmountInvested() + totalAmt);
            case "TAX":
            case "FEE":
                act.setLiquidity(act.getLiquidity() - totalAmt);
                act.setCash(act.getCash() - totalAmt);
                break;
            case "SELL":
                act.setLiquidity(act.getLiquidity() + totalAmt);
                act.setCash(act.getCash() + totalAmt);
                act.setAmountInvested(act.getAmountInvested() - totalAmt);
                break;
            case "DIV":
                // This is intentionally letting it to fall through because it shares the
                // instructions as the following two cases
                act.setTotalDividend(act.getTotalDividend() + totalAmt);
            case "REFER":
            case "CONT":
                act.setLiquidity(act.getLiquidity() + totalAmt);
                act.setCash(act.getCash() + totalAmt);
                break;
        }
    }

    /**
     * Adds Transaction t to Account act
     *
     * @param act target Account
     * @param t   target Transaction
     */
    public void addTransaction(Account act, Transaction t) throws AccountActionException {
        if (act == null) {
            throw invalidActException;
        } else if (t == null) {
            throw new AccountActionException("Transaction Is Null");
        }

        // Check if the Transaction is net negative cash flow
        if (!t.isPositiveFlow() && t.getTotalAmount() > act.getLiquidity()) {
            // Throw exception if the Account doesn't have enough liquidity to complete the
            // transaction
            throw new AccountActionException("Insufficient Liquidity");
        }

        act.addTransaction(t);
        updateActMetrics(act, t, true);
    }

    /**
     * Deletes a Transaction from Account
     *
     * @param act target Account
     * @param t   the Transaction that is going to be deleted
     */
    public void deleteTransaction(Account act, Transaction t) throws AccountActionException {
        // Throw exceptions if the following errors occur
        if (act == null) {
            throw invalidActException;
        } else if (t == null) {
            throw new AccountActionException("Transaction Is Null");
        } else if (!act.getTransactions().contains(t)) {
            throw new AccountActionException("Transaction Not In Account " + act.getName());
        }

        // We are not performing any more error checking because the if the Transaction is already in the Account, it
        // means that it's a valid transaction to be included in the Account. So it would also be valid to delete it
        // from the Account
        act.deleteTransaction(t);
        updateActMetrics(act, t, false);
    }
}
