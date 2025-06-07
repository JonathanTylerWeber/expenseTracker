package crud.expenseTracker.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import crud.expenseTracker.model.Expense;
import crud.expenseTracker.utils.ExpenseDataLoader;

@Service
@Profile("json")
public class ExpenseServiceImpl implements ExpenseService {

  private static final AtomicLong idCounter = new AtomicLong();
  // atomic is thread safe meaning that if two people tried doing something at the same time there's a possibility of the id being the same but
  // atomic prevents this

  @Override
  public List<Expense> getExpenseByDay(String date) {
    return ExpenseDataLoader
      .getExpenses()
      .stream()
      .filter(expense -> expense
        .getDate()
        .equalsIgnoreCase(date))
      .toList();
  }

  @Override
  public List<Expense> getExpenseByCategoryAndMonth(String category, String month) {
    return ExpenseDataLoader
      .getExpenses()
      .stream()
      .filter(expense -> expense
        .getCategory()
        .equalsIgnoreCase(category) 
        && 
        expense.getDate()
          .startsWith(month)
      ) 
      .toList();
  }

  @Override
  public List<String> getAllExpenseCategories() {
    return ExpenseDataLoader
      .getExpenses() 
			// gets all expenses
			.stream() 
			// converts the list of expenses to a stream
			// a stream is an abstraction that allows you to perform a sequence of operations (filtering, mapping, reducing, etc.) on elements of a collection
			.map( 
			//map is used to transform elements in the stream. here it takes each expense object and transforms it by applying the getCategory() method   to each one
				Expense::getCategory)
			// Expense::getCategory is a method reference which is shorthand for (expense) -> expense.getCategory()
			.distinct()
			// the distinct operation removes duplicate elements from the stream
			.toList(); // turns the result into a list
  }

  @Override
  public Optional<Expense> getExpenseById(Long id) {
    return ExpenseDataLoader.getExpenses().stream().filter(
      expense -> expense.getId().equals(id)
    ).findFirst();
  }

  @Override
  public Expense addExpense(Expense expense) {
    expense.setId(idCounter.incrementAndGet());
    ExpenseDataLoader.getExpenses().add(expense);
    return expense;
  }

  @Override
  public boolean updateExpense(Expense updatedExpense) {
    Optional<Expense> existingExpense = getExpenseById(updatedExpense.getId());
    if (existingExpense.isPresent()) {
      ExpenseDataLoader.getExpenses().remove(existingExpense.get());
      ExpenseDataLoader.getExpenses().add(updatedExpense);
      return true;
    }
    return false;
  }

  @Override
  public boolean deleteExpense(Long id) {
    Optional<Expense> existingExpense = getExpenseById(id);
    if (existingExpense.isPresent()) {
      ExpenseDataLoader.getExpenses().remove(existingExpense.get());
      return true;
    }
    return false;
  }

}
