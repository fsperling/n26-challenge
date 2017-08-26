package de.n26.challenge.data;

import de.n26.challenge.model.Statistics;
import de.n26.challenge.model.Transaction;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author felix
 */
public class StatisticsStore {
    private static StatisticsStore ts;
    ConcurrentHashMap<Integer, Statistics> transactionStats = new ConcurrentHashMap<>();

    private StatisticsStore() {}
       
    public static StatisticsStore getInstance() {
        if (ts == null)
            ts = new StatisticsStore();
        
        return ts;
    }

    public void save(Transaction tr) {
        Instant timestamp = Instant.ofEpochMilli(tr.getTimestamp());
        Integer epochSeconds = (int) timestamp.getEpochSecond();
        
        if (transactionStats.containsKey(epochSeconds)) {
            Statistics existing = transactionStats.get(epochSeconds);
            Statistics combined = existing.combineWith(tr);
            transactionStats.put(epochSeconds, combined);
        } else {
            transactionStats.put(epochSeconds, new Statistics(tr));
        }
    }

    public Statistics getTransactionStats() {
        Statistics stats = new Statistics();
        stats.setMin(Double.MAX_VALUE);
        
        Instant now = Instant.now();
        for(int i = 0; i < 60; i++) {
            Integer secs = (int) now.minus(i, ChronoUnit.SECONDS).getEpochSecond();
            Statistics s = transactionStats.get(secs);
            if (s != null) {
                stats.combineWith(s);
            }
        }
        
        return stats;
    }

    public void clearStats() {
        transactionStats = new ConcurrentHashMap<>();
    }
    
}