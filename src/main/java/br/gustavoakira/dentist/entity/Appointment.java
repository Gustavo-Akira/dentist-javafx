package br.gustavoakira.dentist.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Appointment {
    private Long id;
    private User user;
    private Client client;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
