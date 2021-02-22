package cn.tim.eventbus_mode;

public class PostingEvent {
    public final String threadInfo;

    public PostingEvent(String threadInfo) {
        this.threadInfo = threadInfo;
    }
}
