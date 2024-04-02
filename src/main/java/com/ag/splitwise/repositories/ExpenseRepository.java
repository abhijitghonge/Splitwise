package com.ag.splitwise.repositories;


import com.ag.splitwise.models.Expense;
import com.ag.splitwise.models.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAllByGroup(UserGroup userGroup);
}
