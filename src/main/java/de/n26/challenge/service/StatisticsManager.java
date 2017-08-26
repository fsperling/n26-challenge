package de.n26.challenge.service;

import de.n26.challenge.data.StatisticsStore;
import de.n26.challenge.model.Statistics;
import de.n26.challenge.model.Transaction;
import java.time.Instant;

/**
 *
 * @author felix
 */
public class StatisticsManager {

    private static StatisticsManager sm;
    private final StatisticsStore ss;

    private StatisticsManager() {
        this.ss = StatisticsStore.getInstance();
    }

    public static StatisticsManager getInstance() {
        if (sm == null) {
            sm = new StatisticsManager();
        }

        return sm;
    }

    public boolean add(Transaction tr) {
        boolean isValid = true;
        Instant now = Instant.now();

        Instant transactionTime = Instant.ofEpochMilli(tr.getTimestamp());
        if (now.minusSeconds(60).isBefore(transactionTime)) {
            ss.save(tr);
        } else {
            isValid = false;
        }
        return isValid;
    }

    public Statistics getTransactionStats() {
        return ss.getTransactionStats();
    }

    void clearStats() {
        ss.clearStats();
    }

}
