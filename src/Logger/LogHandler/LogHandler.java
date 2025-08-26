package Logger.LogHandler;

import Logger.Appender.Appender;
import Logger.LogLevel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogHandler {

    public LogLevel logLevel;
    private final Appender appender;
    private LogHandler nextLogHandler;
    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public LogHandler(LogLevel logLevel, Appender appender){
        this.logLevel = logLevel;
        this.appender = appender;
        this.nextLogHandler = null;
    }
    public void setNextLogHandler(LogHandler logHandler){
        this.nextLogHandler = logHandler;
    }

    public void processMessage(LogLevel logLevel,String message){
        if(logLevel.isGreater(this.logLevel)){
            if(nextLogHandler == null){
                return;
            }
            nextLogHandler.processMessage(logLevel,message);
        } else {
            appender.appendLog(this.printLog(message));
        }
    }


    public String printLog(String message){
        //Time, Log Level Message
        return String.format("[%s %s] - %s", LocalDateTime.now().format(dateTimeFormatter),logLevel.name(),message);

    }


}
