package br.gustavoakira.dentist.boundary.form;

import br.gustavoakira.dentist.boundary.listener.IListener;
import br.gustavoakira.dentist.control.UserController;
import br.gustavoakira.dentist.control.UserTypeControl;
import br.gustavoakira.dentist.entity.User;
import br.gustavoakira.dentist.entity.UserType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserFormBoundary implements Initializable {

    private User user;

    @FXML
    private TextField id;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private TextField name;

    @FXML
    private ComboBox<UserType> typeComboBox;

    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;

    private List<IListener> listeners = new ArrayList<>();

    private UserTypeControl controller = new UserTypeControl();

    private UserController userController = new UserController();

    public void setUser(User user) {
        this.user = user;
    }

    public void addListener(IListener listener){
        this.listeners.add(listener);
    }

    private void sendNotification(){
        listeners.stream().forEach(x->x.updateData());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startComboBox();
        addButton.setOnMouseClicked(x->{
            User user = new User();
            if(!id.getText().isEmpty()){
                user.setId(Long.parseLong(id.getText()));
            }
            if(!email.getText().isEmpty()){
                user.setEmail(email.getText());
            }
            if(!name.getText().isEmpty()){
                user.setName(name.getText());
            }
            if(typeComboBox.getValue() != null){
                user.setType(typeComboBox.getValue());
            }
            if(!password.getText().isEmpty()){
                user.setPassword(password.getText());
            }
            if(user.getId() == null) {
                userController.insertUser(user);
            }else{
                userController.updateUser(user);
            }
            sendNotification();
            ((Stage) ((Node)x.getSource()).getScene().getWindow()).close();
        });
        cancelButton.setOnMouseClicked(x->{
            ((Stage) ((Node)x.getSource()).getScene().getWindow()).close();
        });
    }

    private void startComboBox() {
        settingComboBox();
        typeComboBox.setItems(controller.getUserTypes());
    }

    public void updateFormData(){
        if(user != null){
            id.setText(user.getId().toString());
            email.setText(user.getEmail());
            name.setText(user.getName());
            typeComboBox.setValue(user.getType());
        }
    }

    private void settingComboBox() {
        Callback<ListView<UserType>, ListCell<UserType>> factory = lv -> new ListCell<UserType>() {
            @Override
            protected void updateItem(UserType item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };
        typeComboBox.setCellFactory(factory);
        typeComboBox.setButtonCell(factory.call(null));
    }
}
