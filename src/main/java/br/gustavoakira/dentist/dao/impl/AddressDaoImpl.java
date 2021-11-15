package br.gustavoakira.dentist.dao.impl;

import br.gustavoakira.dentist.dao.AddressDao;
import br.gustavoakira.dentist.dao.ClientDao;
import br.gustavoakira.dentist.db.DB;
import br.gustavoakira.dentist.entity.Address;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@AllArgsConstructor
public class AddressDaoImpl implements AddressDao {

    private final Connection connection;

    @Override
    public void insert(Address address) {
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement("INSERT INTO addresses(Num,Street,City,District,CEP, Complements, ClientId) VALUES(?,?,?,?,?,?,?)");
            statement.setString(1,address.getNum());
            statement.setString(2,address.getStreet());
            statement.setString(3, address.getCity());
            statement.setString(4,address.getDistrict());
            statement.setString(5,address.getCep());
            statement.setString(6, address.getComplement() == null ? " Não tem": address.getComplement());
            statement.setLong(7,address.getClient().getId());
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DB.closeStatement(statement);
        }
    }

    @Override
    public void update(Long id, Address address) {
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement("UPDATE addresses SET Num=?, Street=?,City=?,District=?,CEP=?, Complements=? WHERE Id=?");
            statement.setString(1,address.getNum());
            statement.setString(2,address.getStreet());
            statement.setString(3,address.getCity());
            statement.setString(4,address.getDistrict());
            statement.setString(5,address.getCep());
            statement.setString(6,address.getComplement());
            statement.setLong(7, id);
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
            statement = connection.prepareStatement("DELETE FROM addresses WHERE Id = ?");
            statement.setLong(1,id);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DB.closeStatement(statement);
        }
    }

    @Override
    public List<Address> getAll(Long id) {
        PreparedStatement statement = null;
        ResultSet set = null;
        try{
            statement = connection.prepareStatement("SELECT * FROM addresses WHERE ClientId=?");
            statement.setLong(1, id);
            set = statement.executeQuery();
            List<Address> addresses = new ArrayList<>();
            while (set.next()){
                Address address = new Address();
                address.setId(set.getLong("Id"));
                address.setNum(set.getString("Num"));
                address.setCity(set.getString("City"));
                address.setDistrict(set.getString("District"));
                address.setComplement(set.getString("Complements"));
                address.setCep(set.getString("CEP"));
                address.setStreet(set.getString("Street"));
                addresses.add(address);
            }
            return addresses;
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DB.closeStatement(statement);
            DB.closeResultSet(set);
        }
        return null;
    }
}
