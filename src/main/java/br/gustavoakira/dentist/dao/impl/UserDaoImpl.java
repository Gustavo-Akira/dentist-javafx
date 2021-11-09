package br.gustavoakira.dentist.dao.impl;

import br.gustavoakira.dentist.dao.UserDao;
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

public class UserDaoImpl implements UserDao {

    private final Connection connection;

    private final UserTypeDao typeDao;

    public UserDaoImpl(Connection connection, UserTypeDao dao){
        this.connection = connection;
        this.typeDao = dao;
    }



    @Override
    public User login(String email, String password) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM users WHERE Email = ? AND Password = ?");
            statement.setString(1,email);
            statement.setString(2,password);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User obj = new User();
                obj.setId(resultSet.getLong("Id"));
                obj.setName(resultSet.getString("Name"));
                obj.setEmail(resultSet.getString("Email"));
                obj.setType(typeDao.getType(resultSet.getLong("UserTypeId")));
                obj.setPassword(resultSet.getString("Password"));
                return obj;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.closeStatement(statement);
            DB.closeResultSet(resultSet);
        }
        return null;
    }

    @Override
    public void insert(User user) {
        String sql = "INSERT INTO users(Name,Email,Password,UserTypeId) VALUES (?,?,?,?)";
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getName());
            statement.setString(2,user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setLong(4,user.getType().getId());
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DB.closeStatement(statement);
        }
    }

    @Override
    public void update(Long id, User user) {
        String sql = "UPDATE users SET Name=?, Email=?, Password=? WHERE Id=?";
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getName());
            statement.setString(2,user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setLong(4,user.getId());
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DB.closeStatement(statement);
        }
    }

    @Override
    public void remove(Long id) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("DELETE users WHERE Id = ?");
            statement.setLong(1,id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.closeStatement(statement);
        }

    }

    @Override
    public List<User> getAll(){
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(
                    "SELECT * FROM users ORDER BY Name");
            resultSet = statement.executeQuery();

            List<User> list = new ArrayList<>();

            while (resultSet.next()) {
                User obj = new User();
                obj.setId(resultSet.getLong("Id"));
                obj.setEmail(resultSet.getString("Email"));
                obj.setName(resultSet.getString("Name"));
                obj.setType(typeDao.getType(resultSet.getLong("UserTypeId")));
                obj.setPassword(resultSet.getString("Password"));
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
