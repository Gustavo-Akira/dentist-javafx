package br.gustavoakira.dentist.dao.impl;

import br.gustavoakira.dentist.controller.security.LoginController;
import br.gustavoakira.dentist.dao.ClientDao;
import br.gustavoakira.dentist.dao.UserDao;
import br.gustavoakira.dentist.db.DB;
import br.gustavoakira.dentist.entity.Client;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ClientDaoImpl implements ClientDao {

    private final Connection connection;

    private final UserDao dao;

    @Override
    public void insert(Client client) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO clients(Name,UserId) VALUES(?,?)");
            statement.setString(1,client.getName());
            statement.setLong(2, LoginController.getLogged().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.closeStatement(statement);
        }
    }

    @Override
    public void update(Long id, Client client) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE clients SET Name=? WHERE Id=?");
            statement.setString(1,client.getName());
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.closeStatement(statement);
        }
    }

    @Override
    public void delete(Long id) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("DELETE clients WHERE Id=?");
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.closeStatement(statement);
        }
    }

    @Override
    public List<Client> getAll(Long id) {
        PreparedStatement statement = null;
        ResultSet set = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM clients WHERE UserId=?");
            statement.setLong(1,id);
            set = statement.executeQuery();
            List<Client> clients = new ArrayList<>();
            while (set.next()){
                Client client = new Client();
                client.setId(set.getLong("Id"));
                client.setName(set.getString("Name"));
                client.setUser(dao.getOne(id));
                clients.add(client);
            }
            return clients;
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DB.closeStatement(statement);
            DB.closeResultSet(set);
        }
        return null;
    }

    @Override
    public Client getOne(Long id) {
        PreparedStatement statement = null;
        ResultSet set = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM clients WHERE Id=?");
            statement.setLong(1,id);
            set = statement.executeQuery();
            if(set.next()){
                Client client = new Client();
                client.setId(set.getLong("Id"));
                client.setName(set.getString("Name"));
                client.setUser(dao.getOne(id));
                return client;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DB.closeStatement(statement);
            DB.closeResultSet(set);
        }
        return null;
    }
}
