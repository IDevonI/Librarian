package stud.devon.controllers;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;
import stud.devon.service.DataBase;
import stud.devon.service.Gui;

import java.io.IOException;

public class StartUpController {

    @FXML
    private Label startingText;

    @FXML
    public void initialize() {
        fadeEffect();
        Runnable toLogin = () -> {
                try {
                    Gui.setRoot("loginMenuView");
                } catch (IOException e) {
                    e.printStackTrace();
                }
        };
        Thread thread=new Thread(()-> {
            while (!DataBase.isHibernateLoaded()) {
                try {
                        Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Platform.runLater(toLogin);
        });
        thread.start();
    }

    private void fadeEffect() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(startingText);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.setAutoReverse(true);
        fadeTransition.play();
    }
}
