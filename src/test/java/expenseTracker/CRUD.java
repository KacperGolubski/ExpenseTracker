package expenseTracker;
import expenseTracker.domain.ExpenseType;
import expenseTracker.logic.Console;
import expenseTracker.logic.ExpenseService;
import expenseTracker.logic.FileExpenseRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import expenseTracker.domain.Expense;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class CRUD {
    private FileExpenseRepository fileRepository;
    private ExpenseService expenses;
    private Expense testExpense;
    private String simulatedInput;
    private ByteArrayInputStream inputStream;
    private Scanner testScanner;

    @BeforeEach
    public void setUp() {
        fileRepository = new FileExpenseRepository("test_expenses.json");
        expenses = new ExpenseService(fileRepository);
        testExpense = new Expense("Paliwo", "BP", "Cena za litr 5.19zł", 200, LocalDate.parse("2026-04-20"), ExpenseType.FUEL);
        simulatedInput = "Paliwo\n" + "Orlen\n" + "Do pełna\n" + "250.50\n" + "2026-04-10\n" + "FUEL\n";

    }

    @AfterEach
    public void tearDown() {
        try {
            Files.deleteIfExists(Paths.get("test_expenses.json"));
        } catch (IOException e) {
            System.err.println("Could not delete test file: " + e.getMessage());
        }
    }

    @Test
    public void shouldCreateExpense() {
        inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        testScanner = new Scanner(inputStream);
        Console.setScanner(testScanner);
        Expense expense = Console.createExpense();
        assertNotNull(expense);
    }

    @Test void shouldCreateExpenseWithCurrentDate(){
        simulatedInput = "Rata za kredyt\n" +"PEAKO\n" + "\n" + "1700\n" + "\n" + "MORTGAGE\n";
        inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        testScanner = new Scanner(inputStream);
        Console.setScanner(testScanner);
        Expense expense = Console.createExpense();
        assertEquals(LocalDate.now(), expense.getDate());
    }

    @Test void shouldAddExpense(){
        inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        testScanner = new Scanner(inputStream);
        Console.setScanner(testScanner);
        Main.setExpenseService(expenses);
        Main.addExpenseMain();
        assertEquals(1, expenses.getExpenses().size());
    }

    @Test
    void shouldCreateCorrectExpenseFromSimulatedInput() {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner testScanner = new Scanner(inputStream);
        Console.setScanner(testScanner);
        Expense result = Console.createExpense();
        assertAll("Verifying expense",
                () -> assertEquals("Paliwo", result.getName(), "Should have correct name"),
                () -> assertEquals("Orlen", result.getShopName(), "Should have correct shop name"),
                () -> assertEquals(250.50, result.getPrice(),"Should have correct price"),
                () -> assertEquals(LocalDate.parse("2026-04-10"), result.getDate(), "Should have correct date"),
                () -> assertEquals(ExpenseType.FUEL, result.getExpenseType(), "Should have correct expense Type")
        );
    }

    @Test void shouldDeleteExpense(){
        expenses.addExpense(testExpense);
        Main.setExpenseService(expenses);
        String deleteInput = "\n" + "\n" + "\n" + "\n" + "\n" + "1\n";
        inputStream = new ByteArrayInputStream(deleteInput.getBytes());
        testScanner = new Scanner(inputStream);
        Console.setScanner(testScanner);
        Main.deleteExpenseMain();
        assertEquals(0, expenses.getExpenses().size());
    }

    @Test void shouldUpdateExpense(){
        expenses.addExpense(testExpense);
        Main.setExpenseService(expenses);
        String updateInput = "\n" + "\n" + "\n" + "\n" + "\n" + "1\n" + "Rata za kredyt\n" +"PEAKO\n" + "\n" + "1700\n" + "2026-01-01\n" + "MORTGAGE\n" ;
        inputStream = new ByteArrayInputStream(updateInput.getBytes());
        testScanner = new Scanner(inputStream);
        Console.setScanner(testScanner);
        Main.updateExpenseMain();
        Expense updatedExpense = expenses.getExpenses().get(0);
        assertAll("Verifying expense",
                () -> assertEquals("Rata za kredyt", updatedExpense.getName(), "Should have correct name"),
                () -> assertEquals("PEAKO", updatedExpense.getShopName(), "Should have correct shop name"),
                () -> assertEquals(1700, updatedExpense.getPrice(),"Should have correct price"),
                () -> assertEquals(LocalDate.parse("2026-01-01"), updatedExpense.getDate(), "Should have correct date"),
                () -> assertEquals(ExpenseType.MORTGAGE, updatedExpense.getExpenseType(), "Should have correct expense Type")
        );
    }

    @Test
    void shouldReturnFilteredExpenses(){
        expenses.addExpense(testExpense);
        expenses.addExpense(new Expense("Kino", "Multikino", "", 50, LocalDate.now(), ExpenseType.ENTERTAINMENT));
        String viewInput = "Paliwo\n" + "\n" + "\n" + "\n" + "\n";
        inputStream = new ByteArrayInputStream(viewInput.getBytes());
        Console.setScanner(new Scanner(inputStream));
        List<Expense> resultList = Console.findListOfExpenses(expenses);
        System.out.println(resultList);
        assertAll("Verifying expenses list",
                () -> assertNotNull(resultList, "List cannot be null"),
                () -> assertEquals(1, resultList.size(), "Should find only one expense"),
                () -> assertEquals("Paliwo", resultList.get(0).getName(), "Found expense should have correct name")
        );
    }
}