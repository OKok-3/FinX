package tg.finx.backend.usecase;

import tg.finx.backend.entity.Account;
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
}
