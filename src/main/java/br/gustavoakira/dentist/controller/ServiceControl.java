package br.gustavoakira.dentist.controller;

import br.gustavoakira.dentist.dao.DaoFactory;
import br.gustavoakira.dentist.dao.ServiceDao;
import br.gustavoakira.dentist.entity.Services;
import br.gustavoakira.dentist.entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ServiceControl {
    private ServiceDao dao = DaoFactory.createServiceDao();

    public ObservableList<Services> getServices(User user){
        return FXCollections.observableList(dao.getServices(user.getId()));
    }

    public void insertService(Services services){
        dao.insert(services);
    }

    public void updateService(Services services){
        dao.update(services.getId(), services);
    }

    public void deleteService(Services services){
        dao.delete(services.getId());
    }
}
