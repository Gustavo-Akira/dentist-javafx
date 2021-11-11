package br.gustavoakira.dentist.boundary.form;

import br.gustavoakira.dentist.boundary.listener.IListener;
import br.gustavoakira.dentist.controller.ClientController;
import br.gustavoakira.dentist.entity.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClientFormBoundary implements Initializable {

    @FXML
    private TextField id;

    @FXML
    private TextField name;

    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;

    @FXML
    private ClientController controller = new ClientController();

    private List<IListener> listeners = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addButton.setOnMouseClicked(x->{
            Client client = new Client();
            client.setName(name.getText());
            controller.insert(client);
            sendNotification();
            ((Stage) ((Node)x.getSource()).getScene().getWindow()).close();
        });

        cancelButton.setOnMouseClicked(x->{
            ((Stage) ((Node)x.getSource()).getScene().getWindow()).close();
        });
    }

    public void addListener(IListener listener){
        listeners.add(listener);
    }

    public void sendNotification(){
        for(IListener listener :listeners){
            listener.updateData();
        }
    }
}