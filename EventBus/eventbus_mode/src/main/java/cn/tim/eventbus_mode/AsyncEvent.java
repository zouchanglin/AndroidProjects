package cn.tim.eventbus_mode;

public class AsyncEvent {
    public final String threadInfo;

    public AsyncEvent(String threadInfo) {
        this.threadInfo = threadInfo;
    }
}
