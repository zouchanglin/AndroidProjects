package cn.tim.custom_application;

import com.squareup.otto.Bus;

public class BusProvider {
    private BusProvider(){}

    private static final Bus bus = new Bus();

    public static Bus getBus(){
        return bus;
    }
}
