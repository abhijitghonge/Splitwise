package com.ag.splitwise.services;

import com.ag.splitwise.enums.ExpenseType;
import com.ag.splitwise.enums.UserExpenseType;
import com.ag.splitwise.exceptions.InvalidGroupException;
import com.ag.splitwise.models.Expense;
import com.ag.splitwise.models.User;
import com.ag.splitwise.models.UserExpense;
import com.ag.splitwise.models.UserGroup;
import com.ag.splitwise.repositories.ExpenseRepository;
import com.ag.splitwise.repositories.GroupRepository;

import java.util.*;

public class SettleupService {

    private GroupRepository groupRepository;

    private ExpenseRepository expenseRepository;


    public List<Expense> settleUpUser(long userId) {
        return new ArrayList<>();

    }

    public List<Expense> settleUpGroup(long groupId) throws InvalidGroupException {
        Optional<UserGroup> groupById = groupRepository.findById(groupId);

        if (groupById.isPresent()) {
            UserGroup userGroup = groupById.get();
            List<Expense> expenses = expenseRepository.findAllByGroup(userGroup);
            return internalSettleup(expenses);

        } else {
            throw new InvalidGroupException("Group not found for:[" + groupId + "]");
        }
    }

    private List<Expense> internalSettleup(List<Expense> expenses) {
        List<Expense> reverseExpenses = new ArrayList<>();
        for (Expense expense : expenses) {
            Expense reverseExpense = new Expense();
            reverseExpenses.add(reverseExpense);

            reverseExpense.setExpenseType(ExpenseType.TRANSACTION);
            reverseExpense.setDescription("Settlement for:" + expense.getDescription());
            List<UserExpense> paidUpExpenses = expense.getUserExpenses().values()
                    .stream().filter(userExpense -> userExpense.getUserExpenseType() == UserExpenseType.PAID)
                    .sorted((e1, e2) -> Double.compare(e2.getAmount(), e1.getAmount()))
                    .toList();

            List<UserExpense> stillToPayExpenses = expense.getUserExpenses().values()
                    .stream().filter(userExpense -> userExpense.getUserExpenseType() == UserExpenseType.OWES)
                    .sorted((e1, e2) -> Double.compare(e2.getAmount(), e1.getAmount()))
                    .toList();
            int stillToPayIndex = 0;
            for (UserExpense paid : paidUpExpenses) {
                double pending = paid.getAmount();
                while (pending > 0 && stillToPayIndex < stillToPayExpenses.size()) {
                    UserExpense stillToPay = stillToPayExpenses.get(stillToPayIndex);
                    double toBeSettled = Math.min(paid.getAmount(), stillToPay.getAmount());
                    Map<User, UserExpense> settlementTransactions = new HashMap<>();
                    UserExpense received = UserExpense.builder()
                            .expense(reverseExpense)
                            .amount(toBeSettled)
                            .userExpenseType(UserExpenseType.OWES)
                            .user(paid.getUser()).build();


                    UserExpense paidToSettle = UserExpense.builder()
                            .expense(reverseExpense)
                            .amount(toBeSettled)
                            .userExpenseType(UserExpenseType.PAID)
                            .user(stillToPay.getUser()).build();
                    settlementTransactions.put(received.getUser(), received);
                    settlementTransactions.put(paidToSettle.getUser(), paidToSettle);
                    reverseExpense.setUserExpenses(settlementTransactions);
                    pending -= toBeSettled;
                    stillToPayIndex++;
                }
            }
        }
        return reverseExpenses;
    }

}
