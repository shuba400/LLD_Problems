package Logger;

import Logger.Appender.Appender;
import Logger.Appender.ConsoleAppender;
import Logger.LogHandler.LogHandler;

public class Logger {
    private LogHandler logHandler;
    public Logger(){
        intializeLogger(new ConsoleAppender(),LogLevel.INFO);
    }

    public Logger(LogLevel logLevel){
        intializeLogger(new ConsoleAppender(),logLevel);
    }

    public Logger(Appender appender,LogLevel leastLoglevel){
        intializeLogger(appender,leastLoglevel);
    }

    private void intializeLogger(Appender appender, LogLevel greatestLogLevel){
        LogHandler currentLogHandler = null;
        LogHandler prevLogHandler;
        this.logHandler = null;
        LogLevel[] levels = LogLevel.values();
        for(LogLevel logLevel : levels){
            if(logLevel.isGreater(greatestLogLevel)){
                break;
            }
            prevLogHandler = currentLogHandler;
            currentLogHandler = new LogHandler(logLevel,appender);
            if(this.logHandler == null){
                this.logHandler = currentLogHandler;
            } else {
                prevLogHandler.setNextLogHandler(currentLogHandler);
            }
        }
    }

    public void logMessage(LogLevel logLevel,String message){
        logHandler.processMessage(logLevel,message);
    }
}
