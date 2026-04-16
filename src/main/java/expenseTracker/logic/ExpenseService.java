package expenseTracker.logic;

import expenseTracker.domain.Expense;
import expenseTracker.logic.FileExpenseRepository;
import java.util.List;

public class ExpenseService {
    private List<Expense> expenses;
    private FileExpenseRepository repository;

    public ExpenseService(FileExpenseRepository repository) {
        this.repository = repository;
        this.expenses = repository.loadExpenses();
    }

    public void addExpense(Expense expense){
        expenses.add(expense);
        repository.saveExpenses(expenses);
    }


}
