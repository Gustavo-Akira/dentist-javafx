package br.gustavoakira.dentist.control;

import br.gustavoakira.dentist.dao.AddressDao;
import br.gustavoakira.dentist.dao.DaoFactory;
import br.gustavoakira.dentist.entity.Address;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AddressControl {

    private AddressDao dao = DaoFactory.createAddressDao();

    public ObservableList<Address> getAddresses(Long id){
        return FXCollections.observableList(dao.getAll(id));
    }

    public void insert(Address address){
        dao.insert(address);
    }

    public void update(Address address){
        dao.update(address.getId(), address);
    }

    public void delete(Address address){
        dao.delete(address.getId());
    }
}
