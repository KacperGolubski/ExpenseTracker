package expenseTracker.logic;

import expenseTracker.domain.Expense;
import static expenseTracker.logic.Console.createExpense;

public class ExpenseHandler {

    private final ExpenseService expenseService;

    public ExpenseHandler(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    public void addExpenseMain(){
        Expense expense = Console.createExpense();
        if(expenseService.addExpense(expense)){
            System.out.println("Expense added successfully");
            return;
        } else  {
            System.out.println("Expense could not be added");
            return;
        }
    }

    public void updateExpenseMain(){
        Expense expenseToBeUpdated = Console.findAndSelectExpense(expenseService);
        if(expenseToBeUpdated == null){
            return;
        }
        System.out.println("Now provide new details for this expense");
        Expense expenseUpdate = createExpense();
        if(expenseService.updateExpense(expenseToBeUpdated.getId(),  expenseUpdate)){
            System.out.println("Expense updated successfully");
        } else  {
            System.out.println("Expense could not be updated");
        }
    }

    public void deleteExpenseMain(){
        Expense expenseToBeDeleted = Console.findAndSelectExpense(expenseService);
        if(expenseToBeDeleted == null){
            return;
        }
        if(expenseService.deleteExpense(expenseToBeDeleted.getId())){
            System.out.println("Expense deleted successfully");
        } else  {
            System.out.println("Expense could not be deleted");
        }
    }
}
