package br.gustavoakira.dentist.controller;

import br.gustavoakira.dentist.dao.DaoFactory;
import br.gustavoakira.dentist.dao.PhoneDao;
import br.gustavoakira.dentist.entity.Phone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PhoneController {
    private final PhoneDao dao = DaoFactory.createPhoneDao();

    public void insert(Phone phone){
        dao.insert(phone);
    }

    public void update(Phone phone){
        dao.update(phone.getId(), phone);
    }

    public void delete(Phone phone){
        dao.delete(phone.getId());
    }

    public ObservableList<Phone> getPhones(Long id){
        return FXCollections.observableList(dao.getAll(id));
    }
}
