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
        invalidActException = new AccountActionException("Account Is Null");
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
     * This method returns a new Account object. Use this to create new Accounts
     * 
     * @param name name of Account
     * @return an Account object with the give name
     */
    public Account createAccount(String name) {
        return new Account(name);
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
     * Get whether the Account is margin account or not
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
     * Returns the capital return of Account
     *
     * @param act target Account
     * @return Account's capital return
     */
    public double getCapitalRetOfAct(Account act) throws AccountActionException {
        if (act == null) {
            throw invalidActException;
        }
        return act.getCapitalReturn();
    }

    /**
     * Returns the total return of Account
     *
     * @param act target Account
     * @return Account's total return
     */
    public double getTotalRetOfAct(Account act) throws AccountActionException {
        if (act == null) {
            throw invalidActException;
        }
        return act.getTotalReturn();
    }

    /**
     * Returns the margin amount of Account
     * 
     * @param act target Account
     * @return the margin amount of Account
     */
    public double getMarginAmtOfAct(Account act) throws AccountActionException {
        if (act == null) {
            throw invalidActException;
        }
        return act.getMarginAmount();
    }

    /**
     * Increases the marginAmt of Account by amt
     * 
     * @param act target Account
     * @param amt increase increment
     */
    public void increaseActMarginByAmt(Account act, double amt) throws AccountActionException {
        if (act == null) {
            throw invalidActException;
        } else if (amt < 0) {
            throw new AccountActionException("Increase Increment Cannot Be Negative");
        }
        act.setLiquidity(act.getLiquidity() + amt);
        act.setMarginAmount(act.getMarginAmount() + amt);
    }

    /**
     * Decreases the marginAmt of Account by amt
     * 
     * @param act target Account
     * @param amt decrease increment
     */
    public void decreaseActMarginByAmt(Account act, double amt) throws AccountActionException {
        if (act == null) {
            throw invalidActException;
        } else if (amt < 0) {
            throw new AccountActionException("Increase Increment Cannot Be Negative");
        } else if (amt > act.getLiquidity()) {
            throw new AccountActionException("Insufficient Liquidity");
        }
        act.setLiquidity(act.getLiquidity() - amt);
        act.setMarginAmount(act.getMarginAmount() - amt);
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
            throw new AccountActionException("Margin Amount Cannot Be Negative");
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
            throw new AccountActionException("Insufficient Liquidity");
        }
        act.setMargin(false);
        act.setLiquidity(act.getLiquidity() - act.getMarginAmount());
        act.setMarginAmount(0.0);
    }
}
