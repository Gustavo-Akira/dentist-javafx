package br.gustavoakira.dentist.boundary;

import br.gustavoakira.dentist.boundary.utils.Alerts;
import br.gustavoakira.dentist.controller.UserController;
import br.gustavoakira.dentist.controller.security.LoginController;
import br.gustavoakira.dentist.entity.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;


import java.net.URL;
import java.util.ResourceBundle;

public class ProfileBoundary implements Initializable {

    UserController controller = new UserController();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createView();
    }
    private void createView(){
        User user = LoginController.getLogged();
        userLabel.setText(user.getEmail());
        id.setText(user.getId().toString());
        this.user.setText(user.getEmail());
        this.name.setText(user.getName());
        this.addButton.setOnMouseClicked(x->{
            User updatedUser = new User();
            updatedUser.setId(Long.parseLong(id.getText()));
            updatedUser.setEmail(this.user.getText());
            updatedUser.setName(name.getText());
            controller.updateUser(updatedUser);
            Alerts.showAlert("Alerta","Por favor feche o aplicativo para confirmar as alterações","",Alert.AlertType.CONFIRMATION);
        });
    }
    @FXML
    private TextField id;

    @FXML
    private TextField user;

    @FXML
    private TextField name;

    @FXML
    private PasswordField password;

    @FXML
    private Button addButton;

    @FXML
    private Label userLabel;

}
