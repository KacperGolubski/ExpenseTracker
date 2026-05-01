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


public class ExpenseTest {

    private Expense TestExpense;

    @BeforeEach
    public void setup() {
        TestExpense = new Expense("Paliwo", "BP", "Cena za litr 5.19zł", 200, LocalDate.parse("2026-04-20"), ExpenseType.FUEL);
    }

    @Test
    public void shouldCreateNotEmptyExpense() {
        Expense expense = new Expense("Rata kredytu", "Pekao", "", 1500, LocalDate.parse("2026-04-17"), ExpenseType.MORTGAGE);
        assertNotNull(expense);
    }

    @Test
    public void shouldCreateEmptyExpense() {
        Expense expense = new  Expense();
        assertNull(expense.getName());
    }

    @Test
    public void shouldThrowExceptionWhenNameIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Expense expense = new Expense(null, "Pekao", "", 1500, LocalDate.parse("2026-04-17"), ExpenseType.MORTGAGE);
        });
        assertEquals("Nazwa nie może być pusta", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenShopNameIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Expense expense = new Expense("Rata", "", "", 1500, LocalDate.parse("2026-04-17"), ExpenseType.MORTGAGE);
        });
        assertEquals("Nazwa nie może być pusta",  exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenPriceIsEqualZero() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Expense expense = new Expense("Rata kredytu", "Pekao", "", 0, LocalDate.parse("2026-04-17"), ExpenseType.MORTGAGE);
        });
        assertEquals("Kwota musi być większa od 0", exception.getMessage());
    }

    @Test
    public void shouldSetCorrectName(){
        TestExpense.setName("Benzyna");
        assertEquals("Benzyna", TestExpense.getName());
    }

    @Test
    public void shouldSetCorrectShopName(){
        TestExpense.setShopName("Orlen");
        assertEquals("Orlen", TestExpense.getShopName());
    }

    @Test
    public void shouldSetCorrectPrice(){
        TestExpense.setPrice(150);
        assertEquals(150.0, TestExpense.getPrice());
    }

    @Test
    public void shouldSetCorrectDate(){
        TestExpense.setDate(LocalDate.parse("2026-04-12"));
        assertEquals(LocalDate.parse("2026-04-12"), TestExpense.getDate());
    }

    @Test
    public void shouldSetCorrectDescription(){
        TestExpense.setDescription("Cena za litr 6.19zł");
        assertEquals("Cena za litr 6.19zł", TestExpense.getDescription());
    }

    @Test
    public void shouldSetCorrectExpenseType() {
        TestExpense.setExpenseType(ExpenseType.CAR);
        assertEquals(ExpenseType.CAR, TestExpense.getExpenseType());
    }

}
