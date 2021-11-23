package br.gustavoakira.dentist.boundary;

import br.gustavoakira.dentist.boundary.form.ServiceFormBoundary;
import br.gustavoakira.dentist.boundary.listener.IListener;
import br.gustavoakira.dentist.boundary.utils.Alerts;
import br.gustavoakira.dentist.controller.security.LoginControl;
import br.gustavoakira.dentist.controller.ServiceControl;
import br.gustavoakira.dentist.entity.Services;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleDoubleProperty;
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

public class ServiceBoundary implements Initializable, IListener {
    @FXML
    private Button addButton;

    @FXML
    private TableView<Services> servicesTableView;

    @FXML
    private TableColumn<Services, Long> id;

    @FXML
    private TableColumn<Services, String> name;

    @FXML
    private TableColumn<Services, Double> price;

    @FXML
    private TableColumn<Services, Services> edit;

    @FXML
    private TableColumn<Services, Services> delete;

    private ServiceControl controller = new ServiceControl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addButton.setOnMouseClicked(x->{
            createModal(null, (Stage) ((Node)x.getSource()).getScene().getWindow());
        });
        updateTable();
    }

    @Override
    public void updateData() {
        updateTable();
    }

    private void updateTable(){
        createTable();
        startDeleteButton();
        startEditButton();
        servicesTableView.setItems(controller.getServices(LoginControl.getLogged()));
    }

    private void createTable(){
        name.setCellValueFactory(x->new SimpleStringProperty(x.getValue().getName()));
        id.setCellValueFactory(x-> new SimpleLongProperty(x.getValue().getId()).asObject());
        price.setCellValueFactory(x-> new SimpleDoubleProperty(x.getValue().getPrice()).asObject());
    }

    private void startEditButton(){
        edit.setCellValueFactory(x -> new ReadOnlyObjectWrapper<>(x.getValue()));
        edit.setCellFactory(x->new TableCell<Services, Services>(){
            private final Button button = new Button("edit");

            @Override
            protected void updateItem(Services item, boolean empty) {
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
        delete.setCellFactory(x->new TableCell<Services, Services>(){
            private final Button button = new Button("delete");

            @Override
            protected void updateItem(Services item, boolean empty) {
                super.updateItem(item, empty);

                if(item == null){
                    setGraphic(null);
                    return;
                }
                setGraphic(button);

                button.setOnMouseClicked(x->{
                    controller.deleteService(item);
                    updateTable();
                });
            }
        });
    }

    private void createModal(Services services, Stage parent){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("form/service_form.fxml"));
            Pane pane = loader.load();
            ServiceFormBoundary boundary = loader.getController();
            boundary.setServices(services);
            boundary.addListener(this);
            boundary.updateForm();
            Stage stage = new Stage();
            stage.setScene(new Scene(pane));
            stage.setTitle("Service form");
            stage.setResizable(false);
            stage.initOwner(parent);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
        }catch (IOException e){
            Alerts.showAlert("IO Exception","Error loading the modal",e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
}
