package br.gustavoakira.dentist.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Appointment {
    private Long id;
    private User user;
    private Client client;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Services> services;

    public Double getTotal(){
        if(services.isEmpty()){
            return 0.0;
        }
        return services.stream().mapToDouble(Services::getPrice).sum();
    }
}
