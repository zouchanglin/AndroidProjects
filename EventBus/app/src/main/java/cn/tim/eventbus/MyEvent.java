package cn.tim.eventbus;

public class MyEvent {
    private boolean result;

    public MyEvent(boolean result) {
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
