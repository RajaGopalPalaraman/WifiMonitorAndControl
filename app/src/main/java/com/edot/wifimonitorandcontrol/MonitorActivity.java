package com.edot.wifimonitorandcontrol;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;

public class MonitorActivity extends AppCompatActivity {

    private TextView[] fields = new TextView[5];
    private int index=0;
    private Background background;
    private OutputStream outputStream;
    private boolean on1 = false;
    private boolean on2 = false;
    private boolean on3 = false;
    private final String on1_data = "*1";
    private final String off1_data = "*2";
    private final String on2_data = "*3";
    private final String off2_data = "*4";
    private final String on3_data = "*5";
    private final String off3_data = "*6";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
        fields[0] = findViewById(R.id.f1);
        fields[1] = findViewById(R.id.f2);
        fields[2] = findViewById(R.id.f3);
        fields[3] = findViewById(R.id.f4);
        fields[4] = findViewById(R.id.f5);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final Intent intent = getIntent();
        if(intent != null)
        {
            String IP = intent.getStringExtra("ip");
            int port = intent.getIntExtra("port", 8080);
            background = new Background(IP, port,new Handler(Looper.getMainLooper()){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what)
                    {
                        case WifiHelper.ERROR:
                            Toast.makeText(MonitorActivity.this,R.string.error, Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                        case WifiHelper.CONNECTED:
                            Toast.makeText(MonitorActivity.this,R.string.connected, Toast.LENGTH_SHORT).show();
                            outputStream = background.getWifiHelper().getOutputStream();
                            break;
                        case WifiHelper.CONNECTION_TERMINATED:
                            Toast.makeText(MonitorActivity.this,R.string.terminated, Toast.LENGTH_SHORT).show();
                            break;
                        case WifiHelper.MESSAGE:
                            index=index%5;
                            if (index == 3 || index == 4)
                            {
                                String s = (String) msg.obj;
                                if ("0".equals(s))
                                {
                                    fields[(index)].setText(R.string.low);
                                }
                                else
                                {
                                    fields[(index)].setText(R.string.high);
                                }
                            }
                            else {
                                fields[(index)].setText((String) msg.obj);
                            }
                            index++;
                    }
                }
            });
            background.start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        background.interrupt();
    }

    public void onClick1(final View view) {
        if(on1)
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        if (outputStream != null) {
                            outputStream.write(off1_data.getBytes());
                            on1 = false;
                        }
                    } catch (IOException e) {

                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    if(!on1)
                    ((Button)view).setText(R.string.fan_on);
                }
            }.execute();
        else
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        if (outputStream != null) {
                            outputStream.write(on1_data.getBytes());
                            on1 = true;
                        }
                    } catch (IOException e) {

                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    if(on1)
                    ((Button)view).setText(R.string.fan_off);
                }
            }.execute();

    }

    public void onClick2(final View view) {
        if(on2)
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        if (outputStream != null) {
                            outputStream.write(off2_data.getBytes());
                            on2 = false;
                        }
                    } catch (IOException e) {

                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    if(!on2)
                        ((Button)view).setText(R.string.load_on);
                }
            }.execute();
        else
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        if (outputStream != null) {
                            outputStream.write(on2_data.getBytes());
                            on2 = true;
                        }
                    } catch (IOException e) {

                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    if(on2)
                        ((Button)view).setText(R.string.load_off);
                }
            }.execute();

    }

    public void onClick3(final View view) {
        if(on3)
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        if (outputStream != null) {
                            outputStream.write(off3_data.getBytes());
                            on3 = false;
                        }
                    } catch (IOException e) {

                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    if(!on3)
                        ((Button)view).setText(R.string.pump_on);
                }
            }.execute();
        else
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        if (outputStream != null) {
                            outputStream.write(on3_data.getBytes());
                            on3 = true;
                        }
                    } catch (IOException e) {

                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    if(on3)
                        ((Button)view).setText(R.string.pump_off);
                }
            }.execute();

    }

}
