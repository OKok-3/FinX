package tg.finx.backend.entity;

import java.time.LocalDateTime;

public class Transaction {
    private LocalDateTime time;
    private String type;
    private String ticker;
    private double shares;
    private String currency;
    private double fxRate;
    private double fee;
    private double costPerShare;
    private double totalAmount;
    private final boolean isPositiveFlow;

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
    public Transaction(LocalDateTime time, String type, String ticker, double shares, String currency, double costPerShare, double fxRate, double fee, double totalAmount, boolean pCF) {
        this.time = time;
        this.type = type;
        this.ticker = ticker;
        this.shares = shares;
        this.currency = currency;
        this.costPerShare = costPerShare;
        this.fxRate = fxRate;
        this.fee = fee;
        this.totalAmount = totalAmount;
        this.isPositiveFlow = pCF;
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

    public void setCostPerShare(double costPerShare) {
        this.costPerShare = costPerShare;
    }

    public double getTotalAmount() {
        return this.totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public boolean isPositiveFlow() {
        return this.isPositiveFlow;
    }
}
