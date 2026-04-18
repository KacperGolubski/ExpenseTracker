package expenseTracker;
import expenseTracker.domain.ExpenseType;
import expenseTracker.logic.ExpenseService;
import expenseTracker.logic.FileExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import expenseTracker.domain.Expense;
import expenseTracker.domain.ExpenseType;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.IllegalFormatException;

public class ExpenseServiceTest {
    private FileExpenseRepository fileRepository;
    private ExpenseService expenses;
    private Expense testExpense;

    @BeforeEach
    public void setUp() {
        fileRepository = new FileExpenseRepository("test_expenses.json");
        expenses = new ExpenseService(fileRepository);
        testExpense = new Expense("Paliwo", "BP", "Cena za litr 5.19zł", 200, LocalDate.parse("2026-04-20"), ExpenseType.FUEL);

    }

    @Test
    public void shouldAddOneExpense() {
        assertTrue(expenses.addExpense(testExpense));
    }

    @Test
    public void shouldAddMultipleExpenses() {
        Expense expense2 = new Expense("Czynsz", "Spółdzielnia", "Opłaty za wodę, śmieci i utrzymanie", 600, LocalDate.parse("2026-04-10"), ExpenseType.HOME);
        expenses.addExpense(testExpense);
        expenses.addExpense(expense2);
        assertEquals(2, expenses.getExpenses().size());
    }

    @Test
    public void shouldReturnCorrectExpenseById(){
        expenses.addExpense(testExpense);
        assertEquals(testExpense, expenses.getExpenseById(testExpense.getId()));
    }

    @Test
        public void shouldUpdateExpenseById(){
        Expense update = new Expense("Zakupy", "Netto", "Zakupy do diety Respo", 123,  LocalDate.parse("2026-04-18"), ExpenseType.GROCERIES);
        expenses.addExpense(testExpense);
        assertTrue(expenses.updateExpense(testExpense.getId(),  update));
    }

    @Test
    public void shouldUpdateAllFieldsInExpenseById(){
        Expense update = new Expense("Zakupy", "Netto", "Zakupy do diety Respo", 123,  LocalDate.parse("2026-04-18"), ExpenseType.GROCERIES);
        expenses.addExpense(testExpense);
        expenses.updateExpense(testExpense.getId(), update);
        Expense updatedExpense = expenses.getExpenseById(testExpense.getId());
        update.setId(testExpense.getId());
        assertEquals(update, updatedExpense);
    }

    @Test
    public void shouldDeleteExpenseById(){
        expenses.addExpense(testExpense);
        assertTrue(expenses.deleteExpense(testExpense.getId()));
    }

    @Test
    public void shouldDeleteCorrectExpense(){
        Expense expense2 = new Expense("Czynsz", "Spółdzielnia", "Opłaty za wodę, śmieci i utrzymanie", 600, LocalDate.parse("2026-04-10"), ExpenseType.HOME);
        expenses.addExpense(testExpense);
        expenses.addExpense(expense2);
        expenses.deleteExpense(testExpense.getId());
        assertEquals(expense2, expenses.getExpenseById(expense2.getId()));

    }
}
