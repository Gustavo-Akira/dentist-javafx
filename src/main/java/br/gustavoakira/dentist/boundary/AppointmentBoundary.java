package br.gustavoakira.dentist.boundary;

import br.gustavoakira.dentist.boundary.form.AppointmentFormBoundary;
import br.gustavoakira.dentist.boundary.listener.IListener;
import br.gustavoakira.dentist.boundary.utils.Alerts;
import br.gustavoakira.dentist.control.AppointmentControl;
import br.gustavoakira.dentist.control.security.LoginControl;
import br.gustavoakira.dentist.entity.Appointment;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
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
    private TableColumn<Appointment, Appointment> edit;

    @FXML
    private TableColumn<Appointment, Appointment> delete;

    @FXML
    private Button addButton;

    private final AppointmentControl controller = new AppointmentControl();


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
        start.setCellValueFactory(x->new SimpleObjectProperty(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss",
                Locale.GERMANY).format(x.getValue().getStartDate())));
        end.setCellValueFactory(x->new SimpleObjectProperty(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss",
                Locale.GERMANY).format(x.getValue().getEndDate())));
        client.setCellValueFactory(x->new SimpleStringProperty(x.getValue().getClient().getName()));
        startEditButton();
        startDeleteButton();
    }

    private void startEditButton(){
        edit.setCellValueFactory(x -> new ReadOnlyObjectWrapper<>(x.getValue()));
        edit.setCellFactory(x->new TableCell<Appointment, Appointment>(){
            private final Button button = new Button("editar");

            @Override
            protected void updateItem(Appointment item, boolean empty) {
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
        delete.setCellFactory(x->new TableCell<Appointment,Appointment>(){
            private final Button button = new Button("deletar");

            @Override
            protected void updateItem(Appointment item, boolean empty) {
                super.updateItem(item, empty);

                if(item == null){
                    setGraphic(null);
                    return;
                }
                setGraphic(button);

                button.setOnMouseClicked(x->{
                    controller.delete(item);
                    updateData();
                });
            }
        });
    }
    @Override
    public void updateData() {
        appointmentTableView.setItems(controller.getAppointments(LoginControl.getLogged().getId()));
    }
    private void createModal(Appointment appointment, Stage parent){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("form/appointments_form.fxml"));
            Pane pane = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(pane));
            stage.setTitle("Appointment form");
            AppointmentFormBoundary appointmentFormBoundary = loader.getController();
            appointmentFormBoundary.setAppointment(appointment);
            if(appointment != null){
                appointmentFormBoundary.updateForm();
            }
            appointmentFormBoundary.setListener(this);
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
