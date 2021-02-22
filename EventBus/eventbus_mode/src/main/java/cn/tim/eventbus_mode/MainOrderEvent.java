package cn.tim.eventbus_mode;

public class MainOrderEvent {
    public final String threadInfo;

    public MainOrderEvent(String threadInfo) {
        this.threadInfo = threadInfo;
    }
}
