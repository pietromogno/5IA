package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.HashMap;

public class messagingController {

    @FXML
    private Button btnSend;

    @FXML
    private Button btnLogOut;

    @FXML
    private Button btnWhoIsIn;

    @FXML
    private TextField txtSendFild;

    @FXML
    private TextArea messageArea;

    @FXML
    private Label lblDestination;

    @FXML
    private ListView<Contact> clientList;
    private ObservableList<Contact> listViewData = FXCollections.observableArrayList();

    private Client client;
    private int destinationId;
    private String message;

    public messagingController(Client client) {
        this.destinationId = -1;
        this.client = client;
        this.client.setMessagingController(this);
        this.client.startReceiver();
    }

    @FXML
    public void setListViewData(HashMap<Integer, String> HashViewData) {
        this.listViewData.clear();
        if (HashViewData.isEmpty()) destinationId = -1;
        HashViewData.forEach((key, value) -> {
            this.listViewData.add(new Contact(key, value));
        });
        clientList.setItems(this.listViewData);
    }

    @FXML
    public void setMessage(Message message) {
        if (message.getDestinationId() == client.getClientId())
            messageArea.appendText((String) message.getMessage() + "\n\n");
    }

    @FXML
    private void initialize() {
        btnWhoIsIn.setOnAction(event -> {
            client.updateClientList();
        });

        clientList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            lblDestination.setText(newValue.getUsername());
            destinationId = newValue.getClientId();
        });

        btnSend.setOnAction(event -> {
            if (!txtSendFild.getText().isEmpty() && destinationId != -1) {
                this.client.sendMessage(destinationId, client.getUsername() + ": " + txtSendFild.getText());
                messageArea.appendText(client.getUsername() + ": " + txtSendFild.getText() + "\n\n");
                txtSendFild.clear();
            }
        });

        btnLogOut.setOnAction(event -> {
            try {
                client.sendLogout();
                client.stopReciving();
                Platform.exit();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

}
