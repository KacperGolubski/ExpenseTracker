import expenseTracker.domain.Expense;
import expenseTracker.domain.ExpenseType;
import expenseTracker.logic.ExpenseService;
import expenseTracker.logic.FileExpenseRepository;
import java.time.LocalDate;
import java.util.Scanner;


public class Main {
    static FileExpenseRepository fileRepository = new FileExpenseRepository("expenses.json");
    static ExpenseService expenseService = new ExpenseService(fileRepository);
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
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
                    System.out.println("Provide expense details");
                    addExpenseMain();
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
    public static void addExpenseMain(){
        Expense expense = null;
        System.out.println("Enter expense name");
        String name = scanner.nextLine();
        System.out.println("Enter expense shop name");
        String shopName = scanner.nextLine();
        System.out.println("Enter expense description");
        String description = scanner.nextLine();
        System.out.println("Enter expense price");
        double price = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Enter expense date in format YYYY-MM-DD");
        LocalDate date = LocalDate.parse(scanner.nextLine());
        System.out.println("Enter expense type");
        ExpenseType type = ExpenseType.valueOf(scanner.nextLine().toUpperCase());
        try{
            expense = new Expense(name, shopName, description, price, date, type);
        } catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
            return;
        }
        if(expenseService.addExpense(expense)){
            System.out.println("Expense added successfully");
        } else  {
            System.out.println("Expense could not be added");
        }
    }
}
