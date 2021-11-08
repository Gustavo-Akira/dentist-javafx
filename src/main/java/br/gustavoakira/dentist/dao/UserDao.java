package br.gustavoakira.dentist.dao;

import br.gustavoakira.dentist.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    User getOne(Long id);
    void insert(User user);
    void update(Long id, User user);
    void remove(Long id);
    List<User> getAll() ;
}
