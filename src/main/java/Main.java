import expenseTracker.domain.Expense;
import expenseTracker.domain.ExpenseType;
import expenseTracker.logic.ExpenseService;
import expenseTracker.logic.FileExpenseRepository;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
    public static String getStringInput(String message){
        System.out.println(message);
        return scanner.nextLine();
    }

    public static double getDoubleInput(String message){
        System.out.println(message);
        return Double.parseDouble(scanner.nextLine());
    }

    public static LocalDate getLocalDateInput(String message){
        System.out.println(message);
        while(true) {
            try {
                return LocalDate.parse(scanner.nextLine());
            }  catch (DateTimeParseException e) {
                System.out.println("Invalid date format! Please try again");
            }
        }
    }

    public static ExpenseType getExpenseTypeInput(String message){
        System.out.println(message);
        for(ExpenseType type : ExpenseType.values()){
            System.out.print(type + " ");
        }
        while(true) {
            try {
                return ExpenseType.valueOf(scanner.nextLine().toUpperCase());
            } catch(IllegalArgumentException e) {
                System.out.println("Invalid type of expense type! Please try again");
            }
        }
    }

    public static void addExpenseMain(){
        Expense expense = null;
        String name = getStringInput("Enter expense name: ");
        String shopName =  getStringInput("Enter shop name");
        String description = getStringInput("Enter description (You can leave it empty): ");
        double price = getDoubleInput("Enter price: ");
        LocalDate date = getLocalDateInput("Enter expense date in format YYYY-MM-DD: ");
        ExpenseType type = getExpenseTypeInput("Enter correct expense type from the list: ");
        try{
            expense = new Expense(name, shopName, description, price, date, type);
        } catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
            return;
        }
        if(expenseService.addExpense(expense)){
            System.out.println("Expense added successfully");
            return;
        } else  {
            System.out.println("Expense could not be added");
            return;
        }
    }

//    public static void updateExpenseMain(){
//       String shopName = getStringInput("Provide shop name: ");
//        ExpenseType expenseType = getExpenseTypeInput("Provide expense type: ");
//        LocalDate dateFrom = getLocalDateInput("Provide from date: ");
//        LocalDate dateTo = getLocalDateInput("Provide to date: ");
//    }




}
