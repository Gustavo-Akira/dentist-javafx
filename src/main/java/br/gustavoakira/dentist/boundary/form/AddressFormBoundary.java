package br.gustavoakira.dentist.boundary.form;

import br.gustavoakira.dentist.boundary.listener.IListener;
import br.gustavoakira.dentist.controller.AddressController;
import br.gustavoakira.dentist.entity.Address;
import br.gustavoakira.dentist.entity.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddressFormBoundary implements Initializable {

    private Address address = new Address();

    private Long client;

    private IListener listener;

    @FXML
    private TextField id;

    @FXML
    private TextField street;

    @FXML
    private TextField num;

    @FXML
    private TextField district;

    @FXML
    private TextField city;

    @FXML
    private TextField complement;

    @FXML
    private TextField cep;

    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;


    private final AddressController addressController = new AddressController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addButton.setOnMouseClicked(x->{
            Address address = mountAddress();
            if(address.getId() == null) {
                Client o = new Client();
                o.setId(client);
                address.setClient(o);
                addressController.insert(address);
            }else{
                addressController.update(address);
            }
            sendNotification();
            ((Stage) ((Node)x.getSource()).getScene().getWindow()).close();
        });
        cancelButton.setOnMouseClicked(x->{
            ((Stage) ((Node)x.getSource()).getScene().getWindow()).close();
        });
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    private Address mountAddress(){
        Address address = new Address();
        if(!this.id.getText().isEmpty()){
            address.setId(Long.parseLong(this.id.getText()));
        }
        if(!this.num.getText().isEmpty()){
            address.setNum(this.num.getText());
        }
        if(!this.street.getText().isEmpty()){
            address.setStreet(this.street.getText());
        }
        if(!this.cep.getText().isEmpty()) {
            address.setCep(this.cep.getText());
        }
        if(!this.complement.getText().isEmpty()){
            address.setComplement(this.complement.getText());
        }
        if(!this.city.getText().isEmpty()){
            address.setCity(this.city.getText());
        }
        if(!this.district.getText().isEmpty()){
            address.setDistrict(this.district.getText());
        }

        return address;
    }

    public void updateForm(){
        this.id.setText(address.getId().toString());
        this.num.setText(address.getNum());
        this.street.setText(address.getStreet());
        this.district.setText(address.getDistrict());
        this.city.setText(address.getCity());
        this.complement.setText(address.getComplement());
        this.cep.setText(address.getCep());
    }

    public void setListener(IListener listener) {
        this.listener = listener;
    }

    private void sendNotification(){
        this.listener.updateData();
    }

    public void setClient(Long client) {
        this.client = client;
    }
}
