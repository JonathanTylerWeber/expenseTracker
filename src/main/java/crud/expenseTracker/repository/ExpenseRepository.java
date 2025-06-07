package crud.expenseTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import crud.expenseTracker.model.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> { // first value is type of object, 2nd is type of key (id)

} 
