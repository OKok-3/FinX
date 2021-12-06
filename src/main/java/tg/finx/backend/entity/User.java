package tg.finx.backend.entity;

import java.util.ArrayList;

public class User {
    private ArrayList<Account> accounts;
    private Account defaultAccount;
    private String userName;
    private String email;

    /**
     * Constructor of User
     * @param userName username of user
     * @param email email address of user
     */
    public User(String userName, String email) {
        this.userName = userName;
        this.email = email;
        this.accounts = new ArrayList<>();
        this.accounts.add(new Account("Default Account"));
    }

    public ArrayList<Account> getAccounts() {
        return this.accounts;
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public void deleteAccount(Account account) {
        this.accounts.remove(account);
    }

    public Account getDefaultAccount() {
        return this.defaultAccount;
    }

    public void setDefaultAccount(Account defaultAccount) {
        this.defaultAccount = defaultAccount;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
