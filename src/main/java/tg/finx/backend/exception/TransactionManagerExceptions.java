package tg.finx.backend.exception;

public class TransactionManagerExceptions extends Exception {
    /**
     * This is a custom exception to be thrown when there are errors occurring in TransactionManager
     */
    private final String msg;

    public TransactionManagerExceptions(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Invalid Transaction Manager Action: " + msg;
    }
}
