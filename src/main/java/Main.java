import expenseTracker.logic.ExpenseService;
import expenseTracker.logic.FileExpenseRepository;

public class Main {
    public static void main(String[] args) {
        FileExpenseRepository fileRepository = new FileExpenseRepository("expenses.json");
        ExpenseService expenseService = new ExpenseService(fileRepository);
    }
}
