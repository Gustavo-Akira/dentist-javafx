package br.gustavoakira.dentist.dao;

import br.gustavoakira.dentist.entity.Address;

import java.util.List;

public interface AddressDao {
    void insert(Address address);
    void update(Long id, Address address);
    void delete(Long id);
    List<Address> getAll(Long id);
}
