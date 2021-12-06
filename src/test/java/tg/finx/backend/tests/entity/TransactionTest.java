package tg.finx.backend.tests.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tg.finx.backend.entity.Transaction;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {
    Transaction t;
    LocalDateTime time;

    @BeforeEach
    void setUp() {
        time = LocalDateTime.now();
        t = new Transaction(
                time,
                "BUY",
                "AAPL",
                10.0,
                "USD",
                1.3122,
                0.0,
                10000.0
        );
    }

    @Test
    void setTime() {
        LocalDateTime newTime = time.plusHours(4);
        t.setTime(newTime);
        assertEquals(newTime, t.getTime());
    }

    @Test
    void setType() {
        t.setType("SELL");
        assertEquals("SELL", t.getType());
    }


    @Test
    void setTicker() {
        t.setTicker("CNR.TO");
        assertEquals("CNR.TO", t.getTicker());
    }


    @Test
    void setShares() {
        t.setShares(100.0);
        assertEquals(100.0, t.getShares());
        assertEquals(100.0, t.getCostPerShare());
    }

    @Test
    void setCurrency() {
        t.setCurrency("CAD");
        assertEquals("CAD", t.getCurrency());
    }

    @Test
    void setFxRate() {
        t.setFxRate(1.0);
        assertEquals(1.0, t.getFxRate());
    }

    @Test
    void setTotalAmount() {
        t.setTotalAmount(9000);
        assertEquals(9000.0, t.getTotalAmount());
        assertEquals(900, t.getCostPerShare());
    }

    @Test
    void setFee() {
        t.setFee(4.99);
        assertEquals(4.99, t.getFee());
    }
}