package com.example.mvvm;

import androidx.lifecycle.ViewModel;

// ViewModel для управления списком расходов
public class ExpenseViewModel extends ViewModel {
    private final ExpenseModel expenseModel = new ExpenseModel(); // Используем экземпляр ExpenseModel

    public void addExpense(String name, double amount) {
        // Добавление расхода в модель
        expenseModel.addExpense(new Expense(name, amount));
    }

    public void removeExpense(int position) {
        // Удаление расхода из модели
        expenseModel.removeExpense(position);
    }

    public ExpenseModel getExpenseModel() {
        // Возвращает экземпляр ExpenseModel
        return expenseModel;
    }
}
