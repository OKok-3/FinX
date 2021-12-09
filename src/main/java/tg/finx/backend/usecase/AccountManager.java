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
     * Converts the Account to a margin account
     *
     * @param act       target Account
     * @param marginAmt margin amount of Account
     */
    public void convertActToMargin(Account act, double marginAmt) throws AccountActionException {
        if (act == null) {
            throw invalidActException;
        }
        act.setMargin(true);
        act.setMarginAmount(marginAmt);
    }

    /**
     * Convert Account to Non-Margin account
     *
     * @param act target Account
     */
    public void convertActToNonMargin(Account act) throws AccountActionException {
        if (act == null) {
            throw invalidActException;
        }
        act.setMargin(false);
        act.setMarginAmount(0.0);
    }
}
