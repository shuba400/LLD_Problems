# Top Down Approach
We will go top down approach here with all the important things to be defined
1) Publisher - Mainly for id and .publish method
2) Subscriber - Mainly for id and .subscribe method and also processMessage function adding some sleep
3) Controller - Just a glue for a common interface, definatly not the most ideal but it makes implementation easy
4) Then you need topic for message queue and also need message class
5) Lately you stumpble upon how to manage TopicSubscribber relation - Basically eash subsriber topic have to be treated as
one entity , so that each subscriber of a topic and get the message. So we add this class and let it manage its own offset/interaction with subscriber
6) Also AtomicInteger internals can be demonstrated quite nicely here. Since example is straigtforward. 