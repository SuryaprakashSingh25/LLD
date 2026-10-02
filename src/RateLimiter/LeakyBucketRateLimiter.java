package RateLimiter;

import java.util.concurrent.locks.ReentrantLock;

public class LeakyBucketRateLimiter implements RateLimiter{
    private final double capacity;
    private final double leakRateMs;
    private double waterLevel=0.0;
    private long lastLeakTime;
    private final ReentrantLock lock=new ReentrantLock();

    public LeakyBucketRateLimiter(double capacity, double leakRatePerSecond){
        this.capacity=capacity;
        this.leakRateMs=leakRatePerSecond/1000.0;
        this.lastLeakTime=System.currentTimeMillis();
    }

    @Override
    public boolean allow(){
        lock.lock();
        try{
            leak();
            if(waterLevel+1.0<=capacity){
                waterLevel+=1.0;
                return true;
            }
            return false;
        }
        finally {
            lock.unlock();
        }
    }

    private void leak(){
        long now=System.currentTimeMillis();
        double leaked=(now-lastLeakTime)*leakRateMs;
        if(leaked>0.0){
            waterLevel=Math.max(0.0,waterLevel-leaked);
            lastLeakTime=now;
        }
    }
}
