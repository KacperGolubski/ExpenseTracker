package expenseTracker.logic;

import expenseTracker.domain.Expense;
import expenseTracker.domain.ExpenseType;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Console {

    static Scanner scanner = new Scanner(System.in);
    public static String getStringInput(String message){
        System.out.println(message);
        return scanner.nextLine();
    }

    public static int getIntInput(String message){
        System.out.println(message);
        return Integer.parseInt(scanner.nextLine());
    }

    public static double getDoubleInput(String message){
        System.out.println(message);
        return Double.parseDouble(scanner.nextLine());
    }

    public static LocalDate getLocalDateInput(String message){
        System.out.println(message);
        while(true) {
            String input = scanner.nextLine();
            if(input.isEmpty()){
                return null;
            }
            try {
                return LocalDate.parse(input);
            }  catch (DateTimeParseException e) {
                System.out.println("Invalid date format! Please try again");
            }
        }
    }

    public static ExpenseType getExpenseTypeInput(String message){
        System.out.println(message);
        while(true) {
            String input = scanner.nextLine();
            if(input.isEmpty()){
                return null;
            }
            try {
                return ExpenseType.valueOf(input.toUpperCase());
            } catch(IllegalArgumentException e) {
                System.out.println("Invalid type of expense type! Please try again");
            }
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

    public static Expense findAndSelectExpense(ExpenseService expenseService){
        Expense expense = null;
        List<Expense> filteredExpenses = findListOfExpenses(expenseService);
        if(filteredExpenses == null){
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

    public static List<Expense> findListOfExpenses(ExpenseService expenseService){
        String name = getStringInput("Enter expense name: ");
        String shopName = getStringInput("Provide shop name: ");
        ExpenseType expenseType = getExpenseTypeInput("Provide expense type: ");
        LocalDate dateFrom = getLocalDateInput("Provide from date: ");
        LocalDate dateTo = getLocalDateInput("Provide to date: ");
        List<Expense> filteredExpenses = expenseService.filterByShopNameDatesType(name, shopName, dateFrom, dateTo, expenseType);
        if(filteredExpenses.isEmpty()){
            System.out.println("No expense match the criteria");
            return null;
        }
        return filteredExpenses;
    }

}
