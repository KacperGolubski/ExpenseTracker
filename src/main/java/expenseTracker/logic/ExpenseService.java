package expenseTracker.logic;

import expenseTracker.domain.Expense;
import expenseTracker.domain.ExpenseType;

import java.time.LocalDate;
import java.util.ArrayList;
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

    public List<Expense> filterExpensesByName(List<Expense> inputList, String name){
        List<Expense> filteredExpenses = inputList;
        if(name != null && !name.isEmpty()){
            List<Expense> temp = new ArrayList<>();
            for (Expense expense : filteredExpenses) {
                if (expense.getName().equals(name)) {
                    temp.add(expense);
                }
            }
            filteredExpenses = temp;
        }
        return filteredExpenses;
    }

    public List<Expense> filterExpenseByType(List<Expense> inputList, ExpenseType expenseType) {
        List<Expense> filteredExpenses = inputList;
        if (expenseType != null) {
            List<Expense> temp = new ArrayList<>();
            for (Expense expense : filteredExpenses) {
                if (expense.getExpenseType().equals(expenseType)) {
                    temp.add(expense);
                }
            }
            filteredExpenses = temp;
        }
        return filteredExpenses;
    }

    public List<Expense> filterExpensesByDates(List<Expense> inputList, LocalDate dateFrom, LocalDate dateTo) {
        List<Expense> filteredExpenses = inputList;
        if (dateFrom != null) {
            List<Expense> temp = new ArrayList<>();
            for (Expense expense : filteredExpenses) {
                if (expense.getDate().isAfter(dateFrom)) {
                    temp.add(expense);
                }
            }
            filteredExpenses = temp;
        }

        if (dateTo != null) {
            List<Expense> temp = new ArrayList<>();
            for (Expense expense : filteredExpenses) {
                if (expense.getDate().isBefore(dateTo)) {
                    temp.add(expense);
                }
            }
            filteredExpenses = temp;
        }
        return filteredExpenses;
    }

    public List<Expense> filterByNameShopNameDatesType(String name, String shopName, LocalDate dateFrom, LocalDate dateTo, ExpenseType expenseType) {
        List<Expense> result = new ArrayList<>(expenses);
        result = filterExpensesByName(result,name);
        result = filterExpensesByName(result,shopName);
        result = filterExpensesByDates(result,dateFrom,dateTo);
        result = filterExpenseByType(result,expenseType);
        return result;
    }
}

