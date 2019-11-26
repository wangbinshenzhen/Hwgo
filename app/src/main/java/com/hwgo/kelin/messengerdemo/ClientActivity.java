package com.hwgo.kelin.messengerdemo;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.hwgo.kelin.R;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class ClientActivity extends AppCompatActivity {
    private Messenger serverMsger;
    private boolean isBond;
    private boolean canUnbind;
    private ServiceConnection serviceConnection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_layout);
        findViewById(R.id.button).setOnClickListener(v -> {
            if (isBond) {
                Message toServerMsg = Message.obtain();
                toServerMsg.what = 1;
                toServerMsg.arg1 = 6666;
                toServerMsg.replyTo = clientMessenger;
                try {
                    serverMsger.send(toServerMsg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.button2).setOnClickListener(v -> {
            stopBindService();
        });
        findViewById(R.id.button4).setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction("com.wangbin.kelin.messengerdemo.myservice");
            intent.setPackage(getPackageName());
            stopService(intent);
        });
        findViewById(R.id.button5).setOnClickListener(v -> {
            bindService();
        });
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) {

            }
        }).subscribe();
        bindService();

    }

    /**
     * 绑定服务
     */
    private void bindService() {
        Intent intent = new Intent();
        intent.setAction("com.wangbin.kelin.messengerdemo.myservice");
        intent.setPackage(getPackageName());
        canUnbind = bindService(intent, (serviceConnection = new MyServiceConnection()), BIND_AUTO_CREATE);
    }

    private class MyServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("kelin", "服务绑定成功,onServiceConnected name=" + name);
            serverMsger = new Messenger(service);
            isBond = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("kelin", "服务分离,onServiceDisconnected name=" + name);
            serverMsger = null;
            isBond = false;
        }

        @Override
        public void onBindingDied(ComponentName name) {
            Log.d("kelin", "服务消失,onBindingDied name=" + name);
        }

        @Override
        public void onNullBinding(ComponentName name) {
            Log.d("kelin", "onNullBinding name=" + name);
        }
    }

    @SuppressLint("HandlerLeak")
    private Messenger clientMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(ClientActivity.this, msg.arg1 + "", Toast.LENGTH_LONG).show();
            super.handleMessage(msg);
        }
    });

    private void stopBindService() {
        if (isBond && canUnbind) {
            isBond = false;
            canUnbind = false;
            unbindService(serviceConnection);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopBindService();

    }
}
