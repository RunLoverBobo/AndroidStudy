package cn.bobo.raspberry;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by lionking on 3/4/17.
 */
public class GetDataFromRaspberryThread implements Runnable {

    private static final String HOST = "192.168.0.5";
    private static final int PORT = 9999;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private Handler mHandler = null;

    GetDataFromRaspberryThread(Handler mHandler) {
        this.mHandler = mHandler;
    }

    @Override
    public void run() {
        try {
            Log.i("i", "Thread");
            Socket socket = new Socket(HOST, PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {
                if(in!=null) {
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
