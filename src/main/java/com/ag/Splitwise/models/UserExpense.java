package com.ag.Splitwise.models;

import com.ag.Splitwise.enums.UserExpenseType;
import jakarta.persistence.*;

@Entity
public class UserExpense extends BaseModel{
    @ManyToOne
    @JoinColumn(name = "expense_id")
    private Expense expense;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.ORDINAL)
    private UserExpenseType userExpenseType;


    private double amount;
}
