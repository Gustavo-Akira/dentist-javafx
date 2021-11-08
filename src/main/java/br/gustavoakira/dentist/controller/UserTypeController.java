package br.gustavoakira.dentist.controller;

import br.gustavoakira.dentist.dao.DaoFactory;
import br.gustavoakira.dentist.dao.UserTypeDao;
import br.gustavoakira.dentist.entity.UserType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class UserTypeController {
    private UserTypeDao typeDao = DaoFactory.createUserTypeDao();

    public ObservableList<UserType> getUserTypes(){
        return FXCollections.observableList(typeDao.getUserTypes());
    }

    public UserType getUserType(Long id){
        return typeDao.getType(id);
    }
}
