package br.gustavoakira.dentist.dao.impl;

import br.gustavoakira.dentist.dao.PhoneDao;
import br.gustavoakira.dentist.db.DB;
import br.gustavoakira.dentist.entity.Phone;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
public class PhoneDaoImpl implements PhoneDao {

    private final Connection connection;

    @Override
    public void insert(Phone phone) {
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement("INSERT INTO telephones(Num, DDD, Country, ClientId) VALUES(?,?,?,?)");
            statement.setString(1,phone.getNum());
            statement.setString(2,phone.getDdd());
            statement.setString(3,phone.getCountry());
            statement.setLong(4,phone.getClient().getId());
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DB.closeStatement(statement);
        }
    }

    @Override
    public void update(Long id, Phone phone) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE telephones SET Num=?, DDD=?, Country=? WHERE Id=?");
            statement.setString(1,phone.getNum());
            statement.setString(2,phone.getDdd());
            statement.setString(3,phone.getCountry());
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DB.closeStatement(statement);
        }
    }

    @Override
    public void delete(Long id) {
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement("DELETE FROM telephones WHERE Id=?");
            statement.setLong(1,id);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DB.closeStatement(statement);
        }
    }

    @Override
    public List<Phone> getAll(Long id) {
        PreparedStatement statement = null;
        ResultSet set = null;
        try{
            statement = connection.prepareStatement("SELECT * FROM telephones WHERE ClientId=?");
            statement.setLong(1,id);
            set = statement.executeQuery();
            List<Phone> phones = new ArrayList<>();
            while(set.next()){
                Phone phone = new Phone();
                phone.setId(set.getLong("Id"));
                phone.setCountry(set.getString("Country"));
                phone.setDdd(set.getString("DDD"));
                phone.setNum(set.getString("Num"));
                phones.add(phone);
            }
            return phones;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
