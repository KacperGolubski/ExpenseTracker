package expenseTracker.logic;

import expenseTracker.domain.Expense;
import expenseTracker.domain.ExpenseType;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static expenseTracker.logic.InputReader.*;
import static expenseTracker.logic.InputReader.getExpenseTypeInput;

public class ExpenseHandler {

    private final ExpenseService expenseService;

    public ExpenseHandler(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    public void addExpense(){
        Expense expense = createExpense();
        if(expenseService.addExpense(expense)){
            System.out.println("Expense added successfully");
            return;
        } else  {
            System.out.println("Expense could not be added");
            return;
        }
    }

    public void viewExpense(){
        List<Expense> listToShow = findListOfExpenses();
        if (listToShow == null) {
            return;
        }
        printExpenseMap(indexExpenses(listToShow));
    }

    public void updateExpense(){
        Expense expenseToBeUpdated = findAndSelectExpense();
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

    public void deleteExpense(){
        Expense expenseToBeDeleted = findAndSelectExpense();
        if(expenseToBeDeleted == null){
            return;
        }
        if(expenseService.deleteExpense(expenseToBeDeleted.getId())){
            System.out.println("Expense deleted successfully");
        } else  {
            System.out.println("Expense could not be deleted");
        }
    }

    public Expense createExpense(){
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

    private Expense findAndSelectExpense(){
        Expense expense = null;
        List<Expense> filteredExpenses = findListOfExpenses();
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

    private List<Expense> findListOfExpenses(){
        String name = getStringInput("Enter expense name: ");
        String shopName = getStringInput("Provide shop name: ");
        LocalDate dateFrom = getLocalDateInput("Provide from date: ");
        LocalDate dateTo = getLocalDateInput("Provide to date: ");
        ExpenseType expenseType = getExpenseTypeInput("Provide expense type: ");
        List<Expense> filteredExpenses = this.expenseService.filterByNameShopNameDatesType(name, shopName, dateFrom, dateTo, expenseType);
        if(filteredExpenses.isEmpty()){
            System.out.println("No expense match the criteria");
            return null;
        }
        return filteredExpenses;
    }

    private List<Expense> findListOfExpensesByDates(){
        LocalDate dateFrom = getLocalDateInput("Provide from date: ");
        LocalDate dateTo = getLocalDateInput("Provide to date: ");
        List <Expense> filteredExpenses = this.expenseService.filterExpensesByDates(this.expenseService.getExpenses(), dateFrom, dateTo);
        if(filteredExpenses.isEmpty()){
            System.out.println("No expense match the criteria");
        }
        return filteredExpenses;
    }

    public void generateReport() {
        List<Expense> expensesForReport = findListOfExpensesByDates();
        if (expensesForReport == null || expensesForReport.isEmpty()) {
            return;
        }
        Map<ExpenseType, Double> reportMap = this.expenseService.calculateExpensesByType(expensesForReport);
        String fileName = getStringInput("Provide report name: ");
        ReportGenerator generator = new ReportGenerator(fileName, reportMap);
        generator.generateReport();
        System.out.println("Report successfully saved as " + fileName + ".csv");
    }


}
