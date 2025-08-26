package Logger.Appender;

public class ConsoleAppender implements Appender {

    @Override
    public void appendLog(String message) {
        System.out.println(message);
    }
}
