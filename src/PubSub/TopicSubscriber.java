package PubSub;

import java.util.concurrent.atomic.AtomicInteger;

public class TopicSubscriber implements Runnable {
    Topic topic;
    Subscriber subscriber;
    private AtomicInteger offset; //ToD- Investigate why not using atomic integer here will break stuff when you call updateReset. Might some caching issue
    int k;

    public TopicSubscriber(Topic topic,Subscriber subscriber){
        this.topic = topic;
        this.subscriber = subscriber;
        this.offset = new AtomicInteger();
    }

    @Override
    public void run() {
        System.out.println("We are in ts " + topic.topicId + " " + subscriber.id);
        while (true) {

            while (offset.get() < topic.getQueueSize()) {
                try {
                    subscriber.processMessage(topic.getMessageAtIndex(offset.get()));
                    offset.getAndIncrement();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void updateResetOffSet(){
        offset.set(0);
    }
}
