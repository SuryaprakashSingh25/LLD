package RateLimiter;

import java.util.concurrent.locks.ReentrantLock;

public class TokenBucketRateLimiter implements RateLimiter{
    private final double capacity;
    private final double refillRateMs;
    private double tokens;
    private long lastRefillTime;
    private final ReentrantLock lock=new ReentrantLock();

    public TokenBucketRateLimiter(double capacity, double refillRatePerSecond){
        this.capacity=capacity;
        this.refillRateMs=refillRatePerSecond/1000.0;
        this.tokens=capacity;
        this.lastRefillTime=System.currentTimeMillis();
    }

    @Override
    public boolean allow(){
        lock.lock();
        try{
            refill();
            if(tokens>=1.0){
                tokens-=1.0;
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    private void refill(){
        long now=System.currentTimeMillis();
        double addToken=(now-lastRefillTime)*refillRateMs;
        if(addToken>0.0){
            tokens=Math.min(capacity,tokens+addToken);
            lastRefillTime=now;
        }
    }
}
