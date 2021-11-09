package br.gustavoakira.dentist.dao.impl;

import br.gustavoakira.dentist.dao.ServiceDao;
import br.gustavoakira.dentist.entity.Services;

import java.sql.Connection;
import java.util.List;

public class ServiceDaoImpl implements ServiceDao {

    private Connection connection;

    public ServiceDaoImpl(Connection connection){
        this.connection = connection;
    }

    @Override
    public void insert(Services services) {

    }

    @Override
    public void update(Services services) {

    }

    @Override
    public void delete(Services services) {

    }

    @Override
    public List<Services> getServices() {
        return null;
    }
}
