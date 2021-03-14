package stud.devon.service;

import stud.devon.App;

import java.io.IOException;
import java.net.InetAddress;

public class ConnectionCheck {
    public static boolean checked = false;
    public static boolean connected = false;
    public static void checkServerConnection()
    {
        try {
             connected=InetAddress.getByName("mat.umk.pl").isReachable(10000);
        } catch (IOException e) {
           connected=false;
        }finally {
            synchronized (App.getSyncObj())
            {
                checked=true;
                App.getSyncObj().notify();
            }
        }
    }

    public static boolean isConnected() {
        return connected;
    }

    public static boolean isChecked() {
        return checked;
    }
}
