package br.gustavoakira.dentist.dao;

import br.gustavoakira.dentist.entity.UserType;

import java.util.List;

public interface UserTypeDao {
    UserType getType(Long id);
    List<UserType> getUserTypes();
}
