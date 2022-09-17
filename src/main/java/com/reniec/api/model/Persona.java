package com.reniec.api.model;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "t_persona")
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private String dni;
    @NotNull
    private String name;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName; 
    @NotNull
    private String address;
    @NotNull
    private String gender;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthdate;
    @NonNull
    private String status; 

}

