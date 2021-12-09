package tg.finx.backend.usecase;

import tg.finx.backend.entity.Account;
import tg.finx.backend.exception.AccountActionException;

public class AccountManager {
    private static AccountManager AM_INSTANCE = null;
    private final AccountActionException invalidActException;

    /**
     * Private constructor to forbid the use of "new" keyword
     */
    private AccountManager() {
        invalidActException = new AccountActionException("Invalid Account Action: Account Is Null");
    }

    /**
     * Get an instance of AccountManager
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
     * Get the name of the Account
     *
     * @param act target Account
     * @return a String representing the name of the Account
     */
    public String getNameOfAct(Account act) throws AccountActionException {
        if (act == null) {
            throw invalidActException;
        }
        return act.getName();
    }

    /**
     * Sets the name of the Account to newName
     *
     * @param act     the target Account
     * @param newName the new name for the Account
     */
    public void setNameOfAct(Account act, String newName) throws AccountActionException {
        if (act == null) {
            throw invalidActException;
        }
        act.setName(newName);
    }

    /**
     * Get whether the Account is margin accuont or not
     *
     * @param act target account
     * @return true if the Account is margin, false otherwise
     */
    public boolean accountIsMargin(Account act) throws AccountActionException {
        if (act == null) {
            throw invalidActException;
        }
        return act.isMargin();
    }

    /**
     * Returns the liquidity of Account
     *
     * @param act target Account
     * @return Account's liquidity
     */
    public double getLiquidityOfAct(Account act) throws AccountActionException {
        if (act == null) {
            throw invalidActException;
        }
        return act.getLiquidity();
    }

    /**
     * Returns the remaining cash of Account
     *
     * @param act target Account
     * @return Account's remaining cash
     */
    public double getCashLvlOfAct(Account act) throws AccountActionException {
        if (act == null) {
            throw invalidActException;
        }
        return act.getCash();
    }

    /**
     * Returns the total dividend received of Account
     *
     * @param act target Account
     * @return Account's total dividend received
     */
    public double getTotalDivOfAct(Account act) throws AccountActionException {
        if (act == null) {
            throw invalidActException;
        }
        return act.getTotalDividend();
    }

    /**
     * Converts the Account to a margin account
     *
     * @param act       target Account
     * @param marginAmt margin amount of Account
     */
    public void convertActToMargin(Account act, double marginAmt) throws AccountActionException {
        if (act == null) {
            throw invalidActException;
        } else if (marginAmt < 0) {
            throw new AccountActionException("Invalid Account Action: Margin Amount Cannot Be Negative");
        }
        act.setMargin(true);
        act.setMarginAmount(marginAmt);
        act.setLiquidity(act.getLiquidity() + marginAmt);
    }

    /**
     * Convert Account to Non-Margin account
     *
     * @param act target Account
     */
    public void convertActToNonMargin(Account act) throws AccountActionException {
        if (act == null) {
            throw invalidActException;
        } else if (act.getLiquidity() < act.getMarginAmount()) {
            // check if the Account is able to convert to non-margin
            throw new AccountActionException("Invalid Account Action: Insufficient Liquidity");
        }
        act.setMargin(false);
        act.setLiquidity(act.getLiquidity() - act.getMarginAmount());
        act.setMarginAmount(0.0);
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
            throw new AccountActionException("Invalid Account Action: Deposit Amount Cannot Be Negative");
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
            throw new AccountActionException("Invalid Account Action: Withdrawal Amount Cannot Be Negative");
        } else if (amt > act.getCash()) {
            throw new AccountActionException("Invalid Account Action: Not Enough Cash");
        }
        act.setCash(act.getCash() - amt);
        act.setLiquidity(act.getLiquidity() - amt);
    }
}
