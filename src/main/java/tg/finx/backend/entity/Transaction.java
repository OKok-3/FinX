package tg.finx.backend.entity;

import java.time.LocalDateTime;

public class Transaction {
    private LocalDateTime time;
    private String type;
    private String ticker;
    private double shares;
    private String currency;
    private double fxRate;
    private double costPerShare;
    private double totalAmount;

    /**
     * Constructor of Transaction class. It calculates the costPerShare from totalAmount
     *
     * @param time        time of transaction
     * @param type        type of transaction (e.g. "BUY", "SELL", etc.)
     * @param ticker      ticker of the stock of transaction
     * @param shares      shares purchased
     * @param currency    currency of exchange for transaction
     * @param fxRate      foreign exchange rate of transaction (if local currency, set to 1.0)
     * @param totalAmount total amount transacted
     */
    public Transaction(LocalDateTime time, String type, String ticker, double shares, String currency, double fxRate, double totalAmount) {
        this.time = time;
        this.type = type;
        this.ticker = ticker;
        this.shares = shares;
        this.currency = currency;
        this.costPerShare = totalAmount / shares;
        this.fxRate = fxRate;
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getTime() {
        return this.time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTicker() {
        return this.ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public double getShares() {
        return this.shares;
    }

    public void setShares(double shares) {
        this.shares = shares;
        this.updateCostPerShare();
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getFxRate() {
        return this.fxRate;
    }

    public void setFxRate(double fxRate) {
        this.fxRate = fxRate;
    }

    public double getCostPerShare() {
        return this.costPerShare;
    }

    private void updateCostPerShare() {
        this.costPerShare = this.totalAmount/this.shares;
    }

    public double getTotalAmount() {
        return this.totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
        this.updateCostPerShare();
    }
}
