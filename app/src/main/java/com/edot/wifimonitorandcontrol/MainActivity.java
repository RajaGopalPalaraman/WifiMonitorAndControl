package com.edot.wifimonitorandcontrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        try {
            startActivity(new Intent(this, MonitorActivity.class)
                    .putExtra("ip", ((EditText) findViewById(R.id.IPAddr)).getText().toString())
                    .putExtra("port", Integer.parseInt(((EditText) findViewById(R.id.PortNo)).getText().toString())));
        }
        catch (Exception e) {
            Toast.makeText(this,"Fill all Fields", Toast.LENGTH_SHORT).show();
        }
    }

}
