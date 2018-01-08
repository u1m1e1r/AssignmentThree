package com.example.hp.broadcastthroughmanifest;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.audiofx.BassBoost;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    TextView textView;
    Switch aSwitch,bSwitch;
    EditText editText;
    NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sp = getSharedPreferences("data",Context.MODE_PRIVATE);
                editor = sp.edit();
                editor.putString("key",editText.getText().toString());
                editor.commit();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SwitchEvent event){
        Intent intent = event.i;
        Context context = event.c;
        textView = (TextView) findViewById(R.id.textView);
        aSwitch = (Switch) findViewById(R.id.switch1);
        if(intent.getAction().equals("android.intent.action.AIRPLANE_MODE")){

            aSwitch.setChecked(intent.getBooleanExtra("state",false));
            boolean a=intent.getBooleanExtra("state",false);
            if(intent.getBooleanExtra("state",false)==true){
                mBuilder.setSmallIcon(R.drawable.ic_launcher).setContentTitle("Airplane Mode").setContentText("AirPlane Mode on");
                NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                mNotifyMgr.notify(0,mBuilder.build());
            }else{
                mBuilder.setSmallIcon(R.drawable.ic_launcher).setContentTitle("AirPlaneMode").setContentText("AirPlane mode off");
                NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                mNotifyMgr.notify(0,mBuilder.build());
            }
        }
        else if(intent.getAction().equals("android.intent.action.BATTERY_LOW")){
            textView.setText("battery low");
        }else if(intent.getAction().equals("android.intent.action.BATTERY_High")){
            textView.setText("battery high");
        }
    }
    public void wifistate(View view){
        bSwitch = (Switch) findViewById(R.id.switch2);
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if(bSwitch.isChecked()==true){
            wifiManager.setWifiEnabled(true);
            mBuilder.setSmallIcon(R.drawable.ic_launcher).setContentTitle("Wifi Status").setContentText("Wifi Device ON");
            NotificationManager mNotifyMgr = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotifyMgr.notify(0,mBuilder.build());
        }
        else {
            wifiManager.setWifiEnabled(false);
            mBuilder.setSmallIcon(R.drawable.ic_launcher).setContentTitle("Wifi Status").setContentText("Wifi Device OFF");
            NotificationManager mNotifyMgr = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotifyMgr.notify(0,mBuilder.build());
        }

    }


}
