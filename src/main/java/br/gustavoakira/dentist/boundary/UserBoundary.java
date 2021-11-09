package br.gustavoakira.dentist.boundary;

import br.gustavoakira.dentist.boundary.form.UserFormBoundary;
import br.gustavoakira.dentist.boundary.listener.IListener;
import br.gustavoakira.dentist.controller.UserController;
import br.gustavoakira.dentist.entity.User;
import br.gustavoakira.dentist.boundary.utils.Alerts;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleLongProperty;
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
import java.util.ResourceBundle;

public class UserBoundary implements Initializable, IListener {

    @FXML
    private Button addButton;

    @FXML
    private TableView<User> userTableView;

    @FXML
    private TableColumn<User, Long> idCol;

    @FXML
    private TableColumn<User, String> nameCol;

    @FXML
    private TableColumn<User, String> emailCol;

    @FXML
    private TableColumn<User, String> type;

    @FXML
    private TableColumn<User, User> editCol;

    @FXML
    private TableColumn<User, User> deleteCol;

    private final UserController controller = new UserController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        updateTable();
        addButton.setOnMouseClicked(x->{
            createModal(null,(Stage) ((Node)x.getSource()).getScene().getWindow());
        });
    }


    @Override
    public void updateData() {
        updateTable();
    }

    private void updateTable(){
        createTable();
        startDeleteButton();
        startEditButton();
        userTableView.setItems(controller.getUsers());
    }

    private void createTable(){
        nameCol.setCellValueFactory(x->new SimpleStringProperty(x.getValue().getName()));
        emailCol.setCellValueFactory(x->new SimpleStringProperty(x.getValue().getEmail()));
        type.setCellValueFactory(x->new SimpleStringProperty(x.getValue().getType().getName()));
        idCol.setCellValueFactory(x-> new SimpleLongProperty(x.getValue().getId()).asObject());
    }

    private void startEditButton(){
        editCol.setCellValueFactory(x -> new ReadOnlyObjectWrapper<>(x.getValue()));
        editCol.setCellFactory(x->new TableCell<User, User>(){
            private final Button button = new Button("edit");

            @Override
            protected void updateItem(User item, boolean empty) {
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
        deleteCol.setCellValueFactory(x -> new ReadOnlyObjectWrapper<>(x.getValue()));
        deleteCol.setCellFactory(x->new TableCell<User, User>(){
            private final Button button = new Button("delete");

            @Override
            protected void updateItem(User item, boolean empty) {
                super.updateItem(item, empty);

                if(item == null){
                    setGraphic(null);
                    return;
                }
                setGraphic(button);

                button.setOnMouseClicked(x->{
                    controller.deleteUser(item);
                    updateTable();
                });
            }
        });
    }

    private void createModal(User user, Stage parent){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("form/user_form.fxml"));
            Pane pane = loader.load();
            UserFormBoundary formBoundary = loader.getController();
            formBoundary.setUser(user);
            formBoundary.addListener(this);
            formBoundary.updateFormData();
            Stage stage = new Stage();
            stage.setScene(new Scene(pane));
            stage.setTitle("User form");
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
