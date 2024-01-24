package com.ag.Splitwise.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class UserGroup extends BaseModel{

    @ManyToMany
    private List<User> users;

    private String name;

    @OneToMany
    @JoinColumn(name = "expense_id")
    private List<Expense> expenses;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private User admin;

}
