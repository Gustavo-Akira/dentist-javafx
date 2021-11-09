package br.gustavoakira.dentist.dao.impl;

import br.gustavoakira.dentist.dao.ServiceDao;
import br.gustavoakira.dentist.dao.UserDao;
import br.gustavoakira.dentist.db.DB;
import br.gustavoakira.dentist.entity.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceDaoImpl implements ServiceDao {

    private Connection connection;

    public ServiceDaoImpl(Connection connection, UserDao dao){
        this.connection = connection;
        this.dao = dao;
    }

    private UserDao dao;

    @Override
    public void insert(Services services) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO services(Name, Price, UserId) VALUES (?,?,?)");
            statement.setString(1,services.getName());
            statement.setDouble(2,services.getPrice());
            statement.setLong(3,services.getUser().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.closeStatement(statement);
        }
    }

    @Override
    public void update(Long id, Services services) {
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement("UPDATE services SET Name=?, Price=? WHERE Id=?");
            statement.setString(1,services.getName());
            statement.setDouble(2,services.getPrice());
            statement.setLong(3,services.getId());
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement("DELETE services WHERE Id=?");
            statement.setLong(1,id);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DB.closeStatement(statement);
        }
    }

    @Override
    public List<Services> getServices(Long id) {
        PreparedStatement statement = null;
        ResultSet set = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM services WHERE Id=?");
            set = statement.executeQuery();
            List<Services> services = new ArrayList<>();
            while (set.next()){
                Services service = new Services();
                service.setId(set.getLong("Id"));
                service.setName(set.getString("Name"));
                service.setPrice(set.getDouble("Price"));
                service.setUser(dao.getOne(id));
                services.add(service);
            }
            return services;
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DB.closeStatement(statement);
            DB.closeResultSet(set);
        }
        return null;
    }
}
