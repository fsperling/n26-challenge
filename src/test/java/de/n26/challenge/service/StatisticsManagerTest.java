package de.n26.challenge.service;

import de.n26.challenge.model.Statistics;
import de.n26.challenge.model.Transaction;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author felix
 */
public class StatisticsManagerTest {
    
    public StatisticsManagerTest() {
    }
    
    @After
    public void statisticsStore() {
        StatisticsManager.getInstance().clearStats();
    }

    @Test
    public void testThatCurrentTransactionIsAdded() {
        Transaction tr = new Transaction(100.0, System.currentTimeMillis());
        StatisticsManager sm = StatisticsManager.getInstance();
        assertTrue(sm.add(tr));
        assertEquals(1L, sm.getTransactionStats().getCount());
        assertEquals(tr.getAmount(), sm.getTransactionStats().getAvg(), 0.1);
    }

    @Test
    public void testThatOldTransactionIsRejected() {
        Instant now = Instant.now();
        Instant fiveMinOld = now.minus(5, ChronoUnit.MINUTES);
        Transaction tr = new Transaction(100.0, fiveMinOld.toEpochMilli());
        StatisticsManager sm = StatisticsManager.getInstance();
        assertFalse(sm.add(tr));
        assertEquals(0L, sm.getTransactionStats().getCount());
    }

    @Test
    public void testThatStatsAreCalculatedCorrectly() {
        StatisticsManager sm = StatisticsManager.getInstance();
        Transaction tr = new Transaction(100.0, System.currentTimeMillis());
        sm.add(tr);
        tr.setAmount(50.0);
        sm.add(tr);
        tr.setAmount(200.0);
        tr.setTimestamp(System.currentTimeMillis() - 1000);   // equals 1 secs ago
        sm.add(tr);
        Statistics stats = sm.getTransactionStats();
        assertEquals(50.0, stats.getMin(), 0.1);
        assertEquals(200.0, stats.getMax(), 0.1);
        assertEquals(3, stats.getCount());
        assertEquals(350.0, stats.getSum(), 0.1);
        assertEquals((50+100+200)/3.0, stats.getAvg(), 0.1);
    }
   
    @Test
    public void testThatOldTransactionExpireFromStats() throws InterruptedException {
        StatisticsManager sm = StatisticsManager.getInstance();
        Instant now = Instant.now();
        Instant almostExpired = now.minusSeconds(59);        // stats contain only transactions of the last 6 secs
        Transaction tr = new Transaction(100.0, almostExpired.toEpochMilli());
        assertTrue(sm.add(tr));
        assertEquals(1, sm.getTransactionStats().getCount());
        TimeUnit.SECONDS.sleep(2);
        assertEquals(0, sm.getTransactionStats().getCount());
    }
}
