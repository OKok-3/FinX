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

    /**
     * This is a private constructor method to ensure that there is only one account manager at any given time
     */
    private AccountManager() {
        invalidAccountException = new AccountActionException("Invalid Account: Account Is NULL");
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


}