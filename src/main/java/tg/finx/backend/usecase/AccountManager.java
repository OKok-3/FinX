package tg.finx.backend.usecase;

import tg.finx.backend.entity.Account;
import tg.finx.backend.entity.Transaction;
import tg.finx.backend.exceptions.AccountActionException;

import java.util.ArrayList;

public class AccountManager {
    Account account;

    public AccountManager(String name) {
        this.account = new Account(name);
    }

}
