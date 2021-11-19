package br.gustavoakira.dentist.boundary;

import br.gustavoakira.dentist.controller.security.LoginController;
import br.gustavoakira.dentist.entity.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.ChoiceBoxListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainBoundary implements Initializable {


    @FXML
    private ListView<String> listView;

    @FXML
    private BorderPane borderPane;

    private ObservableList<String> list = LoginController.getAuthorizations();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createListView();
        if(LoginController.getAuthorizations().contains("Usuarios")) {
            changeEntity("usuarios");
        }else{
            changeEntity("appointments");
        }
    }

    private void createListView(){
        listView.setItems(list);
        listView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ChoiceBoxListCell<>();
            }
        });

        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.equals("Serviços")){
                    newValue = "servicos";
                }
                if(newValue.equals("Clientes")){
                    newValue = "clients";
                }
                if(newValue.equals("Appontamentos")){
                    newValue = "appointments";
                }
                if(newValue.equals("Perfil")){
                    newValue="profile";
                }
                if(newValue.equals("Sair")){
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("login.fxml"));
                        Parent parent = loader.load();
                        Scene scene = new Scene(parent);
                        Stage stage = new Stage();
                        stage.setResizable(false);
                        stage.setScene(scene);
                        stage.show();
                        Stage thisStage = (Stage) borderPane.getScene().getWindow();
                        thisStage.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                changeEntity(newValue);
            }
        });
    }

    private void changeEntity(String name){
        try {
            name = name.toLowerCase(Locale.ROOT);
            VBox box = FXMLLoader.load(getClass().getClassLoader().getResource(name+".fxml"));
            borderPane.setCenter(box);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
