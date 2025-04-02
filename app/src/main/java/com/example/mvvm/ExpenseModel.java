package com.example.mvvm;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

// Класс модели данных для управления списком расходов
public class ExpenseModel {
    private final MutableLiveData<List<Expense>> expensesLiveData = new MutableLiveData<>(); // LiveData для списка расходов
    private final MutableLiveData<Double> totalLiveData = new MutableLiveData<>(); // LiveData для общего суммарного расхода
    private final List<Expense> expenses = new ArrayList<>(); // Список расходов

    public ExpenseModel() {
        // Инициализация LiveData с пустым списком расходов
        expensesLiveData.setValue(new ArrayList<>());
        totalLiveData.setValue(0.0);
    }

    public void addExpense(Expense expense) {
        // Добавление расхода в список
        expenses.add(expense);
        expensesLiveData.setValue(new ArrayList<>(expenses));
        calculateTotal(); // Обновление общего суммарного расхода
    }

    public void removeExpense(int position) {
        // Удаление расхода по индексу
        if (position >= 0 && position < expenses.size()) {
            expenses.remove(position);
            expensesLiveData.setValue(new ArrayList<>(expenses));
            calculateTotal(); // Обновление общего суммарного расхода
        }
    }

    private void calculateTotal() {
        // Расчет общего суммарного расхода
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        totalLiveData.setValue(total); // Обновление LiveData с общим суммарным расходом
    }
    
    // Предоставление LiveData для ViewModel
    public MutableLiveData<List<Expense>> getExpensesLiveData() {
        // Возвращает LiveData для списка расходов
        return expensesLiveData;
    }

    public MutableLiveData<Double> getTotalLiveData() {
        // Возвращает LiveData для общего суммарного расхода
        return totalLiveData;
    }
}