package com.itman.appkeepalivedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        appKeepAliveService();
    }


    private void appKeepAliveService() {
        Intent intent = new Intent(this, AppKeepAliveService.class);
        stopService(intent);
        startService(intent);
    }
}
