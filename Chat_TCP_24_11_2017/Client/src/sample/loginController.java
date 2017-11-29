package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class loginController {

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnRegister;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private Labeled lblError;

    private Client client;

    public loginController(Client client) {
        this.client = client;
    }

    @FXML
    private void initialize() {

        btnRegister.setOnAction(event -> {
            registrationController rc = new registrationController(this.client);
            Stage regWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
            regWindow.setTitle("Registration");
            try {
                FXMLLoader regLoader = new FXMLLoader(loginController.class.getResource("Registration.fxml"));
                regLoader.setController(rc);
                AnchorPane regPage = (AnchorPane) regLoader.load();
                Scene regScene = new Scene(regPage);
                regWindow.setScene(regScene);
                regWindow.show();
            } catch (IOException iOe) {
                iOe.printStackTrace();
            }
        });

        btnLogin.setOnAction((ActionEvent event) -> {
            client.setUsername(txtUsername.getText().trim());
            client.setPassword(txtPassword.getText().trim());
            if (client.logIn()) {
                messagingController mc = new messagingController(this.client);
                Stage mesWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
                mesWindow.setTitle(client.getUsername());

                try {
                    FXMLLoader mesLoader = new FXMLLoader(loginController.class.getResource("Messaging.fxml"));
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
