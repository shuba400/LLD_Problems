package Logger;

public enum LogLevel {
    FATAL(1),
    ERROR(2),
    DEBUG(3),
    INFO(4);

    private final int value;
    LogLevel(int i) {
        this.value = i;
    }

    public int getValue(){
        return this.value;
    }

    public boolean isGreater(LogLevel other) {
        return this.value > other.value;
    }
}
