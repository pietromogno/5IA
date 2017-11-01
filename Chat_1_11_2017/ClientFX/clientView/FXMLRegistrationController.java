package chatfx;

import client.Client;
import client.Objects.Message;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXMLRegistrationController implements Initializable, Observer {

    private Label label;
    @FXML
    private AnchorPane FRMreg;
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblUsername;
    @FXML
    private Label lblPassword;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private Button btnRegister;
    @FXML
    private Button btnLogin;
    @FXML
    private Label lblErrors;

    protected Client client;
    protected Message msg;
    protected ActionEvent event;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        client = new Client("localhost", 9898);
    }

    @FXML
    private void handleBtnRegister(ActionEvent event) {
        client.sendRegistration(txtUsername.getText(), txtPassword.getText());
        this.event = event;
    }

    @FXML
    private void handleBtnLogin(ActionEvent event) {
        client.sendLogin(txtUsername.getText(), txtPassword.getText());
        this.event = event;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Client && arg instanceof Message) {
            this.msg = (Message) arg;
            if (msg.getType() == Message.SATISFAIED) {
                try {
                    client.setPassword(txtPassword.getText());
                    client.setUsername(txtUsername.getText());
                    goToDialogFrame();
                } catch (IOException ex) {
                    Logger.getLogger(FXMLRegistrationController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (o instanceof Client && arg instanceof Message) {
            Message msg = (Message) arg;
            lblErrors.setText((String) msg.getMessage()[0]);
        }
    }

    private void goToDialogFrame() throws IOException {
        Parent dialogFRM = FXMLLoader.load(getClass().getResource("FXMLDialog.fxml"));
        Scene dialog = new Scene(dialogFRM);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(dialog);
        window.show();
    }

}
