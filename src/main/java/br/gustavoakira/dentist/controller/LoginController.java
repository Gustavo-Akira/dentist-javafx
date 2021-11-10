package br.gustavoakira.dentist.controller;

import br.gustavoakira.dentist.dao.DaoFactory;
import br.gustavoakira.dentist.dao.UserDao;
import br.gustavoakira.dentist.entity.User;

public class LoginController {

    private static User user;
    private UserDao dao = DaoFactory.createUserDao();

    public void login(String email, String password){
        user = dao.login(email,password);
    }

    public static User getLogged(){
        return user;
    }
}
