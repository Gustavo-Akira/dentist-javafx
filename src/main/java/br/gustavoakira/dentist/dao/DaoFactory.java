package br.gustavoakira.dentist.dao;

import br.gustavoakira.dentist.dao.impl.*;
import br.gustavoakira.dentist.db.DB;

public class DaoFactory {
    public static UserTypeDao createUserTypeDao(){
        return new UserTypeDaoImpl(DB.getConnection());
    }

    public static UserDao createUserDao(){
        return new UserDaoImpl(DB.getConnection(),createUserTypeDao());
    }

    public static ServiceDao createServiceDao(){return  new ServiceDaoImpl(DB.getConnection(),createUserDao());}

    public static ClientDao createClientDao(){
        return new ClientDaoImpl(DB.getConnection(), createUserDao());
    }

    public static AddressDao createAddressDao(){
        return new AddressDaoImpl(DB.getConnection());
    }

    public static PhoneDao createPhoneDao(){
        return new PhoneDaoImpl(DB.getConnection());
    }
}
