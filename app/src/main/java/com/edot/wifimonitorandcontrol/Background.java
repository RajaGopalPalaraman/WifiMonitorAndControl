package com.edot.wifimonitorandcontrol;

import android.os.Handler;

/**
 * Created by Admin on 3/2/2018.
 */

public class Background extends Thread {

    private Handler handler;
    private String ip;
    private int port;
    private WifiHelper wifiHelper;

    public WifiHelper getWifiHelper() {
        return wifiHelper;
    }

    public Background(String ip, int port, Handler handler)
    {
        super();
        this.ip = ip;
        this.port = port;
        this.handler = handler;
    }

    @Override
    public void run() {
        super.run();
        try {
            wifiHelper = new WifiHelper(ip,port,handler);
            wifiHelper.startReception();
        } catch (IllegalAccessException e) {

        }
    }

    @Override
    public void interrupt() {
        super.interrupt();
        wifiHelper.closeConnection();
    }
}
