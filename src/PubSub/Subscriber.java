package PubSub;

public class Subscriber {
    public final String id;
    private final Controller controller;

    public Subscriber(String id,final Controller controller){
        this.id = id;
        this.controller = controller;
    }

    public void subscribe(String topicId){
        controller.subscribe(this,topicId);

        return;
    }

    public synchronized void processMessage(Message mssg) throws InterruptedException {
        System.out.println("Consumer " + id + " is consuming message " + mssg.getMessage());
        Thread.sleep(1000); //process message;
        return;
    }
}
