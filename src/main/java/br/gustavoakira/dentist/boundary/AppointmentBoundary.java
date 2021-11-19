package br.gustavoakira.dentist.boundary;

import br.gustavoakira.dentist.boundary.form.ClientFormBoundary;
import br.gustavoakira.dentist.boundary.listener.IListener;
import br.gustavoakira.dentist.boundary.utils.Alerts;
import br.gustavoakira.dentist.controller.AppointmentController;
import br.gustavoakira.dentist.controller.security.LoginController;
import br.gustavoakira.dentist.entity.Appointment;
import br.gustavoakira.dentist.entity.Client;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
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
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AppointmentBoundary implements Initializable, IListener {
    @FXML
    private TableView<Appointment> appointmentTableView;

    @FXML
    private TableColumn<Appointment, Long> id;

    @FXML
    private TableColumn<Appointment, LocalDateTime> start;

    @FXML
    private TableColumn<Appointment, LocalDateTime> end;

    @FXML
    private TableColumn<Appointment, String> client;

    @FXML
    private Button addButton;

    private final AppointmentController controller = new AppointmentController();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createTable();
        addButton.setOnMouseClicked(x->{
            createModal(null, (Stage) ((Node)x.getSource()).getScene().getWindow());
        });
        updateData();
    }

    private void createTable(){
        id.setCellValueFactory(x->new SimpleLongProperty(x.getValue().getId()).asObject());
        start.setCellValueFactory(x->new SimpleObjectProperty<>(x.getValue().getStartDate()));
        end.setCellValueFactory(x->new SimpleObjectProperty<>(x.getValue().getEndDate()));
        client.setCellValueFactory(x->new SimpleStringProperty(x.getValue().getClient().getName()));
    }


    @Override
    public void updateData() {
        appointmentTableView.setItems(controller.getAppointments(LoginController.getLogged().getId()));
    }
    private void createModal(Appointment appointment, Stage parent){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("form/appointments_form.fxml"));
            Pane pane = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(pane));
            stage.setTitle("Appointment form");
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
