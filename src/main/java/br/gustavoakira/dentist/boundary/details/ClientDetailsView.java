package br.gustavoakira.dentist.boundary.details;

import br.gustavoakira.dentist.entity.Address;
import br.gustavoakira.dentist.entity.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientDetailsView implements Initializable {

    private Client client;

    @FXML
    private AddressBoundary addressController;

    @FXML
    private AnchorPane phone;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setClient(Client client) {
        this.client = client;
        addressController.setClient(client.getId());
        addressController.updateData();
    }
}
