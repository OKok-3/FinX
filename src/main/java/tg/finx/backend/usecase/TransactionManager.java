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

    /**
     * Create a Transaction object
     *
     * @param time        the time of Transaction
     * @param type        the type of Transaction
     * @param ticker      the ticker of the security, if cash enter CONT/WITH
     * @param shares      number of shares involved in the Transaction
     * @param currency    the currency of the Transaction
     * @param fxRate      the FX Rate applied to this Transaction, if applicable
     * @param fee         the fee incurred for this Transaction
     * @param totalAmount the total amount of this Transaction (including fees)
     * @return a Transaction object
     * @throws TransactionManagerExceptions when the type is not supported or number of shares is negative
     */
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
                totalAmount / shares,
                fxRate,
                fee,
                totalAmount,
                positiveFlowTypes.contains(type)
        );
    }

    /**
     * Gets the time of the Transaction
     *
     * @param t target Transaction
     * @return the time of the Transaction as LocalDateTime object
     * @throws TransactionManagerExceptions when the Transaction passed in as argument is null
     */
    public LocalDateTime getTimeOfTransaction(Transaction t) throws TransactionManagerExceptions {
        // Check if the Transaction passed in is null
        if (t == null) {
            throw new TransactionManagerExceptions("Transaction Cannot Be Null");
        }

        return t.getTime();
    }

    /**
     * Sets (or changes) the time of the Transaction
     *
     * @param t    target Transaction
     * @param time the new time for the target Transaction
     * @throws TransactionManagerExceptions when the Transaction passed in as argument is null
     */
    public void setTimeOfTransaction(Transaction t, LocalDateTime time) throws TransactionManagerExceptions {
        // Check if the Transaction passed in is null
        if (t == null) {
            throw new TransactionManagerExceptions("Transaction Cannot Be Null");
        }

        t.setTime(time);
    }


    /**
     * Gets the type of the Transaction
     *
     * @param t target Transaction
     * @return the type of the Transaction as String object
     * @throws TransactionManagerExceptions when the Transaction passed in as argument is null
     */
    public String getTypeOfTransaction(Transaction t) throws TransactionManagerExceptions {
        // Check if the Transaction passed in is null
        if (t == null) {
            throw new TransactionManagerExceptions("Transaction Cannot Be Null");
        }

        return t.getType();
    }

    /**
     * Sets (or changes) the type of the Transaction
     *
     * @param t    target Transaction
     * @param type the new type for the target Transaction
     * @throws TransactionManagerExceptions when the Transaction is null or when the type is not supported
     */
    public void setTypeOfTransaction(Transaction t, String type) throws TransactionManagerExceptions {
        // Check if the Transaction passed in is null
        if (t == null) {
            throw new TransactionManagerExceptions("Transaction Cannot Be Null");
        } else if (!negativeFlowTypes.contains(type) && !positiveFlowTypes.contains(type)) {
            throw new TransactionManagerExceptions("Type Not Supported");
        }

        t.setType(type);
        t.updatePositiveFlow(positiveFlowTypes.contains(type));
    }

    /**
     * Gets the ticker of the Transaction
     *
     * @param t target Transaction
     * @return the ticker of the Transaction as String object
     * @throws TransactionManagerExceptions when the Transaction passed in as argument is null
     */
    public String getTickerOfTransaction(Transaction t) throws TransactionManagerExceptions {
        // Check if the Transaction passed in is null
        if (t == null) {
            throw new TransactionManagerExceptions("Transaction Cannot Be Null");
        }

        return t.getTicker();
    }

    /**
     * Sets (or changes) the ticker of the Transaction
     *
     * @param t      target Transaction
     * @param ticker the new ticker for the target Transaction
     * @throws TransactionManagerExceptions when the Transaction is null
     */
    public void setTickerOfTransaction(Transaction t, String ticker) throws TransactionManagerExceptions {
        // Check if the Transaction passed in is null
        if (t == null) {
            throw new TransactionManagerExceptions("Transaction Cannot Be Null");
        }

        t.setTicker(ticker);
    }

    /**
     * Gets the number of shares of the Transaction
     *
     * @param t target Transaction
     * @return the number of shares of the Transaction as a double object
     * @throws TransactionManagerExceptions when the Transaction passed in as argument is null
     */
    public double getSharesOfTransaction(Transaction t) throws TransactionManagerExceptions {
        // Check if the Transaction passed in is null
        if (t == null) {
            throw new TransactionManagerExceptions("Transaction Cannot Be Null");
        }

        return t.getShares();
    }

    /**
     * Sets (or changes) the number of shares of the Transaction
     *
     * @param t      target Transaction
     * @param shares the new number of shares for the target Transaction
     * @throws TransactionManagerExceptions when the Transaction is null or number of shares is negative
     */
    public void setSharesOfTransaction(Transaction t, double shares) throws TransactionManagerExceptions {
        // Check if the Transaction passed in is null
        if (t == null) {
            throw new TransactionManagerExceptions("Transaction Cannot Be Null");
        } else if (shares < 0) {
            throw new TransactionManagerExceptions("Number of Shares Must Be Non-Negative");
        }

        t.setShares(shares);
    }

    /**
     * Gets the currency of the Transaction
     *
     * @param t target Transaction
     * @return the currency of the Transaction as a String object
     * @throws TransactionManagerExceptions when the Transaction passed in as argument is null
     */
    public String getCurrencyOfTransaction(Transaction t) throws TransactionManagerExceptions {
        // Check if the Transaction passed in is null
        if (t == null) {
            throw new TransactionManagerExceptions("Transaction Cannot Be Null");
        }

        return t.getCurrency();
    }

    /**
     * Sets (or changes) the currency of the Transaction
     *
     * @param t        target Transaction
     * @param currency the new currency of the target Transaction
     * @throws TransactionManagerExceptions when the Transaction passed in is null
     */
    public void setCurrencyOfTransaction(Transaction t, String currency) throws TransactionManagerExceptions {
        // Check if the Transaction passed in is null
        if (t == null) {
            throw new TransactionManagerExceptions("Transaction Cannot Be Null");
        }

        t.setCurrency(currency);
    }

    /**
     * Gets the FX rate of the Transaction
     *
     * @param t target Transaction
     * @return the FX rate of the Transaction as a double object
     * @throws TransactionManagerExceptions when the Transaction passed in as argument is null
     */
    public double getFXRateOfTransaction(Transaction t) throws TransactionManagerExceptions {
        // Check if the Transaction passed in is null
        if (t == null) {
            throw new TransactionManagerExceptions("Transaction Cannot Be Null");
        }

        return t.getFxRate();
    }

    /**
     * Sets (or changes) the currency of the Transaction
     *
     * @param t      target Transaction
     * @param fxRate the new FX rate of the target Transaction
     * @throws TransactionManagerExceptions when the Transaction passed in is null or the FX rate is negative
     */
    public void setFXRateOfTransaction(Transaction t, double fxRate) throws TransactionManagerExceptions {
        // Check if the Transaction passed in is null
        if (t == null) {
            throw new TransactionManagerExceptions("Transaction Cannot Be Null");
        } else if (fxRate < 0) {
            throw new TransactionManagerExceptions("FX Rate Must Be Non-Negative");
        }

        t.setFxRate(fxRate);
    }

    /**
     * Gets the cost per share of the Transaction
     *
     * @param t target Transaction
     * @return the cost per share of the Transaction as a double object
     * @throws TransactionManagerExceptions when the Transaction passed in as argument is null
     */
    public double getCostPerShareOfTransaction(Transaction t) throws TransactionManagerExceptions {
        // Check if the Transaction passed in is null
        if (t == null) {
            throw new TransactionManagerExceptions("Transaction Cannot Be Null");
        }

        return t.getCostPerShare();
    }

    /**
     * Gets the total amount of the Transaction
     *
     * @param t target Transaction
     * @return the total amount of the Transaction as a double object
     * @throws TransactionManagerExceptions when the Transaction passed in as argument is null
     */
    public double getTtlAmtOfTransaction(Transaction t) throws TransactionManagerExceptions {
        // Check if the Transaction passed in is null
        if (t == null) {
            throw new TransactionManagerExceptions("Transaction Cannot Be Null");
        }

        return t.getTotalAmount();
    }

    /**
     * Sets (or changes) the currency of the Transaction
     *
     * @param t   target Transaction
     * @param amt the new total amount of the target Transaction
     * @throws TransactionManagerExceptions when the Transaction passed in is null or the total amount is negative
     */
    public void setTtlAmtOfTransaction(Transaction t, double amt) throws TransactionManagerExceptions {
        // Check if the Transaction passed in is null
        if (t == null) {
            throw new TransactionManagerExceptions("Transaction Cannot Be Null");
        } else if (amt < 0) {
            throw new TransactionManagerExceptions("Amount Must Be Non-Negative");
        }

        // Set the total amount
        t.setTotalAmount(amt);
        // Update the cost per share
        t.setCostPerShare(amt / t.getShares());
    }

    /**
     * Gets the fee amount of the Transaction
     *
     * @param t target Transaction
     * @return the fee amount of the Transaction as a double object
     * @throws TransactionManagerExceptions when the Transaction passed in as argument is null
     */
    public double getFeeOfTransaction(Transaction t) throws TransactionManagerExceptions {
        // Check if the Transaction passed in is null
        if (t == null) {
            throw new TransactionManagerExceptions("Transaction Cannot Be Null");
        }

        return t.getFee();
    }

    /**
     * Sets (or changes) the fee amount of the Transaction
     *
     * @param t   target Transaction
     * @param fee the new fee amount of the target Transaction
     * @throws TransactionManagerExceptions when the Transaction passed in is null or the fee amount is negative
     */
    public void setFeeOfTransaction(Transaction t, double fee) throws TransactionManagerExceptions {
        // Check if the Transaction passed in is null
        if (t == null) {
            throw new TransactionManagerExceptions("Transaction Cannot Be Null");
        } else if (fee < 0) {
            throw new TransactionManagerExceptions("Fee Amount Must Be Non-Negative");
        }

        // Set the fee amount
        t.setFee(fee);
        // Update the total amount
        t.setTotalAmount(t.getTotalAmount() + fee);
        // Update cost per share
        t.setCostPerShare(t.getTotalAmount() / t.getShares());
    }
}
