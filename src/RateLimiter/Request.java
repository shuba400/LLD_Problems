package RateLimiter;

public class Request implements Runnable {
    RateLimiter rateLimiter;
    int id;

    public Request(final RateLimiter rateLimiter,final int id){
        this.rateLimiter = rateLimiter;
        this.id = id;
    }

    @Override
    public void run() {
        String val = "NOT Accepted";
        if(rateLimiter.accessRequest()){
            val = "Accepted";
        }
        System.out.printf("Request ID %d : %s\n",this.id,val);
    }
}
