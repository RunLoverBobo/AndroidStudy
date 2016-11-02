package runlover.bobo.portabletest;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private USBBroadcastReceiver usbBroadcastReceiver;
    private Intent socketServiceIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        usbBroadcastReceiver = new USBBroadcastReceiver();

    }

    @Override
    public void onResume() {
        super.onResume();
        //注册USB状态接收器
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.hardware.usb.action.USB_STATE");
        registerReceiver(usbBroadcastReceiver, intentFilter);
    }

    @Override
    public void onStart() {
        super.onStart();
        //注册服务
        socketServiceIntent = new Intent();
        socketServiceIntent.setAction("cn.bobo.SocketServer");
        startService(socketServiceIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销USB状态接收器
        unregisterReceiver(usbBroadcastReceiver);
        //注销服务
        stopService(socketServiceIntent);
    }
}
