package br.gustavoakira.dentist.entity;

import lombok.Data;

@Data
public class Services {
    private Long id;
    private String name;
    private Double price;
    private User user;
}
