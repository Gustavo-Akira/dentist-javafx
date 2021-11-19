package br.gustavoakira.dentist.dao;

import br.gustavoakira.dentist.entity.Appointment;

import java.util.List;

public interface AppointmentDao {
    List<Appointment> getAppointments(Long id);
    void insert(Appointment appointment);
    void delete(Long id);
    void update(Long id, Appointment appointment);
}
