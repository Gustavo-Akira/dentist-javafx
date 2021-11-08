package br.gustavoakira.dentist.boundary;

import br.gustavoakira.dentist.db.DB;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.ChoiceBoxListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
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

    private ObservableList<String> list = FXCollections.observableArrayList(
            "Usuarios","Serviços");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createListView();
        changeEntity("usuarios");
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
                if(newValue == "Serviços"){
                    newValue = "servicos";
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
