package br.gustavoakira.dentist.entity;

import lombok.Data;

@Data
public class Appointment {
    private Long id;
    private User user;
    private Client client;
}
