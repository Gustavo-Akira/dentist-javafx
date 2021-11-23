package br.gustavoakira.dentist.boundary.form;

import br.gustavoakira.dentist.boundary.listener.IListener;
import br.gustavoakira.dentist.boundary.utils.Alerts;
import br.gustavoakira.dentist.boundary.utils.TimeField;
import br.gustavoakira.dentist.controller.AppointmentControl;
import br.gustavoakira.dentist.controller.ClientControl;
import br.gustavoakira.dentist.controller.security.LoginControl;
import br.gustavoakira.dentist.entity.Appointment;
import br.gustavoakira.dentist.entity.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import lombok.Setter;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;
@Setter
public class AppointmentFormBoundary implements Initializable {

    private Appointment appointment = new Appointment();

    private IListener listener;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createComboBox();
        addButton.setOnMouseClicked(x->{
            if(!validateDateTime()){
                Alerts.showAlert("Error of consistency","Data ou hora","Por favor coloque uma data ou hora de termino apos o horario de inicio", Alert.AlertType.ERROR);
                return;
            }
            Appointment appointment = new Appointment();
            if(!id.getText().isEmpty()){
                appointment.setId(Long.parseLong(id.getText()));
            }
            appointment.setStartDate(LocalDateTime.of(startDate.getValue(), LocalTime.parse(startTime.getText())));
            appointment.setEndDate(LocalDateTime.of(endDate.getValue(), LocalTime.parse(endTime.getText())));
            appointment.setClient(clientComboBox.getValue());
            appointment.setUser(LoginControl.getLogged());
            if(appointment.getId() == null){
                appointmentControl.insert(appointment);
            }else{
                appointmentControl.update(appointment);
            }
            sendNotification();
            ((Stage) ((Node)x.getSource()).getScene().getWindow()).close();
        });
        cancelButton.setOnMouseClicked(x->{
            sendNotification();
            ((Stage) ((Node)x.getSource()).getScene().getWindow()).close();
        });
    }

    @FXML
    private TextField id;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private TimeField startTime;

    @FXML
    private TimeField endTime;

    @FXML
    private ComboBox<Client> clientComboBox;

    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;

    private final ClientControl controller = new ClientControl();

    private final AppointmentControl appointmentControl = new AppointmentControl();

    private void createComboBox(){
        settingComboBox();
        clientComboBox.setItems(controller.getClients(LoginControl.getLogged(),""));
    }

    public void updateForm(){
        id.setText(this.appointment.getId().toString());
        startDate.setValue(appointment.getStartDate().toLocalDate());
        startTime.setText(appointment.getStartDate().toLocalTime().toString());
        endDate.setValue(appointment.getEndDate().toLocalDate());
        endTime.setText(appointment.getEndDate().toLocalTime().toString());
        clientComboBox.setValue(appointment.getClient());
    }

    public void sendNotification(){
        this.listener.updateData();
    }

    private void settingComboBox() {
        Callback<ListView<Client>, ListCell<Client>> factory = lv -> new ListCell<Client>() {
            @Override
            protected void updateItem(Client item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };
        clientComboBox.setCellFactory(factory);
        clientComboBox.setButtonCell(factory.call(null));
    }

    private boolean validateDateTime(){
        if(endDate.getValue().isAfter(startDate.getValue()) || endDate.getValue().isEqual(startDate.getValue())){
            if(endDate.getValue().isEqual(startDate.getValue())){
                if(LocalTime.parse(startTime.getText()).isBefore(LocalTime.parse(endTime.getText()))){
                    return true;
                }else {
                    return false;
                }
            }else{
                return true;
            }
        }
        return false;
    }
}
