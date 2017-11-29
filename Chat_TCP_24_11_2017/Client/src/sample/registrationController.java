package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class registrationController {

    @FXML
    private Button btnRegister;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNickName;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private Label lblError;

    private Client client;

    public registrationController(Client client) {
        this.client = client;
    }

    @FXML
    public void initialize() {
        btnRegister.setOnAction(event -> {
            client.setName(txtName.getText().trim());
            client.setNickname(txtNickName.getText().trim());
            client.setUsername(txtUsername.getText().trim());
            client.setPassword(txtPassword.getText().trim());

            if (client.registerUser()) {
                messagingController mc = new messagingController(this.client);
                Stage mesWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
                mesWindow.setTitle(client.getUsername());
                try {
                    FXMLLoader mesLoader = new FXMLLoader(registrationController.class.getResource("Messaging.fxml"));
                    mesLoader.setController(mc);
                    AnchorPane mesPage = (AnchorPane) mesLoader.load();
                    Scene mesScene = new Scene(mesPage);
                    mesWindow.setScene(mesScene);
                    mesWindow.show();
                    mesWindow.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        public void handle(WindowEvent we) {
                            try {
                                client.stopReciving();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (IOException iOe) {
                    iOe.printStackTrace();
                }
            } else {
                lblError.setText(client.getError());
            }
        });
    }
}
