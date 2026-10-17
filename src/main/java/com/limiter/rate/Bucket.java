package com.limiter.rate;

public class Bucket {
    private final double capacity;
    private final double refillPerSecond;
    private double tokens;
    private long lastRefillNanos;

    Bucket(double capacity, double debit, long lastRefillNanos)  {
        if(capacity<=0 || debit<=0)
            throw new IllegalArgumentException("capacity and lastRefillNanos must be > 0");
        tokens=capacity;
        this.capacity = capacity;
        this.refillPerSecond = debit;
        this.lastRefillNanos = lastRefillNanos;
    }



    public boolean tryConsume(long nowNano){
        long elapsedTime=Math.max(0,nowNano-lastRefillNanos);
        double gained=elapsedTime*refillPerSecond/1_000_000_000.0;
        tokens=Math.min(capacity,gained+tokens);
        lastRefillNanos=nowNano;
        if (tokens>=1){
            tokens-=1;
            return true;
        }
        return false;
    }
}
