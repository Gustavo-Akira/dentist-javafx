package br.gustavoakira.dentist.dao.impl;

import br.gustavoakira.dentist.controller.security.LoginController;
import br.gustavoakira.dentist.dao.AppointmentDao;
import br.gustavoakira.dentist.dao.ClientDao;
import br.gustavoakira.dentist.db.DB;
import br.gustavoakira.dentist.entity.Appointment;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class AppointmentDaoImpl implements AppointmentDao {

    private final Connection connection;

    private final ClientDao dao;

    @Override
    public List<Appointment> getAppointments(Long id) {
        PreparedStatement statement = null;
        ResultSet set = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM appointments WHERE UserId=? AND time_start>=GETDATE() AND time_end>=GETDATE() ORDER BY time_start");
            statement.setLong(1,id);
            set = statement.executeQuery();
            List<Appointment> appointments = new ArrayList<>();
            while(set.next()){
                Appointment appointment = new Appointment();
                appointment.setClient(dao.getOne(set.getLong("ClientId")));
                appointment.setStartDate(set.getObject("time_start", LocalDateTime.class));
                appointment.setEndDate(set.getObject("time_end", LocalDateTime.class));
                appointment.setId(set.getLong("Id"));
                appointments.add(appointment);
            }
            return appointments;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DB.closeStatement(statement);
            DB.closeResultSet(set);
        }
        return null;
    }

    @Override
    public void insert(Appointment appointment) {
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement("INSERT INTO appointments(time_start,time_end,ClientId,UserId) VALUES(?,?,?,?)");
            statement.setTimestamp(1, Timestamp.valueOf(appointment.getStartDate().truncatedTo(ChronoUnit.SECONDS)));
            statement.setTimestamp(2,Timestamp.valueOf(appointment.getEndDate().truncatedTo(ChronoUnit.SECONDS)));
            statement.setLong(3,appointment.getClient().getId());
            statement.setLong(4, LoginController.getLogged().getId());
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DB.closeStatement(statement);
        }
    }

    @Override
    public void delete(Long id) {
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement("DELETE appointments WHERE Id=?");
            statement.setLong(1,id);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DB.closeStatement(statement);
        }
    }

    @Override
    public void update(Long id, Appointment appointment) {
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement("UPDATE appointments SET time_start=?, time_end=? WHERE Id=?");
            statement.setTimestamp(1, Timestamp.valueOf(appointment.getStartDate()));
            statement.setTimestamp(2,Timestamp.valueOf(appointment.getEndDate()));
            statement.setLong(3,appointment.getId());
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DB.closeStatement(statement);
        }
    }
}
