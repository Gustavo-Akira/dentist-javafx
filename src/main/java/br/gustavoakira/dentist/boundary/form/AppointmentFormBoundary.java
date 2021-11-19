package br.gustavoakira.dentist.boundary.form;

import br.gustavoakira.dentist.boundary.listener.IListener;
import br.gustavoakira.dentist.boundary.utils.DateTimePicker;
import br.gustavoakira.dentist.controller.AppointmentController;
import br.gustavoakira.dentist.controller.ClientController;
import br.gustavoakira.dentist.controller.security.LoginController;
import br.gustavoakira.dentist.entity.Appointment;
import br.gustavoakira.dentist.entity.Client;
import br.gustavoakira.dentist.entity.UserType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import lombok.Setter;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
@Setter
public class AppointmentFormBoundary implements Initializable {

    private Appointment appointment = new Appointment();

    private IListener listener;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createComboBox();
        addButton.setOnMouseClicked(x->{
            Appointment appointment = new Appointment();
            if(!id.getText().isEmpty()){
                appointment.setId(Long.parseLong(id.getText()));
            }
            appointment.setStartDate(startDate.dateTimeValueProperty().getValue());
            appointment.setEndDate(endDate.dateTimeValueProperty().getValue());
            appointment.setClient(clientComboBox.getValue());
            appointment.setUser(LoginController.getLogged());
            if(appointment.getId() == null){
                appointmentController.insert(appointment);
            }else{
                appointmentController.update(appointment);
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
    private DateTimePicker startDate;

    @FXML
    private DateTimePicker endDate;

    @FXML
    private ComboBox<Client> clientComboBox;

    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;

    private final ClientController controller = new ClientController();

    private final AppointmentController appointmentController = new AppointmentController();

    private void createComboBox(){
        settingComboBox();
        clientComboBox.setItems(controller.getClients(LoginController.getLogged()));
    }

    public void updateForm(){
        id.setText(this.appointment.getId().toString());
        startDate.setDateTimeValue(appointment.getStartDate());
        endDate.setDateTimeValue(appointment.getEndDate());
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

}
