package RateLimiter;

import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.locks.ReentrantLock;


class BucketCronJob implements Runnable {
    final private BucketRateLimiter bucketRateLimiter;

    public BucketCronJob(BucketRateLimiter bucketRateLimiter){
        this.bucketRateLimiter = bucketRateLimiter;
    }

    @Override
    public void run() {
        while (true){
            bucketRateLimiter.refillBucket();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

public class BucketRateLimiter implements RateLimiter{
    private volatile Integer BUCKET_CAPACITY;
    private volatile int BUCKET_SIZE;
    private volatile int PER_SECOND_REFRESH;
    private final ReentrantLock lock = new ReentrantLock();

    public BucketRateLimiter(Map<String, Integer> config){
        updateConfig(config);
        final BucketCronJob bucketCronJob = new BucketCronJob(this);
        Thread backgroundJob = new Thread(bucketCronJob);
        backgroundJob.start();
    }

    public void updateConfig(Map<String,Integer> config){
        lock.lock();
        try {
            this.BUCKET_CAPACITY = config.get("bucket_capacity");
            this.PER_SECOND_REFRESH = config.get("per_second_refresh");
            this.BUCKET_SIZE = 0;
        } finally {
            lock.unlock();
        }
    }

    public void refillBucket(){
        lock.lock();
        try {
            int tmp = BUCKET_SIZE;
            tmp = Math.min(this.BUCKET_CAPACITY,tmp + this.PER_SECOND_REFRESH);
            this.BUCKET_SIZE = tmp;
        } finally {
            lock.unlock();
        }
    }


    @Override
    public boolean accessRequest() {
        lock.lock();
        try {
            int tmp = BUCKET_SIZE;
            if(tmp == 0){
                return false;
            }
            tmp -= 1;
            this.BUCKET_SIZE = tmp;
            return true;
        } finally {
            lock.unlock();
        }
    }
}
