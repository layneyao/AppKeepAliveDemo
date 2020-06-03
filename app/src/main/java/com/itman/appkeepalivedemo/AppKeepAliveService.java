package com.itman.appkeepalivedemo;

import android.provider.Settings;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;


import androidx.annotation.Nullable;

import java.util.List;

public class AppKeepAliveService extends Service {

    private final long delayMillis = 10000;

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    while (true) {
                        boolean isFrontDesk = isFrontDesk(APP.getInstance());
                        if (!isFrontDesk) {
                            restartApp(APP.getInstance());
                        }
                        Log.e("APPLOCK", "APP是否在前台" + isFrontDesk);
                        sleep(delayMillis);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 判断应用是否是在前台
     */
    public boolean isFrontDesk(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return false;
                } else {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 重启应用
     */
    public void restartApp(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());//再此之前可以做些退出等操作
        System.exit(0);
    }
}
