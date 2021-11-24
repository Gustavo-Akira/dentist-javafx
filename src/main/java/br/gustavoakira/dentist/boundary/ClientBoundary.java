package br.gustavoakira.dentist.boundary;

import br.gustavoakira.dentist.boundary.details.ClientDetailsView;
import br.gustavoakira.dentist.boundary.form.ClientFormBoundary;
import br.gustavoakira.dentist.boundary.listener.IListener;
import br.gustavoakira.dentist.boundary.utils.Alerts;
import br.gustavoakira.dentist.control.ClientControl;
import br.gustavoakira.dentist.control.security.LoginControl;
import br.gustavoakira.dentist.entity.Client;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private TableColumn<Client, Client> viewMore;

    @FXML
    private TableColumn<Client, Client> edit;

    @FXML
    private TableColumn<Client, Client> delete;

    @FXML
    private Button addButton;

    @FXML
    private TextField filter;

    private ClientControl controller = new ClientControl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateData();
        filter.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                clientTableView.setItems(controller.getClients(LoginControl.getLogged(),filter.getText()));
            }
        });
    }

    private void createTable(){
        configTable();
        clientTableView.setItems(controller.getClients(LoginControl.getLogged(),""));
        addButton.setOnMouseClicked(x->{
            createModal(null,(Stage) ((Node)x.getSource()).getScene().getWindow());
        });
    }


    private void configTable() {
        id.setCellValueFactory(x->new SimpleLongProperty(x.getValue().getId()).asObject());
        name.setCellValueFactory(x->new SimpleStringProperty(x.getValue().getName()));
        startDeleteButton();
        startEditButton();
        startViewButton();
    }

    private void startEditButton(){
        edit.setCellValueFactory(x -> new ReadOnlyObjectWrapper<>(x.getValue()));
        edit.setCellFactory(x->new TableCell<Client, Client>(){
            private final Button button = new Button("edit");

            @Override
            protected void updateItem(Client item, boolean empty) {
                super.updateItem(item, empty);

                if(item == null){
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnMouseClicked(x->{
                    createModal(item,(Stage) ((Node)x.getSource()).getScene().getWindow());
                });
            }


        });
    }

    private void startDeleteButton(){
        delete.setCellValueFactory(x -> new ReadOnlyObjectWrapper<>(x.getValue()));
        delete.setCellFactory(x->new TableCell<Client, Client>(){
            private final Button button = new Button("delete");

            @Override
            protected void updateItem(Client item, boolean empty) {
                super.updateItem(item, empty);

                if(item == null){
                    setGraphic(null);
                    return;
                }
                setGraphic(button);

                button.setOnMouseClicked(x->{
                    controller.delete(item.getId());
                    updateData();
                });
            }
        });
    }

    private void startViewButton(){
        viewMore.setCellValueFactory(x->new ReadOnlyObjectWrapper<>(x.getValue()));
        viewMore.setCellFactory(x-> new TableCell<Client, Client>(){
            private final Button button = new Button("details");

            @Override
            protected void updateItem(Client item, boolean empty) {
                super.updateItem(item, empty);

                if(item == null){
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnMouseClicked(x->{
                    createViewWindow(item,(Stage) ((Node)x.getSource()).getScene().getWindow());
                });
            }
        });
    }

    private void createModal(Client client, Stage parent){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("form/client_form.fxml"));
            Pane pane = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(pane));
            stage.setTitle("Client form");
            stage.setResizable(false);
            ClientFormBoundary formBoundary = loader.getController();
            formBoundary.addListener(this);
            formBoundary.setClient(client);
            formBoundary.updateForm();
            stage.initOwner(parent);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
        }catch (IOException e){
            Alerts.showAlert("IO Exception","Error loading the modal",e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }


    private void createViewWindow(Client client, Stage parent){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("details/client_details.fxml"));
            Pane pane = loader.load();
            ClientDetailsView view = loader.getController();
            view.setClient(client);
            Stage stage = new Stage();
            stage.setScene(new Scene(pane));
            stage.initOwner(parent);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
        }catch (IOException e){
            Alerts.showAlert("IO Exception", "Error loading the view", e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @Override
    public void updateData() {
        createTable();
    }
}
