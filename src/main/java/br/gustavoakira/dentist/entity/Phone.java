package br.gustavoakira.dentist.entity;

import lombok.Data;

@Data
public class Phone {
    private Long id;
    private String num;
    private String ddd;
    private String country;
    private Client client;
}
