import expenseTracker.logic.ExpenseService;
import expenseTracker.logic.FileExpenseRepository;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        FileExpenseRepository fileRepository = new FileExpenseRepository("expenses.json");
        ExpenseService expenseService = new ExpenseService(fileRepository);
        Scanner scanner = new Scanner(System.in);
        boolean isRunnig = true;
        while (isRunnig) {
            System.out.println("\n--- MAIN MENU ---\n");
            System.out.println("1. Filter expenses");
            System.out.println("2. Add expenses");
            System.out.println("3. Update expenses");
            System.out.println("4. Delete expenses");
            System.out.println("5. Generate report");
            System.out.println("0. Exit program");
            System.out.println("Enter your choice: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("Provide expense ID");
                case "2":
                    System.out.println("Add new expense");
                    break;
                case "3":
                    System.out.println("Update expense");
                    break;
                case "4":
                    System.out.println("Delete expense");
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
}
