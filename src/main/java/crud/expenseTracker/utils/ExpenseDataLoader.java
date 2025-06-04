package crud.expenseTracker.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import crud.expenseTracker.model.Expense;
import jakarta.annotation.PostConstruct;

@Component
public class ExpenseDataLoader {

  private static List<Expense> expenses = new ArrayList<>();

  @PostConstruct
  public void init(){
    ObjectMapper mapper = new ObjectMapper();
    InputStream is = getClass().getResourceAsStream("/expenses.json");

    try {
      expenses = mapper.readValue(is, new TypeReference<List<Expense>>() {});
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static List<Expense> getExpenses(){
    return expenses; 
  }

}
