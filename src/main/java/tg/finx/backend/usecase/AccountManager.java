package tg.finx.backend.usecase;

import tg.finx.backend.exception.AccountActionException;

public class AccountManager {
    private static AccountManager AM_INSTANCE = null;
    private AccountActionException invalidActException;

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
}
