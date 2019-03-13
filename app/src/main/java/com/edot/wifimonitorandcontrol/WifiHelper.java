package com.edot.wifimonitorandcontrol;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Admin on 3/1/2018.
 */

public class WifiHelper {

    public static final int ERROR = 0;
    public static final int CONNECTED = 1;
    public static final int CONNECTION_TERMINATED = 2;
    public static final int MESSAGE = 3;

    private boolean showError = true;
    private Handler handler;
    private InputStream inputStream;
    private OutputStream outputStream;
    private Socket socket;
    private String ip;
    private int port;

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public WifiHelper(String ip, int port, Handler handler)
    {
        this.ip = ip;
        this.port = port;
        this.handler = handler;
    }

    public void makeConnection()
    {
        if(socket !=null)
            closeConnection();
        try {
            socket = new Socket(ip,port);
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            handler.sendEmptyMessage(CONNECTED);
        }
        catch (IOException e) {
            handler.sendEmptyMessage(ERROR);
        }
    }

    public void closeConnection()
    {
        try {
            if(socket != null) {
                showError = false;
                socket.close();
            }
            handler.sendEmptyMessage(CONNECTION_TERMINATED);
        }
        catch (IOException e) {

        }
    }

    public void startReception() throws IllegalAccessException {
        if(Looper.myLooper()== Looper.getMainLooper())
            throw new IllegalAccessException();
        else {
            makeConnection();
            try {
                int i;
                StringBuilder builder = new StringBuilder();
                if(inputStream == null)
                    return;
                while ((i=inputStream.read())>0)
                {
                    if(i=='$') {
                        int temp = builder.length();
                        if (temp != 0) {
                            handler.obtainMessage(MESSAGE, builder.toString()).sendToTarget();
                            builder.delete(0, temp);
                        }
                    }
                    else
                        builder.append(i-48);
                    Log.d("ThreadBack",builder.toString());
                }
            } catch (IOException e) {
                if (showError) {
                    handler.sendEmptyMessage(ERROR);
                }
            }
        }
    }

}
