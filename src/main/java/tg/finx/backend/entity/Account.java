package tg.finx.backend.entity;

import java.util.ArrayList;

public class Account {
    private final ArrayList<Transaction> transactions;
    private boolean isMargin;
    private double marginAmount;
    private double liquidity;
    private double cash;
    private double amountInvested;
    private double totalDividend;
    private String name;

    /**
     * Constructor of Account. It will return a non-margin empty account when called
     * @param name the name of the account
     */
    public Account(String name) {
        this.transactions = new ArrayList<>();
        this.isMargin = false;
        this.marginAmount = 0.0;
        this.liquidity = 0.0;
        this.cash = 0.0;
        this.amountInvested = 0.0;
        this.totalDividend = 0.0;
        this.name = name;
    }

    public ArrayList<Transaction> getTransactions() {
        return this.transactions;
    }

    public void deleteTransaction(Transaction t) {
        this.transactions.remove(t);
    }

    public void addTransaction(Transaction t) {
        this.transactions.add(t);
    }

    public boolean isMargin() {
        return this.isMargin;
    }

    public void setMargin(boolean margin) {
        this.isMargin = margin;
    }

    public double getMarginAmount() {
        return this.marginAmount;
    }

    public void setMarginAmount(double marginAmount) {
        this.marginAmount = marginAmount;
    }

    public double getLiquidity() {
        return this.liquidity;
    }

    public void setLiquidity(double liquidity) {
        this.liquidity = liquidity;
    }

    public double getCash() {
        return this.cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public double getAmountInvested() {
        return this.amountInvested;
    }

    public void setAmountInvested(double amountInvested) {
        this.amountInvested = amountInvested;
    }

    public double getTotalDividend() {
        return this.totalDividend;
    }

    public void setTotalDividend(double totalDividend) {
        this.totalDividend = totalDividend;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
