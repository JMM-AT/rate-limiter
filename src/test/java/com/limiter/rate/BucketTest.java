package com.limiter.rate;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class BucketTest {
    public static final long SEC= 1_000_000_000L;
    public static void drain(Bucket b, int n, long nowNanos){
        for(int i=0; i<n ; i++){
            assertTrue(b.tryConsume(nowNanos), "appel " + (i + 1) + " devrait réussir");
        }
    }

    @Test

    void newBucketHoldsExactlyItsCapacity(){
        Bucket b=new Bucket(10,2,0);
        drain(b,10,0);
        assertFalse(b.tryConsume(0));
    }

    @Test
    void refillsAfterThreeSeconds(){
        Bucket b=new Bucket(10,2,0);
        drain(b,10,0);
        // 3 s x 2 tokens/s = 6 tokens
        drain(b,6,3*SEC);
        //in the same time
        assertFalse(b.tryConsume(3*SEC));
    }

    @Test
    void fractionsOfTokensAccumulate() {
        Bucket b = new Bucket(10, 2, 0);
        drain(b, 10, 0);

        // 0,25 s -> 0,5 teken : failed
        assertFalse(b.tryConsume(SEC / 4));
        // encore 0,25 s -> 0,5 token , total 1,0 : accepted
        assertTrue(b.tryConsume(SEC / 2));
    }

    @Test
    void neverExceedsCapacityAfterLongPause() {
        Bucket b = new Bucket(10, 2, 0);
        drain(b, 10, 0);

        // 100 s -> 200 tokens earned, but  capacity is 10
        drain(b, 10, 100 * SEC);
        assertFalse(b.tryConsume(100 * SEC));
    }

    @Test
    void clockGoingBackwardsGainsNothing() {
        Bucket b = new Bucket(10, 2, 5 * SEC);
        drain(b, 10, 5 * SEC);

        assertFalse(b.tryConsume(4 * SEC));
    }

    @Test
    void rejectsInvalidCapacity() {
        assertThrows(IllegalArgumentException.class, () -> new Bucket(0, 2, 0));
        assertThrows(IllegalArgumentException.class, () -> new Bucket(-1, 2, 0));
    }

    @Test
    void rejectsInvalidRefillRate() {
        assertThrows(IllegalArgumentException.class, () -> new Bucket(10, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> new Bucket(10, -1, 0));
    }
}