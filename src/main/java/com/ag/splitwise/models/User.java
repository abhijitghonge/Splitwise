package com.ag.splitwise.models;


import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User extends BaseModel{


    String name;

    int phoneNumber;

    int password;
}
