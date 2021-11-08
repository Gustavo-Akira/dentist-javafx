package br.gustavoakira.dentist.boundary;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginBoundary implements Initializable {

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button loginButton;


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
        try{
            Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml"));
            Scene scene = new Scene(parent,500,500);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
        Stage stage = (Stage) this.emailField.getScene().getWindow();
        stage.close();
    }
}
