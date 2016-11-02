package runlover.bobo.portabletest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by lionking on 16-11-2.
 */

public class SocketServer extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        System.out.println("on create");

        new Thread(new Runnable() {
            @Override
            public void run() {
                ServerSocket serverSocket= null;
                Socket socket=null;
                try {
                    serverSocket = new ServerSocket(12345);
                    socket=serverSocket.accept();
                    BufferedOutputStream out=new BufferedOutputStream(socket.getOutputStream());
                    out.write("hello pc".getBytes());
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
