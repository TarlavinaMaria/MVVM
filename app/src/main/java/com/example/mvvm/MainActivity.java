package com.example.mvvm;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ExpenseViewModel viewModel;
    private EditText expenseNameEditText;
    private EditText expenseAmountEditText;
    private LinearLayout expensesContainer;
    private TextView totalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);

        expenseNameEditText = findViewById(R.id.expense_name);
        expenseAmountEditText = findViewById(R.id.expense_amount);
        expensesContainer = findViewById(R.id.expenses_container);
        totalTextView = findViewById(R.id.total_text);

        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> addExpense());

        observeExpenses(); // Обновление списка расходов
    }

    // Добавление расхода
    private void addExpense() {
        // Получение данных о расходе
        String name = expenseNameEditText.getText().toString().trim();
        String amountStr = expenseAmountEditText.getText().toString().trim();
        // Проверка введенных данных
        if (name.isEmpty() || amountStr.isEmpty()) {
            Toast.makeText(this, "Введите название и сумму", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Добавление расхода в модель
            double amount = Double.parseDouble(amountStr);
            viewModel.addExpense(name, amount);

            // Очищаем поля ввода
            expenseNameEditText.setText("");
            expenseAmountEditText.setText("");
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Введите корректную сумму", Toast.LENGTH_SHORT).show();
        }
    }

    private void observeExpenses() {
        // Обновление списка расходов
        viewModel.getExpenseModel().getExpensesLiveData().observe(this, this::updateExpensesList);
        viewModel.getExpenseModel().getTotalLiveData().observe(this, total -> {
            totalTextView.setText(String.format("Общая сумма: %.2f", total));
        });
    }

    // Обновление списка расходов
    private void updateExpensesList(List<Expense> expenses) {
        // Очистка контейнера расходов
        expensesContainer.removeAllViews();

        for (int i = 0; i < expenses.size(); i++) {
            // Получение расхода по индексу
            Expense expense = expenses.get(i);
            // Создание представления расхода
            View expenseView = getLayoutInflater().inflate(R.layout.expense_item, expensesContainer, false);
            // Получение элементов представления по идентификаторам
            TextView nameTextView = expenseView.findViewById(R.id.expense_item_name);
            TextView amountTextView = expenseView.findViewById(R.id.expense_item_amount);
            // Заполнение элементов представления данными расхода
            nameTextView.setText(expense.getName());
            amountTextView.setText(String.format("%.2f", expense.getAmount()));
            // Добавление обработчика нажатия на представление
            final int position = i;
            // Удаление расхода по индексу
            expenseView.setOnClickListener(v -> viewModel.removeExpense(position));
            // Добавление представления в контейнер
            expensesContainer.addView(expenseView);
        }
    }
}