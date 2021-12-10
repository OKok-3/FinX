package tg.finx.backend.usecase;

import tg.finx.backend.entity.Account;
import tg.finx.backend.entity.Transaction;
import tg.finx.backend.exception.AccountActionException;

public class AccountTransactionManager {
    private static AccountTransactionManager ATM_INSTANCE = null;
    private final AccountActionException invalidActException;

    /**
     * Private constructor to forbid the use of "new" keyword
     */
    private AccountTransactionManager() {
        invalidActException = new AccountActionException("Account Is Null");
    }

    /**
     * Get an instance of AccountTransactionManager
     *
     * @return an instance of AccountTransactionManager
     */
    public AccountTransactionManager getInstance() {
        if (ATM_INSTANCE == null) {
            ATM_INSTANCE = new AccountTransactionManager();
        }
        return ATM_INSTANCE;
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

        // Switch case to deal with various types of transactions
        switch (t.getType()) {
            case "BUY":
                // Intentional fall through
                act.setAmountInvested(act.getAmountInvested() + t.getTotalAmount());
            case "TAX":
            case "FEE":
                act.setLiquidity(act.getLiquidity() - t.getTotalAmount());
                act.setCash(act.getCash() - t.getTotalAmount());
                break;
            case "SELL":
                // Check if there are enough shares to be sold
                if (t.getShares() > this.getTtlSharesOwnedInAct(act, t.getTicker())) {
                    throw new AccountActionException("Insufficient Shares");
                }
                act.setLiquidity(act.getLiquidity() + t.getTotalAmount());
                act.setCash(act.getCash() + t.getTotalAmount());
                act.setAmountInvested(act.getAmountInvested() - t.getTotalAmount());
                break;
            case "DIV":
                // This is intentionally letting it to fall through because it shares the
                // instructions as the following two cases
                act.setTotalDividend(act.getTotalDividend() + t.getTotalAmount());
            case "REFER":
            case "CONT":
                act.setLiquidity(act.getLiquidity() + t.getTotalAmount());
                act.setCash(act.getCash() + t.getTotalAmount());
                break;
        }

    }

    public double getTtlSharesOwnedInAct(Account act, String ticker) throws AccountActionException {
        return 0.0;
    }
}
