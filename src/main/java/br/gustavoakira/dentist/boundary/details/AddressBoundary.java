package br.gustavoakira.dentist.boundary.details;

import br.gustavoakira.dentist.boundary.form.AddressFormBoundary;
import br.gustavoakira.dentist.boundary.listener.IListener;
import br.gustavoakira.dentist.boundary.utils.Alerts;
import br.gustavoakira.dentist.controller.AddressControl;
import br.gustavoakira.dentist.entity.Address;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
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

public class AddressBoundary implements Initializable, IListener {

    private Long clientId;

    private final AddressControl controller = new AddressControl();


    @FXML
    private TableView<Address> addressTableView;

    @FXML
    private TableColumn<Address, Long> id;

    @FXML
    private TableColumn<Address, String> num;

    @FXML
    private TableColumn<Address, String> street;

    @FXML
    private TableColumn<Address, String> city;

    @FXML
    private TableColumn<Address, String> district;

    @FXML
    private TableColumn<Address, String> cep;

    @FXML
    private TableColumn<Address, String> complement;

    @FXML
    private TableColumn<Address, Address> edit;

    @FXML
    private TableColumn<Address, Address> delete;

    @FXML
    private Button addButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createTable();
        addButton.setOnMouseClicked(x->{
            createModal(null,(Stage) ((Node)x.getSource()).getScene().getWindow());
        });
    }

    private void createTable() {
        id.setCellValueFactory(x->new SimpleLongProperty(x.getValue().getId()).asObject());
        num.setCellValueFactory(x->new SimpleStringProperty(x.getValue().getNum()));
        street.setCellValueFactory(x-> new SimpleStringProperty(x.getValue().getStreet()));
        city.setCellValueFactory(x-> new SimpleStringProperty(x.getValue().getCity()));
        district.setCellValueFactory(x-> new SimpleStringProperty(x.getValue().getDistrict()));
        cep.setCellValueFactory(x-> new SimpleStringProperty(x.getValue().getCep()));
        complement.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().getComplement()));
        startDeleteButton();
        startEditButton();
    }

    private void startEditButton(){
        edit.setCellValueFactory(x -> new ReadOnlyObjectWrapper<>(x.getValue()));
        edit.setCellFactory(x->new TableCell<Address, Address>(){
            private final Button button = new Button("edit");

            @Override
            protected void updateItem(Address item, boolean empty) {
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

    private void startDeleteButton() {
        delete.setCellValueFactory(x -> new ReadOnlyObjectWrapper<>(x.getValue()));
        delete.setCellFactory(x -> new TableCell<Address, Address>() {
            private final Button button = new Button("delete");

            @Override
            protected void updateItem(Address item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);

                button.setOnMouseClicked(x -> {
                    controller.delete(item);
                    updateData();
                });
            }
        });
    }

    @Override
    public void updateData(){
        addressTableView.setItems(controller.getAddresses(clientId));
    }

    private void createModal(Address address, Stage parent){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("form/address_form.fxml"));
            Pane pane = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(pane));
            stage.setTitle("Address Form");
            stage.setResizable(false);
            AddressFormBoundary formBoundary = loader.getController();
            if(address != null) {
                formBoundary.setAddress(address);
                formBoundary.updateForm();
            }else{
                formBoundary.setClient(clientId);
            }
            formBoundary.setListener(this);
            stage.initOwner(parent);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
        }catch (IOException e){
            Alerts.showAlert("IO Exception","Error loading the modal",e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void setClient(Long id) {
        this.clientId = id;
    }
}
