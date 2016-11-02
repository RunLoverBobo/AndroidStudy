package runlover.bobo.portabletest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by lionking on 16-10-31.
 */

public class USBBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("android.hardware.usb.action.USB_STATE")){
            if(intent.getExtras().getBoolean("connected")){
                Toast.makeText(context,"usb连接",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(context,"usb断开",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
