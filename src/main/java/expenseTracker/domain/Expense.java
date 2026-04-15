package expenseTracker.domain;
import java.time.LocalDate;


public abstract class Expense {
    private String name;
    private String description;
    private double price;
    private int amount;
    private LocalDate date;
    private ExpenseType expenseType;

    public Expense(String name, String description, double price, int amount, LocalDate date, ExpenseType expenseType) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.amount = amount;
        this.date = date;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public double getPrice() {
        return price;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public ExpenseType getExpenseType() {
        return expenseType;
    }
    public void setExpenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
    }

    private void
    private void validateExpenseType(ExpenseType expenseType) {
        if (expenseType == null) {
            throw new IllegalArgumentException("Typ wydatku nie może być pusty");
        }
    }


}
