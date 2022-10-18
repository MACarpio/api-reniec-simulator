package com.reniec.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "t_user")
public class Usuario implements Serializable {
    @Id
    @Column(name = "user_id")
    private String userID;
    private String password;
    private String tipoUsuario;
    private String token;
}
