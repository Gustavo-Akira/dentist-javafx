package br.gustavoakira.dentist.boundary.form;

import br.gustavoakira.dentist.boundary.listener.IListener;
import br.gustavoakira.dentist.controller.PhoneControl;
import br.gustavoakira.dentist.entity.Client;
import br.gustavoakira.dentist.entity.Phone;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

@Setter
public class PhoneFormBoundary implements Initializable {
    private Client client;
    private Phone phone;
    private IListener listener;
    private PhoneControl controller = new PhoneControl();

    @FXML
    private TextField id;

    @FXML
    private TextField num;

    @FXML
    private TextField ddd;

    @FXML
    private TextField country;

    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;

    public void updateForm(){
        id.setText(phone.getId().toString());
        num.setText(phone.getNum());
        ddd.setText(phone.getDdd());
        country.setText(phone.getCountry());
    }

    private void sendNotification(){
        this.listener.updateData();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addButton.setOnMouseClicked(x->{
            Phone phone = mountPhone();
            if(phone.getId() != null){
                controller.update(phone);
            }else{
                phone.setClient(client);
                controller.insert(phone);
            }
            sendNotification();
            ((Stage) ((Node)x.getSource()).getScene().getWindow()).close();
        });
        cancelButton.setOnMouseClicked(x->{
            ((Stage) ((Node)x.getSource()).getScene().getWindow()).close();
        });
    }

    private Phone mountPhone(){
        Phone phone = new Phone();
        if(!this.id.getText().isEmpty()){
            phone.setId(Long.parseLong(id.getText()));
        }
        if(!this.num.getText().isEmpty()){
            phone.setNum(num.getText());
        }
        if(!this.country.getText().isEmpty()){
            phone.setCountry(country.getText());
        }
        if(!this.ddd.getText().isEmpty()){
            phone.setDdd(ddd.getText());
        }
        return phone;
    }
}
