package com.limiter.rate;

public interface RateLimiter {
    boolean tryAcquire(long key);
}