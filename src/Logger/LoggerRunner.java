package Logger;

public class LoggerRunner {
    Logger logger;
    public LoggerRunner(){
        logger = new Logger(LogLevel.ERROR);
    }

    public void run(){
        logger.logMessage(LogLevel.DEBUG,"This is a debug message");
        logger.logMessage(LogLevel.ERROR,"This is an error message");
        logger.logMessage(LogLevel.INFO,"This is an info message");
    }
}
