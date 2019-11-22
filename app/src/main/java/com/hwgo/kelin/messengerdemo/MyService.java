package com.hwgo.kelin.messengerdemo;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyService", "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyService", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyService", "onDestroy");
    }

    @SuppressLint("HandlerLeak")
    private Messenger messenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Message toClientMsg = Message.obtain();
            switch (msg.what) {
                case 1:
                    Toast.makeText(MyService.this, msg.arg1 + "", Toast.LENGTH_SHORT).show();
                    toClientMsg.arg1 = 888888;
                    try {
                        msg.replyTo.send(toClientMsg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:

            }
        }
    });
}
