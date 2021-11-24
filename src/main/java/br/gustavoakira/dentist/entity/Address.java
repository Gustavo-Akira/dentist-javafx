package br.gustavoakira.dentist.entity;

import lombok.Data;

@Data
public class Address {
    private Long id;
    private String num;
    private String street;
    private String city;
    private String district;
    private String cep;
    private String Complement;
    private Client client;
}
