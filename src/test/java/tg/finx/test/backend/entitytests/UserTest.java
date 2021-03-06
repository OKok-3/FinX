package tg.finx.test.backend.entitytests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tg.finx.backend.entity.Account;
import tg.finx.backend.entity.User;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    User user;
    Account account;

    @BeforeEach
    void setUp() {
        user = new User("test user", "test@gmail.com");
        account = new Account("test account");
    }

    @Test
    void TestGetAccounts() {
        assertEquals(1, user.getAccounts().size());
        assertEquals("Default Account", user.getAccounts().get(0).getName());
    }

    @Test
    void TestAddAccount() {
        user.addAccount(account);
        assertEquals(2, user.getAccounts().size());
        assertTrue(user.getAccounts().contains(account));
    }

    @Test
    void TestRemoveAccount() {
        System.out.println(user.getDefaultAccount());
        user.removeAccount(user.getDefaultAccount());
        System.out.println(user.getDefaultAccount());
        assertEquals(0, user.getAccounts().size());
    }

    @Test
    void TestSetDefaultAccount() {
        user.addAccount(account);
        user.setDefaultAccount(account);
        assertEquals("test account", user.getDefaultAccount().getName());
    }

    @Test
    void TestSetUserName() {
        user.setUserName("new name");
        assertEquals("new name", user.getUserName());
    }

    @Test
    void TestSetEmail() {
        user.setEmail("test2@gmail.com");
        assertEquals("test2@gmail.com", user.getEmail());
    }
}