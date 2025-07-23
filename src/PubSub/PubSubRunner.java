package PubSub;


/*
    TopicSubscriber;
 */
public class PubSubRunner {
    Controller controller;

    public PubSubRunner(){
        controller = new Controller();
    }

    public void run(){
        Publisher a = new Publisher("a",controller);
        Publisher b = new Publisher("b",controller);

        Subscriber c_sub = new Subscriber("c_sub",controller);
        Subscriber d_sub = new Subscriber("d_sub",controller);

        String topic_a = "Topic A";
        String topic_b = "Topic B";

        controller.createTopic(topic_a);
        controller.createTopic(topic_b);

        c_sub.subscribe(topic_a);
        c_sub.subscribe(topic_b);
        d_sub.subscribe(topic_b);

        a.publish(topic_a,"message 1");
        a.publish(topic_a,"message 2");
        b.publish(topic_b,"message 3");

        b.publish(topic_b,"message 4");
        b.publish(topic_a,"message 5");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        controller.resetOffSet(c_sub,topic_a);
//        while (true){
//
//        }
        try {
            Thread.sleep(200000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        controller.shutdown();
    }
}
