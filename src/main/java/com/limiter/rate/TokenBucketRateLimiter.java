//package com.limiter.rate;
//
//import java.util.concurrent.ConcurrentHashMap;
//
//public class TokenBucketRateLimiter implements RateLimiter{
//
//    private final long capacity;
//    private final double refillPerNano;
//    private final ConcurrentHashMap<String, Bucket> buckets=new ConcurrentHashMap<String, Bucket>();
//
//    public TokenBucketRateLimiter(long capacity,double refillPerSecond){
//        this.capacity=capacity;this.refillPerNano=refillPerSecond/1000000000;
//    }
//    @Override
//    public boolean tryAcquire(long key) {
//
//    Bucket b= buckets.computeIfAbsent(key,k->new Bucket(capacity,System.nanoTime()));
//    Synchro
//        return false;
//    }
//}
