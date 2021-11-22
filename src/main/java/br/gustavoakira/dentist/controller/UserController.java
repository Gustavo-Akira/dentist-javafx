package br.gustavoakira.dentist.controller;

import br.gustavoakira.dentist.controller.security.SecurityUtil;
import br.gustavoakira.dentist.dao.DaoFactory;
import br.gustavoakira.dentist.dao.UserDao;
import br.gustavoakira.dentist.entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserController {

    private UserDao dao = DaoFactory.createUserDao();

    private ObservableList<User> users = FXCollections.emptyObservableList();

    public ObservableList<User> getUsers(){
        users = FXCollections.observableList(dao.getAll());
        return users;
    }

    public User getUser(Long id){
        return users.stream().filter(x-> x.getId().equals(id)).findFirst().get();
    }

    public void insertUser(User user){
        user.setPassword(SecurityUtil.hashPassword(user.getPassword()));
        dao.insert(user);
    }

    public void updateUser(User user){
        if(user.getPassword() != null) {
            user.setPassword(SecurityUtil.hashPassword(user.getPassword()));
        }
        dao.update(user.getId(),user);
    }

    public void deleteUser(User user){
        dao.remove(user.getId());
    }
}
