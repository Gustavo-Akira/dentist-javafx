package br.gustavoakira.dentist.controller.security;

import br.gustavoakira.dentist.dao.DaoFactory;
import br.gustavoakira.dentist.dao.UserDao;
import br.gustavoakira.dentist.entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class LoginController {

    private static User user;
    private UserDao dao = DaoFactory.createUserDao();

    public void login(String email, String password){
        user = dao.login(email,password);
    }

    public static User getLogged(){
        return user;
    }

    public static ObservableList<String> getAuthorizations(){
        List<String> authorizations = new ArrayList<>();
        String type = getLogged().getType().getName();
        switch (type){
            case "dentist":
                authorizations.add("Appontamentos");
                authorizations.add("Clientes");
                authorizations.add("Serviços");
                break;
            case "admin":
                authorizations.add("Usuarios");
                break;
        }
        authorizations.add("Perfil");
        return FXCollections.observableArrayList(authorizations);
    }
}
