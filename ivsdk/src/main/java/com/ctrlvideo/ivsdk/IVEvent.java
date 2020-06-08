package com.ctrlvideo.ivsdk;

/**
 * Author by Jaiky, Date on 2020/5/28.
 */
public class IVEvent {

    /**
     * 事件，语音识别
     */
    public static final String EVENT_SPEECHRECOGN = "speechrecogn";

    /**
     * 事件，手势识别
     */
    public static final String EVENT_GESTURE = "gesture";



    //调用EventAction
    public interface EventAction {
        String INTERRUPT_EVENT = "interrupt_event";
    }

    public interface ErrorType {
        int INIT_FAILED = -101;
        int GET_CONFIG_FAILED = -102;
    }

}
