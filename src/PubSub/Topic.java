package PubSub;

import java.util.ArrayList;
import java.util.List;

public class Topic {
    String topicId;
    List<Message> queue;

    public Topic(String topicId){
        this.topicId = topicId;
        queue = new ArrayList<>();
    }

    public int getQueueSize(){
        return this.queue.size();
    }

    public Message getMessageAtIndex(int idx){
        if(idx < 0 || idx > this.queue.size()){
            return new Message("");
        }
        return this.queue.get(idx);
    }

    public void addMessage(Message message){
        this.queue.add(message);
    }
}
