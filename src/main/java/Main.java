import expenseTracker.domain.Expense;
import expenseTracker.domain.ExpenseType;
import expenseTracker.logic.ExpenseService;
import expenseTracker.logic.FileExpenseRepository;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Main {
    static FileExpenseRepository fileRepository = new FileExpenseRepository("expenses.json");
    static ExpenseService expenseService = new ExpenseService(fileRepository);
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        boolean isRunnig = true;
        while (isRunnig) {
            System.out.println("\n--- MAIN MENU ---\n");
            System.out.println("1. Add expenses");
            System.out.println("2. Update expenses");
            System.out.println("3. Delete expenses");
            System.out.println("4. Generate report");
            System.out.println("0. Exit program");
            System.out.println("Enter your choice: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("Provide expense details");
                    addExpenseMain();
                    break;
                case "2":
                    System.out.println("Update expense");
                    updateExpenseMain();
                    break;
                case "3":
                    System.out.println("Delete expense");
                    deleteExpenseMain();
                    break;
                case "4":
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

    public static int getIntInput(String message){
        System.out.println(message);
        return scanner.nextInt();
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
        Expense expense = createExpense();
        if(expenseService.addExpense(expense)){
            System.out.println("Expense added successfully");
            return;
        } else  {
            System.out.println("Expense could not be added");
            return;
        }
    }

    public static void updateExpenseMain(){
        Expense expenseToBeUpdated = findAndReturnExpense();
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
        Expense expenseToBeDeleted = findAndReturnExpense();
        if(expenseToBeDeleted == null){
            return;
        }
        if(expenseService.deleteExpense(expenseToBeDeleted.getId())){
            System.out.println("Expense deleted successfully");
        } else  {
            System.out.println("Expense could not be deleted");
        }
    }


    public static HashMap<Integer, Expense> indexExpenses(List<Expense> expenses){
        HashMap<Integer, Expense> expensesMap = new HashMap<>();
        int index = 1;
        for(Expense expense : expenses){
            expensesMap.put(index, expense);
            index++;
        } return expensesMap;
    }

    public static void printExpenseMap(HashMap<Integer, Expense> expensesMap){
        for (Map.Entry<Integer, Expense> entry : expensesMap.entrySet()) {
            System.out.printf("[%d] %s%n", entry.getKey(), entry.getValue());
        }
    }

    public static Expense createExpense(){
        Expense expense = null;
        while(true){
            String name = getStringInput("Enter expense name: ");
            String shopName =  getStringInput("Enter shop name");
            String description = getStringInput("Enter description (You can leave it empty): ");
            double price = getDoubleInput("Enter price: ");
            LocalDate date = getLocalDateInput("Enter expense date in format YYYY-MM-DD: ");
            ExpenseType type = getExpenseTypeInput("Enter correct expense type from the list: ");
            try{
                expense = new Expense(name, shopName, description, price, date, type);
                break;
            } catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
        return expense;
    }

    public static Expense findAndReturnExpense(){
        Expense expense = null;
        String shopName = getStringInput("Provide shop name: ");
        ExpenseType expenseType = getExpenseTypeInput("Provide expense type: ");
        LocalDate dateFrom = getLocalDateInput("Provide from date: ");
        LocalDate dateTo = getLocalDateInput("Provide to date: ");
        List<Expense> filteredExpenses = expenseService.filterByShopNameDatesType(shopName, dateFrom, dateTo, expenseType);
        if(filteredExpenses.isEmpty()){
            System.out.println("No expense match the criteria");
            return null;
        }
        HashMap<Integer, Expense> indexedExpensesMap = indexExpenses(filteredExpenses);
        printExpenseMap(indexedExpensesMap);
        while(true){
            int input = getIntInput("Select expense ID: ");
            if(indexedExpensesMap.containsKey(input)){
                expense = indexedExpensesMap.get(input);
                break;
            } else {
                System.out.println("Invalid input! Please try again");
            }
        }
        return expense;
    }

}
