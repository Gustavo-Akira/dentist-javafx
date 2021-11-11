package br.gustavoakira.dentist.boundary;

import br.gustavoakira.dentist.boundary.form.ServiceFormBoundary;
import br.gustavoakira.dentist.boundary.listener.IListener;
import br.gustavoakira.dentist.boundary.utils.Alerts;
import br.gustavoakira.dentist.controller.ClientController;
import br.gustavoakira.dentist.controller.LoginController;
import br.gustavoakira.dentist.entity.Client;
import br.gustavoakira.dentist.entity.Services;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientBoundary implements Initializable, IListener {

    @FXML
    private TableView<Client> clientTableView;

    @FXML
    private TableColumn<Client, Long> id;

    @FXML
    private TableColumn<Client, String> name;

    @FXML
    private Button addButton;

    private ClientController controller = new ClientController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateData();
    }

    private void createTable(){
        configTable();
        clientTableView.setItems(controller.getClients(LoginController.getLogged()));
        addButton.setOnMouseClicked(x->{
            createModal(null,(Stage) ((Node)x.getSource()).getScene().getWindow());
        });
    }


    private void configTable() {
        id.setCellValueFactory(x->new SimpleLongProperty(x.getValue().getId()).asObject());
        name.setCellValueFactory(x->new SimpleStringProperty(x.getValue().getName()));
    }

    private void createModal(Client client, Stage parent){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("form/client_form.fxml"));
            Pane pane = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(pane));
            stage.setTitle("Client form");
            stage.setResizable(false);
            stage.initOwner(parent);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
        }catch (IOException e){
            Alerts.showAlert("IO Exception","Error loading the modal",e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @Override
    public void updateData() {
        createTable();
    }
}
