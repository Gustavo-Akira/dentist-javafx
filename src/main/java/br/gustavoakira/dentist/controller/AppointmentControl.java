package br.gustavoakira.dentist.controller;

import br.gustavoakira.dentist.App;
import br.gustavoakira.dentist.dao.AppointmentDao;
import br.gustavoakira.dentist.dao.DaoFactory;
import br.gustavoakira.dentist.entity.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AppointmentControl {
    private final AppointmentDao dao = DaoFactory.createAppointmentDao();

    public ObservableList<Appointment> getAppointments(Long id){
        return FXCollections.observableList(dao.getAppointments(id));
    }

    public void insert(Appointment appointment){
        dao.insert(appointment);
    }

    public void update(Appointment appointment){
        dao.update(appointment.getId(),appointment);
    }

    public void delete(Appointment appointment){
        dao.delete(appointment.getId());
    }
}
