package chatfx;

import client.Client;
import client.Objects.Message;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class FXMLDialogController implements Initializable, Observer {

    @FXML
    private AnchorPane FRMdialog;
    @FXML
    private Label lblActualUser;
    @FXML
    private ListView<?> listMessages;
    @FXML
    private TextField txtDestination;
    @FXML
    private Label lblSendingDest;
    @FXML
    private TextField txtMessage;
    @FXML
    private Button btnSend;
    @FXML
    private Label lblErrors;

    protected Client client = null;
    @FXML
    private Button btnLogout;
    @FXML
    private Button btnWhoIsIn;
    @FXML
    private Button btnBroadCast;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void handleSendMessage(ActionEvent event) {
        client.sendMessage(txtDestination.getText(), txtMessage.getText());
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        client.sendLogout();
    }

    @FXML
    private void handleWhoIsIn(ActionEvent event) {
        client.sendWhoIsIn();
    }

    @FXML
    private void handleBroadcast(ActionEvent event) {
        client.sendBroadMessage(txtMessage.getText());
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Client && arg instanceof Client) {
            this.client = (Client) arg;
            lblActualUser.setText(client.getUsername());
        } else if (o instanceof Client && arg instanceof Message) {
            Message msg = (Message) arg;
            listMessages.setAccessibleText((String) msg.getMessage()[0]);
        }
    }

}
