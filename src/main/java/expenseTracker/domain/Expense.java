package expenseTracker.domain;
import java.time.LocalDate;
import java.util.UUID;


public class Expense {
    private String id;
    private String name;
    private String shopName;
    private LocalDate date;
    private double price;
    private String description = "";
    private ExpenseType expenseType;

    public Expense(String name, String shopName, String description, double price, LocalDate date, ExpenseType expenseType) {
        validateName(name);
        validateName(shopName);
        validateDate(date);
        validatePrice(price);
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.shopName = shopName;
        this.description = description;
        this.price = price;
        this.date = date;
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
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        validatePrice(price);
        this.price = price;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        validateDate(date);
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
    private void validateDate(LocalDate date){
        if(date == null){
            throw new IllegalArgumentException("Data nie może być pusta");
        }
    }



}
