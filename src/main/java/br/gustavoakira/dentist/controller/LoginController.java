package br.gustavoakira.dentist.controller;

import br.gustavoakira.dentist.dao.DaoFactory;
import br.gustavoakira.dentist.dao.UserDao;
import br.gustavoakira.dentist.entity.User;

public class LoginController {
    private UserDao dao = DaoFactory.createUserDao();

    public User login(String email, String password){
        return dao.login(email,password);
    }
}
