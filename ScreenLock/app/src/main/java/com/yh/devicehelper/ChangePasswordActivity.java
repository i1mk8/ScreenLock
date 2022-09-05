package com.yh.devicehelper;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ChangePasswordActivity extends AppCompatActivity {
    private int state = 1;

    private String newPassword = "";
    private String tempPassword = "";

    private SharedPreferences.Editor preferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        preferencesEditor = getSharedPreferences("data", MODE_PRIVATE).edit();

        // Кнопки для ввода пароля
        Button passwordBtn1 = findViewById(R.id.passwordBtn1);
        Button passwordBtn2 = findViewById(R.id.passwordBtn2);
        Button passwordBtn3 = findViewById(R.id.passwordBtn3);
        Button passwordBtn4 = findViewById(R.id.passwordBtn4);
        Button passwordBtn5 = findViewById(R.id.passwordBtn5);
        Button passwordBtn6 = findViewById(R.id.passwordBtn6);
        Button passwordBtn7 = findViewById(R.id.passwordBtn7);
        Button passwordBtn8 = findViewById(R.id.passwordBtn8);
        Button passwordBtn9 = findViewById(R.id.passwordBtn9);

        passwordBtn1.setOnClickListener(view -> onClickPasswordButton(1));
        passwordBtn2.setOnClickListener(view -> onClickPasswordButton(2));
        passwordBtn3.setOnClickListener(view -> onClickPasswordButton(3));
        passwordBtn4.setOnClickListener(view -> onClickPasswordButton(4));
        passwordBtn5.setOnClickListener(view -> onClickPasswordButton(5));
        passwordBtn6.setOnClickListener(view -> onClickPasswordButton(6));
        passwordBtn7.setOnClickListener(view -> onClickPasswordButton(7));
        passwordBtn8.setOnClickListener(view -> onClickPasswordButton(8));
        passwordBtn9.setOnClickListener(view -> onClickPasswordButton(9));

        Button cancelBtn = findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(view -> finish());

        Toast.makeText(this, "Введите новый пароль", Toast.LENGTH_SHORT).show();
    }

    private void onClickPasswordButton(int id) {

        if (state == 1) { // Ввод нового пароля
            newPassword += id;
            if (newPassword.length() == 4) {
                state = 2;
                Toast.makeText(this, "Подтвердите новый пароль", Toast.LENGTH_SHORT).show();
            }
        }

        else if (state == 2) { // Подтверждение нового пароля
            tempPassword += id;
            if (tempPassword.length() == 4) {
                if (tempPassword.equals(newPassword)) {
                    preferencesEditor.putString("password", newPassword);
                    preferencesEditor.apply();
                    Toast.makeText(this, "Пароль успешно изменен!", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(this, "Пароли не совпадают!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
