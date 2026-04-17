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

    public boolean addExpense(Expense expense){
        if(expense == null){
            return false;
        }
        expenses.add(expense);
        repository.saveExpenses(expenses);
        return true;
    }

    public Expense getExpenseById(String id){
        for(Expense expenseById : expenses){
            if(expenseById.getId().equals(id)){
                return expenseById;
            }
        } return null;
    }

    public boolean deleteExpense(String id){
        Expense expenseToDelete = getExpenseById(id);
        if(expenseToDelete == null){
            return false;
        }
        expenses.remove(expenseToDelete);
        repository.saveExpenses(expenses);
        return true;
    }

    public boolean updateExpense(String id, Expense updateData){
        Expense existingExpense = getExpenseById(id);
        if(existingExpense == null){
            return false;
        }
        existingExpense.setName(updateData.getName());
        existingExpense.setShopName(updateData.getShopName());
        existingExpense.setDescription(updateData.getDescription());
        existingExpense.setDate(updateData.getDate());
        existingExpense.setExpenseType(updateData.getExpenseType());
        existingExpense.setPrice(updateData.getPrice());
        repository.saveExpenses(expenses);
        return true;
    }

    public List<Expense> getExpenses(){
        return expenses;
    }


}
