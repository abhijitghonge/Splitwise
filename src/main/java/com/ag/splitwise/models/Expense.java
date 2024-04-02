package com.ag.splitwise.models;

import com.ag.splitwise.enums.ExpenseType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Entity
public class Expense extends BaseModel{

    private String description;

    private double amount;

    @OneToMany(mappedBy = "expense")
    @MapKey(name = "user")
    private Map<User, UserExpense> userExpenses;

    @Enumerated(EnumType.ORDINAL)
    private ExpenseType expenseType;

    @ManyToOne
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private UserGroup group;

}
