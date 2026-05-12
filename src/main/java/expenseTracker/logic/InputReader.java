package expenseTracker.logic;

import expenseTracker.domain.ExpenseType;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputReader {
    static Scanner scanner = new Scanner(System.in);
    public static void setScanner(Scanner testScanner) {
        scanner = testScanner;
    }

    public static String getStringInput(String message){
        System.out.println(message);
        String input = scanner.nextLine();
        if(input.isEmpty()){
            return null;
        }
        return input;
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
        for(ExpenseType expenseType : ExpenseType.values()){
            System.out.println(expenseType);
        }
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
}
