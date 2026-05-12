package expenseTracker.logic;
import expenseTracker.domain.Expense;
import expenseTracker.domain.ExpenseType;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReportGenerator {

    private String fileName;
    private Map<ExpenseType, Double> reportData;

    public ReportGenerator(String fileName, Map<ExpenseType, Double> reportData) {
        this.fileName = fileName;
        this.reportData =  reportData;
    }

    public void generateReport() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(this.fileName + ".csv"))){
                writer.println("Category, Sum");
                for (Map.Entry<ExpenseType, Double> entry : this.reportData.entrySet()) {
                    writer.println(entry.getKey()+ "," + entry.getValue());
                }
        } catch (IOException e){
            System.out.println("Error while saving expense report" + e.getMessage());
        }
    }
}
