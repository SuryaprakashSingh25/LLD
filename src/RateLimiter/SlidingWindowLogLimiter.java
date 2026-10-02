package RateLimiter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class SlidingWindowLogLimiter implements RateLimiter{
    private final int limit;
    private final long windowSizeMs;
    private final List<Long> requestLog=new ArrayList<>();
    private final ReentrantLock lock=new ReentrantLock();

    public SlidingWindowLogLimiter(int limit,long windowSizeMs){
        this.limit=limit;
        this.windowSizeMs=windowSizeMs;
    }

    @Override
    public boolean allow(){
        lock.lock();
        try{
            long now=System.currentTimeMillis();
            long boundary=now-windowSizeMs;
            requestLog.removeIf(t -> t<boundary);
            if(requestLog.size()<limit){
                requestLog.add(now);
                return true;
            }
            return false;
        }
        finally {
            lock.unlock();
        }
    }
}
