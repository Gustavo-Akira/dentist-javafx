package br.gustavoakira.dentist.controller;

import br.gustavoakira.dentist.dao.AddressDao;
import br.gustavoakira.dentist.dao.ClientDao;
import br.gustavoakira.dentist.dao.DaoFactory;
import br.gustavoakira.dentist.dao.PhoneDao;
import br.gustavoakira.dentist.entity.Client;
import br.gustavoakira.dentist.entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ClientControl {
    private final ClientDao dao = DaoFactory.createClientDao();

    private final AddressDao addressDao = DaoFactory.createAddressDao();

    private final PhoneDao phoneDao = DaoFactory.createPhoneDao();

    public ObservableList<Client> getClients(User user, String name){
        return FXCollections.observableList(dao.getAll(user.getId(),name));
    }

    public void insert(Client client){
        dao.insert(client);
    }

    public void update(Client client){
        dao.update(client.getId(),client);
    }

    public void delete(Long id){
        dao.delete(id);
    }

    public Client getDetails(Client client){
        client.setAddress(addressDao.getAll(client.getId()));
        client.setPhoneNumber(phoneDao.getAll(client.getId()));
        return client;
    }
}
