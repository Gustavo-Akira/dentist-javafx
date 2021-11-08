package br.gustavoakira.dentist.dao;

import br.gustavoakira.dentist.dao.impl.UserDaoImpl;
import br.gustavoakira.dentist.dao.impl.UserTypeDaoImpl;
import br.gustavoakira.dentist.db.DB;

public class DaoFactory {
    public static UserTypeDao createUserTypeDao(){
        return new UserTypeDaoImpl(DB.getConnection());
    }

    public static UserDao createUserDao(){
        return new UserDaoImpl(DB.getConnection(),createUserTypeDao());
    }
}
