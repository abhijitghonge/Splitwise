package com.ag.Splitwise.models;

import com.ag.Splitwise.enums.ExpenseType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Entity
public class Expense extends BaseModel{

    String description;

    double amount;

    @OneToMany(mappedBy = "expense")
    @MapKey(name = "user")
    private Map<User, UserExpense> userExpenses;

    @Enumerated(EnumType.ORDINAL)
    private ExpenseType expenseType;

}
