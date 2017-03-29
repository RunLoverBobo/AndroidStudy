package cn.bobo.raspberry.M;

import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 接受树莓派数据线程
 * Created by lionking on 3/4/17.
 */
public class GetDataFromRaspberryThread implements Runnable {
    private static final String HOST = "192.168.0.5";
    private static final int PORT = 9999;
    private Handler mHandler = null;

    public GetDataFromRaspberryThread(Handler mHandler) {
        this.mHandler = mHandler;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(HOST, PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {
                if(in !=null) {
                    String data = in.readLine();
                    Message msg = Message.obtain();
                    msg.obj = data;
                    mHandler.sendMessage(msg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
