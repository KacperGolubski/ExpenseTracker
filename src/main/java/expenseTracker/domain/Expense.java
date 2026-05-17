package expenseTracker.domain;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;


public class Expense {
    private String id;
    private String name;
    private String shopName;
    private LocalDate date;
    private double cost;
    private String description = "";
    private ExpenseType expenseType;

    public Expense(String name, String shopName, String description, double cost, LocalDate date, ExpenseType expenseType) {
        validateName(name);
        validateName(shopName);
        validatePrice(cost);
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.shopName = shopName;
        this.description = description;
        this.cost = cost;
        if(date == null){
            this.date = LocalDate.now();
        } else this.date = date;
        this.expenseType = expenseType;
    }

    public Expense(){}; // empty constructor for Jackson

    public String getName() {
        return name;
    }
    public void setName(String name) {
        validateName(name);
        this.name = name;
    }
    public String getShopName() {
        return shopName;
    }
    public void setShopName(String shopName) {
        validateName(shopName);
        this.shopName = shopName;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public double getCost() {
        return cost;
    }
    public void setCost(double cost) {
        validatePrice(cost);
        this.cost = cost;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public ExpenseType getExpenseType() {
        return expenseType;
    }
    public void setExpenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    private void validateName(String name){
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Nazwa nie może być pusta");
        }
    }
    private void validatePrice(double price){
        if(price <=0){
            throw new IllegalArgumentException("Kwota musi być większa od 0");
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Expense expense = (Expense) o;

        return Double.compare(expense.cost, cost) == 0 &&
                Objects.equals(id, expense.id) &&
                Objects.equals(name, expense.name) &&
                Objects.equals(shopName, expense.shopName) &&
                Objects.equals(description, expense.description) &&
                Objects.equals(date, expense.date) &&
                expenseType == expense.expenseType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, shopName, description, cost, date, expenseType);
    }
    @Override
    public String toString() {
        String displayDescription = description;
        if(displayDescription == null){
            displayDescription = "no description";
        }
        return String.format("%s | %-15s | %-15s | %-30s | %8.2f | [%s]",
                date,
                name,
                shopName,
                displayDescription,
                cost,
                expenseType);
    }


}
