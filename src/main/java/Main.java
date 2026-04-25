import expenseTracker.domain.Expense;
import expenseTracker.logic.ExpenseService;
import expenseTracker.logic.FileExpenseRepository;
import expenseTracker.logic.Console;

import java.util.List;
import java.util.Scanner;

import static expenseTracker.logic.Console.*;


public class Main {
    static FileExpenseRepository fileRepository = new FileExpenseRepository("expenses.json");
    static ExpenseService expenseService = new ExpenseService(fileRepository);
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        boolean isRunnig = true;
        while (isRunnig) {
            System.out.println("\n--- MAIN MENU ---\n");
            System.out.println("1. Add expense");
            System.out.println("2. View expenses");
            System.out.println("3. Update expenses");
            System.out.println("4. Delete expenses");
            System.out.println("5. Generate report");
            System.out.println("0. Exit program");
            System.out.println("Enter your choice: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("Provide expense details");
                    addExpenseMain();
                    break;
                case "2":
                   System.out.println("Provide expense details");
                   List<Expense> list = findListOfExpenses(expenseService);
                   if (list == null) {
                       break;
                   }
                   printExpenseMap(indexExpenses(list));
                    break;
                case "3":
                    System.out.println("Update expense");
                    updateExpenseMain();
                    break;
                case "4":
                    System.out.println("Delete expense");
                    deleteExpenseMain();
                    break;
                case "5":
                    System.out.println("Select type of report");
                    break;
                case "0":
                    System.out.println("Exit");
                    isRunnig = false;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } scanner.close();
    }

    public static void addExpenseMain(){
        Expense expense = Console.createExpense();
        if(expenseService.addExpense(expense)){
            System.out.println("Expense added successfully");
            return;
        } else  {
            System.out.println("Expense could not be added");
            return;
        }
    }

    public static void updateExpenseMain(){

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

    public static void deleteExpenseMain(){
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
