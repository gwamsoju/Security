package com.cos.security1.vo;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
public class User1 {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String username;
    private String password;
    private String email;
    private String role; //ROLE_USER, ROLE_ADMIN
    @CreationTimestamp
    private Timestamp createDate;
}
