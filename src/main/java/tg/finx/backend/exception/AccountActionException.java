package tg.finx.backend.exception;

public class AccountActionException extends Exception {
    /**
     * This is a custom exception to be thrown when there are errors occurring in AccountManager
     */
    private final String msg;

    public AccountActionException(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return this.msg;
    }
}
