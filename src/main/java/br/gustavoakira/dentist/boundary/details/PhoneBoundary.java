package br.gustavoakira.dentist.boundary.details;

import br.gustavoakira.dentist.boundary.form.PhoneFormBoundary;
import br.gustavoakira.dentist.boundary.listener.IListener;
import br.gustavoakira.dentist.boundary.utils.Alerts;
import br.gustavoakira.dentist.control.PhoneControl;
import br.gustavoakira.dentist.entity.Client;
import br.gustavoakira.dentist.entity.Phone;
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

public class PhoneBoundary implements Initializable, IListener {

    private Client client;

    @FXML
    private TableView<Phone> phoneTableView;

    @FXML
    private TableColumn<Phone,Long> id;

    @FXML
    private TableColumn<Phone, String> num;

    @FXML
    private TableColumn<Phone, String> ddd;

    @FXML
    private TableColumn<Phone, String> country;

    @FXML
    private TableColumn<Phone, Phone> edit;

    @FXML
    private TableColumn<Phone, Phone> delete;

    @FXML
    private Button addButton;

    private PhoneControl controller = new PhoneControl();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createTable();
        addButton.setOnMouseClicked(x->{
            createModal(null,(Stage) ((Node)x.getSource()).getScene().getWindow());
        });
    }

    private void createTable() {
        id.setCellValueFactory(x->new SimpleLongProperty(x.getValue().getId()).asObject());
        num.setCellValueFactory(x-> new SimpleStringProperty(x.getValue().getNum()));
        ddd.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().getDdd()));
        country.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().getCountry()));
        startEditButton();
        startDeleteButton();
    }

    private void startEditButton(){
        edit.setCellValueFactory(x -> new ReadOnlyObjectWrapper<>(x.getValue()));
        edit.setCellFactory(x->new TableCell<Phone, Phone>(){
            private final Button button = new Button("edit");

            @Override
            protected void updateItem(Phone item, boolean empty) {
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
        delete.setCellFactory(x -> new TableCell<Phone, Phone>() {
            private final Button button = new Button("delete");

            @Override
            protected void updateItem(Phone item, boolean empty) {
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

    private void createModal( Phone phone, Stage parent){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("form/phone_form.fxml"));
            Pane pane = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(pane));
            stage.setTitle("Phone Form");
            stage.setResizable(false);
            PhoneFormBoundary formBoundary = loader.getController();
            if(phone != null) {
                formBoundary.setPhone(phone);
                formBoundary.updateForm();
            }else{
                formBoundary.setClient(client);
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

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public void updateData() {
        phoneTableView.setItems(controller.getPhones(client.getId()));
    }
}
