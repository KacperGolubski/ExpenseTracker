package expenseTracker.logic;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import expenseTracker.domain.Expense;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;

public class FileExpenseRepository {
    private ObjectMapper objectMapper;
    private File file;
    public List<Expense> loadExpenses() {
        try{
            List<Expense> expenses= objectMapper.readValue(file, new TypeReference<List<Expense>>(){});
            return expenses;
        } catch(IOException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    public void saveExpenses(List <Expense> expenses) {
        try{
            objectMapper.writeValue(file, expenses);
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    public FileExpenseRepository(String filePath){
        this.file = new File(filePath);
        this.objectMapper = new ObjectMapper();
    }
}
