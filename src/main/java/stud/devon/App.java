package stud.devon;

import javafx.application.Application;
import javafx.stage.Stage;
import stud.devon.service.ConnectionCheck;
import stud.devon.service.DataBase;
import stud.devon.service.Gui;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    public static Gui gui;
    private static Object syncObj;

    @Override
    public void start(Stage stage) throws IOException {
        gui = new Gui(stage);
    }

    public static Object getSyncObj() {
        return syncObj;
    }

    public static void main() {
        syncObj=new Object();
        Thread guiThread = new Thread(Application::launch);
        Thread connectionThread = new Thread(ConnectionCheck::checkServerConnection);
        Thread hibernateThread = new Thread(new DataBase());
        connectionThread.start();
        synchronized (syncObj)
        {
            while(!ConnectionCheck.isChecked())
            {
                try {
                    syncObj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        guiThread.start();
        if(ConnectionCheck.isConnected()) {
            hibernateThread.start();
        }
    }
}