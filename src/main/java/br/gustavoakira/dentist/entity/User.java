package br.gustavoakira.dentist.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class User {
    private Long id;
    private String name;
    private String email;
    private String password;
    private UserType type;
    private List<Services> services = new ArrayList<>();
}
