package PubSub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class Controller {
    private Map<String,Topic> topicHashMap;
    private final Map<Topic, Map<Subscriber,TopicSubscriber>> topicSubscribers;
    private final ExecutorService executorService;

    public Controller(){
        topicHashMap = new ConcurrentHashMap<>();
        topicSubscribers = new ConcurrentHashMap<>();
        executorService = Executors.newCachedThreadPool();
    }

    void publish(String topicId,Message message){
        Topic topic = topicHashMap.get(topicId);
        synchronized (topic){
            topic.addMessage(message);
        }
    }

    void subscribe(Subscriber subscriber,String topicId){
        Topic topic = topicHashMap.get(topicId);
        TopicSubscriber topicSubscriber = new TopicSubscriber(topic,subscriber);
        if(!this.topicSubscribers.get(topic).containsKey(subscriber)) {
            this.topicSubscribers.get(topic).put(subscriber,topicSubscriber);
            executorService.submit(topicSubscriber);
        }
    }

    void createTopic(String topicId){
        Topic topic = new Topic(topicId);
        topicHashMap.put(topicId,topic);
        topicSubscribers.put(topic,new ConcurrentHashMap<>());
    }

    void resetOffSet(Subscriber subscriber,String topicId){
        Topic topic = topicHashMap.get(topicId);
        TopicSubscriber topicSubscriber = this.topicSubscribers.get(topic).get(subscriber);
        topicSubscriber.updateResetOffSet();
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
        System.out.println("Shutdown done");
    }


}
