package br.gustavoakira.dentist.dao;


import br.gustavoakira.dentist.entity.Phone;

import java.util.List;

public interface PhoneDao {
    void insert(Phone phone);
    void update(Long id, Phone phone);
    void delete(Long id);
    List<Phone> getAll(Long id);
}
