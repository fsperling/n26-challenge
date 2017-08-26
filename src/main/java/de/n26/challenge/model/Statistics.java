package de.n26.challenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author felix
 */
public class Statistics {
    @JsonProperty("sum")
    private double sum;
    @JsonProperty("avg")
    private double avg;
    @JsonProperty("max")
    private double max;
    @JsonProperty("min")
    private double min;
    @JsonProperty("count")
    private long count;

    public Statistics() {}
    
    public Statistics(double sum, double avg, double max, double min, long count) {
        this.sum = sum;
        this.avg = avg;
        this.max = max;
        this.min = min;
        this.count = count;
    }

    public Statistics(Transaction tr) {
        this.avg = tr.getAmount();
        this.max = tr.getAmount();
        this.min = tr.getAmount();
        this.sum = tr.getAmount();
        this.count = 1;
    }
    
    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Statistics combineWith(Transaction tr) {
        return this.combineWith(new Statistics(tr));
    }

    public Statistics combineWith(Statistics s) {
        this.max = Math.max(this.max, s.getMax());
        this.min = Math.min(this.min, s.getMin());
        this.sum = this.sum + s.getSum();
        this.avg = (this.avg * this.count + 
                s.getAvg() * s.getCount()) / (this.count + s.getCount());
        this.count = this.count + s.getCount();
        return this;
    }
                            
}
