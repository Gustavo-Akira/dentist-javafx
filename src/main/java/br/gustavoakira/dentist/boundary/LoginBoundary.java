package br.gustavoakira.dentist.boundary;

import br.gustavoakira.dentist.boundary.utils.Alerts;
import br.gustavoakira.dentist.controller.security.LoginController;
import br.gustavoakira.dentist.entity.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginBoundary implements Initializable {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    private LoginController controller = new LoginController();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginButton.setDisable(true);
        emailField.setOnKeyTyped(x->onChangeText());
        passwordField.setOnKeyTyped(x->onChangeText());
        loginButton.setOnMouseClicked(x->onClickLogin());
    }

    private void onChangeText(){
        if(!emailField.getText().isEmpty() && !passwordField.getText().isEmpty()){
            loginButton.setDisable(false);
        }
    }

    private void onClickLogin(){
        controller.login(emailField.getText(), passwordField.getText());
        User logged = controller.getLogged();
        if(logged != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("main.fxml"));
                Parent parent = loader.load();
                Scene scene = new Scene(parent, 500, 500);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) this.emailField.getScene().getWindow();
            stage.close();
        }else{
            Alerts.showAlert("Error","Login invalid","Please try again", Alert.AlertType.ERROR);
        }
    }
}
