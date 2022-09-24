package com.reniec.api.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "t_persona")
public class Persona {
    @Id
    @Column(name = "dni")
    private String dni;
    private String nombres;
    private String ApePat;
    private String ApeMat;
    private String direc;
    private String genero;
    private Date birthdate;
    private String estado;

}
