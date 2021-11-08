package br.gustavoakira.dentist.entity;

import lombok.Data;

@Data
public class Client {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
}
