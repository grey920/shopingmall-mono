package com.sparta.shoppingmallmono.user.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String email;
    private String password;
    private String phone;
    @Embedded
    private Address address;
    private char gender; // todo: enum 으로 변경
    private boolean use_2nd_auth;

    public User( String name, String email, String password, String phone, Address address, char gender, boolean use_2nd_auth ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
        this.use_2nd_auth = use_2nd_auth;
    }
}
