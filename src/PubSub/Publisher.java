package PubSub;

public class Publisher {
    private String id;
    private Controller controller;
    public Publisher(final String id,final Controller controller){
        this.id = id;
        this.controller = controller;
    }

    public void publish(String topicId,String message){
        System.out.println("Publisher " + id + " is publishing the message " + message);
        controller.publish(topicId,new Message(message));
    }
}
