package br.gustavoakira.dentist.boundary.form;

import br.gustavoakira.dentist.boundary.listener.IListener;
import br.gustavoakira.dentist.controller.security.LoginControl;
import br.gustavoakira.dentist.controller.ServiceControl;
import br.gustavoakira.dentist.entity.Services;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ServiceFormBoundary implements Initializable {

    private Services services;

    @FXML
    private TextField id;

    @FXML
    private TextField name;

    @FXML
    private TextField price;

    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;

    private List<IListener> listeners = new ArrayList<>();

    private ServiceControl controller = new ServiceControl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addButton.setOnMouseClicked(x->{
            Services services = new Services();
            services.setPrice(Double.parseDouble(price.getText()));
            services.setUser(LoginControl.getLogged());
            services.setName(name.getText());
            if(!id.getText().isEmpty()){
                services.setId(Long.parseLong(id.getText()));
                controller.updateService(services);
            }else{
                controller.insertService(services);
            }
            sendNotification();
            ((Stage) ((Node)x.getSource()).getScene().getWindow()).close();
        });
        cancelButton.setOnMouseClicked(x->{
            ((Stage) ((Node)x.getSource()).getScene().getWindow()).close();
        });
    }

    public void updateForm(){
        if(services != null){
            id.setText(services.getId().toString());
            name.setText(services.getName());
            price.setText(services.getPrice().toString());
        }
    }

    public void addListener(IListener listener){
        this.listeners.add(listener);
    }

    private void sendNotification(){
        listeners.stream().forEach(x->x.updateData());
    }

    public void setServices(Services services) {
        this.services = services;
    }
}
