package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../sample/samplelog.fxml"));
        Scene sc = new Scene(root,1224,685);
        sc.getStylesheets().add("css/black.css");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.getIcons().add(new Image("/img/M.png"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(sc);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
