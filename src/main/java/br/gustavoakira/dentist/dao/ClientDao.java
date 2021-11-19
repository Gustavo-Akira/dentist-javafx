package br.gustavoakira.dentist.dao;

import br.gustavoakira.dentist.entity.Client;

import java.util.List;

public interface ClientDao {
    void insert(Client client);
    void update(Long id, Client client);
    void delete(Long id);
    List<Client> getAll(Long id);
    Client getOne(Long id);
}
