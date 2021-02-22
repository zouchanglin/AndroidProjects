package cn.tim.eventbus_mode;

public class BackgroundEvent {
    public final String threadInfo;

    public BackgroundEvent(String threadInfo) {
        this.threadInfo = threadInfo;
    }
}
