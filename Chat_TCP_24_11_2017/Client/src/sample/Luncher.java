package sample;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Luncher extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login");
        loginController lc = new loginController(new Client("localhost", 9898));
        try {
            FXMLLoader loader = new FXMLLoader(Luncher.class.getResource("Login.fxml"));
            loader.setController(lc);
            AnchorPane page = (AnchorPane) loader.load();
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException iOe) {
            iOe.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
