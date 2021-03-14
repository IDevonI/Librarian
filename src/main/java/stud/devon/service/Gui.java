package stud.devon.service;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import stud.devon.App;
import stud.devon.controllers.InfoBoxController;

import java.io.IOException;

public class Gui {

    private static Scene scene;
    private static Stage stage;
    private static FXMLLoader fxmlLoader;

    public Gui(Stage stageGiven) throws IOException {
        if(ConnectionCheck.isConnected()) {
            stage = stageGiven;
            scene = new Scene(loadFXML("startUpView"));
            stage.setScene(scene);
            stage.getIcons().add(new Image(App.class.getResourceAsStream("/stud/devon/images/icon.png")));
            stage.setTitle("Librarian");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        }else
        {
            stage=new Stage();
            FXMLLoader fxmlLoader=new FXMLLoader(App.class.getResource("/stud/devon/fxml/infoBoxView.fxml"));
            Scene scene= null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setScene(scene);
            stage.getIcons().add(new Image(App.class.getResourceAsStream("/stud/devon/images/icon.png")));
            stage.setTitle("Librarian");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            InfoBoxController controller=fxmlLoader.getController();
            controller.getMessage().setText("Bad network connection!");
            controller.getMessage().setStyle("-fx-text-fill: red;");
            controller.getOkButton().setVisible(true);
        }
    };

    public static Stage getStage() {
        return stage;
    }
    public static void setStage(Stage stage) {
        Gui.stage = stage;
    }

    public static Scene getScene() {

        return scene;
    }
    public static void setScene(Scene scene) {

        Gui.scene = scene;
    }
    public static FXMLLoader getFxmlLoader() {
        return fxmlLoader;
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        fxmlLoader = new FXMLLoader(App.class.getResource("/stud/devon/fxml/"+fxml + ".fxml"));
        return fxmlLoader.load();
    }
}
