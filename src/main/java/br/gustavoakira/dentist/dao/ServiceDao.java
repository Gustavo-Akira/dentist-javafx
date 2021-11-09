package br.gustavoakira.dentist.dao;

import br.gustavoakira.dentist.entity.Services;

import java.util.List;

public interface ServiceDao {
    void insert(Services services);
    void update(Services services);
    void delete(Services services);
    List<Services> getServices();
}
