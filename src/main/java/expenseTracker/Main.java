package expenseTracker;

import expenseTracker.domain.Expense;
import expenseTracker.logic.ExpenseHandler;
import expenseTracker.logic.ExpenseService;
import expenseTracker.logic.FileExpenseRepository;
import java.util.List;
import java.util.Scanner;
import static expenseTracker.logic.ExpenseHandler.*;


public class Main {
    static FileExpenseRepository fileRepository = new FileExpenseRepository("expenses.json");
    static ExpenseService expenseService = new ExpenseService(fileRepository);
    static ExpenseHandler expenseHandler = new ExpenseHandler(expenseService);
    static Scanner scanner = new Scanner(System.in);
    public static void setScanner(Scanner testScanner) {
        scanner = testScanner;
    }

    public static void setExpenseService(ExpenseService testService) {
        expenseService = testService;
    }

    public static void main(String[] args) {
        boolean isRunnig = true;
        while (isRunnig) {
            System.out.println("\n--- MAIN MENU ---\n");
            System.out.println("1. Add expense");
            System.out.println("2. View expenses");
            System.out.println("3. Update expenses");
            System.out.println("4. Delete expenses");
            System.out.println("5. Generate report to .csv");
            System.out.println("0. Exit program");
            System.out.println("Enter your choice: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("Provide expense details");
                    expenseHandler.addExpense();
                    break;
                case "2":
                   System.out.println("Provide expense details");
                   expenseHandler.viewExpense();
                    break;
                case "3":
                    System.out.println("Update expense");
                    expenseHandler.updateExpense();
                    break;
                case "4":
                    System.out.println("Delete expense");
                    expenseHandler.deleteExpense();
                    break;
                case "5":

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
}
