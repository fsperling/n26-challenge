package de.n26.challenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author felix
 */
public class Transaction {
    @JsonProperty("amount")
    private double amount;
    @JsonProperty("timestamp")
    private long timestamp;

    public Transaction() {
    }

    public Transaction(double amount, long timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    
    @Override
    public String toString() {
        return "amount: " + this.amount +
                ", timestamp: " + this.timestamp;
    }
    
}
