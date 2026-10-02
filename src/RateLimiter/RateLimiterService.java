package RateLimiter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RateLimiterService {
    private final Map<String,RateLimiter> limiters=new ConcurrentHashMap<>();
    private boolean failOpen=true;

    public void setFailOpen(boolean failOpen){
        this.failOpen=failOpen;
    }

    public void registerClient(String clientId, String type, double limit, double rate){
        if("token_bucket".equalsIgnoreCase(type)){
            limiters.put(clientId,new TokenBucketRateLimiter(limit,rate));
        }
        else if("sliding_window".equalsIgnoreCase(type)){
            limiters.put(clientId,new SlidingWindowLogLimiter((int) limit, (long) rate));
        }
        else if("leaky_bucket".equalsIgnoreCase(type)){
            limiters.put(clientId,new LeakyBucketRateLimiter(limit,rate));
        }
    }

    public boolean isAllowed(String clientId){
        RateLimiter limiter=limiters.get(clientId);
        if(limiter==null){
            return failOpen;
        }
        return limiter.allow();
    }
}
