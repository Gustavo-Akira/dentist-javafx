package br.gustavoakira.dentist.dao.impl;

import br.gustavoakira.dentist.dao.UserTypeDao;
import br.gustavoakira.dentist.db.DB;
import br.gustavoakira.dentist.db.exception.DbException;
import br.gustavoakira.dentist.entity.User;
import br.gustavoakira.dentist.entity.UserType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserTypeDaoImpl implements UserTypeDao {

    Connection connection;

    public UserTypeDaoImpl(Connection connection){
        this.connection = connection;
    }

    @Override
    public UserType getType(Long id) {
        ResultSet set = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM users_type where Id =?");
            statement.setLong(1,id);
            set = statement.executeQuery();
            if(set.next()){
                UserType userType = new UserType();
                userType.setId(set.getLong("Id"));
                userType.setName(set.getString("Name"));
                return userType;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.closeStatement(statement);
            DB.closeResultSet(set);
        }
        return null;
    }

    @Override
    public List<UserType> getUserTypes() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(
                    "SELECT * FROM users_type");
            resultSet = statement.executeQuery();

            List<UserType> list = new ArrayList<>();

            while (resultSet.next()) {
                UserType obj = new UserType();
                obj.setId(resultSet.getLong("Id"));
                obj.setName(resultSet.getString("Name"));
                list.add(obj);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
            DB.closeResultSet(resultSet);
        }
    }
}
