package RateLimiter;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
Leaky Bucket Implementation
Request ---> LB (rate limiter) ---> Server

 */
public class RateLimiterRunner {
    ExecutorService executorService;
    RateLimiter rateLimiter;
    public RateLimiterRunner(){
        Map<String,Integer> config = new HashMap<>();
        config.put("bucket_capacity",10);
        config.put("per_second_refresh",2);
        this.executorService = Executors.newFixedThreadPool(10);
        this.rateLimiter = new BucketRateLimiter(config);
    }

    public void run() throws InterruptedException {
        int id = 0;
        Thread.sleep(1000); //wait for thread runner to fillup
        id = createBurstRequest(8,id);
        Thread.sleep(5000);
        id = createBurstRequest(11,id);
    }

    int createBurstRequest(int num_of_request,int str_id){
        System.out.printf("Sending out %d concurrent Request\n",num_of_request);
        for(int i = 0; i < num_of_request; i++){
            executorService.submit(new Request(rateLimiter,str_id + i));
        }
        return str_id + num_of_request;
    }
}
