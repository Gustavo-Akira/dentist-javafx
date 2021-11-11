package br.gustavoakira.dentist.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Client {
    private Long id;
    private String name;
    private List<Address> address = new ArrayList<>();
    private List<Phone> phoneNumber = new ArrayList<>();
    private User user;
}
