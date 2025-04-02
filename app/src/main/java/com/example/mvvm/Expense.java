package com.example.mvvm;

// Класс, хранящий информацию о расходе
public class Expense {
    String name;
    double amount;

    public Expense(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }
}
