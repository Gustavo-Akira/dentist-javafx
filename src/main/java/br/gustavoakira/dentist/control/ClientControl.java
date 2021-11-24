package br.gustavoakira.dentist.control;

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
}
