package br.gustavoakira.dentist.dao;

import br.gustavoakira.dentist.entity.Services;

import java.util.List;

public interface ServiceDao {
    void insert(Services services);
    void update(Long id, Services services);
    void delete(Long id);
    List<Services> getServices(Long id);
}
