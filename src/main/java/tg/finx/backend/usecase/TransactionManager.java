package tg.finx.backend.usecase;

import tg.finx.backend.entity.Transaction;
import tg.finx.backend.exception.TransactionManagerExceptions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class TransactionManager {
    private static TransactionManager TM = null;
    private static final ArrayList<String> negativeFlowTypes = new ArrayList<>(Arrays.asList("BUY", "FEE", "RST", "WITH"));
    private static final ArrayList<String> positiveFlowTypes = new ArrayList<>(Arrays.asList("SELL", "CONT", "DIV"));

    /**
     * Private constructor for Singleton implementation
     */
    private TransactionManager() {
    }

    /**
     * Get an instance of Transaction Manager
     *
     * @return a TransactionManager object
     */
    public TransactionManager getIntance() {
        if (TM == null) {
            TM = new TransactionManager();
        }

        return TM;
    }

    public Transaction createTransaction(LocalDateTime time,
                                         String type,
                                         String ticker,
                                         double shares,
                                         String currency,
                                         double fxRate,
                                         double fee,
                                         double totalAmount) throws TransactionManagerExceptions {
        // If the transaction type is not supported, throw an exception
        if (!negativeFlowTypes.contains(type) && !positiveFlowTypes.contains(type)) {
            throw new TransactionManagerExceptions("Type Not Supported");
        } else if (shares < 0) {
            throw new TransactionManagerExceptions("Number of Shares Must Be Non-Negative");
        }

        return new Transaction(
                time,
                type,
                ticker,
                shares,
                currency,
                totalAmount/shares,
                fxRate,
                fee,
                totalAmount,
                positiveFlowTypes.contains(type)
        );
    }
}
