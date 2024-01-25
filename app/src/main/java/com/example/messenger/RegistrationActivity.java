package com.example.messenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextName;
    private EditText editTextSurname;
    private EditText editTextAge;
    private Button buttonSignUp;

    private RegistrationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initViews();
        viewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);
        observeViewModel();

        setupClickListeners();
    }

    private void setupClickListeners() {
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = getTrimmedValue(editTextEmail);
                String password = getTrimmedValue(editTextPassword);
                String name = getTrimmedValue(editTextName);
                String surname = getTrimmedValue(editTextSurname);
                int age = parseIntAge();

                validateEmailEditText(email);
                validatePasswordEditText(password);
                validateNameEditText(name);
                validateSurnameEditText(surname);
                validateAgeEditText(age);

                signUpUser(email, password, name, surname, age);
            }
        });
    }

    private int parseIntAge() {
        int age = 0;
        try {
            age = Integer.parseInt(getTrimmedValue(editTextAge));
        } catch (Exception e) {
            e.printStackTrace();
            editTextAge.setError("Error editTextAge");
            Log.d("RegistrationActivity", "Catch body");
            editTextAge.setBackgroundResource(R.drawable.error_background);
        }
        return age;
    }

    private void signUpUser(
            String email,
            String password,
            String name,
            String surname,
            int age
    ) {
        if (!email.isEmpty() &&
                !password.isEmpty() &&
                !name.isEmpty() &&
                !surname.isEmpty() &&
                age > 0
        ) {
            viewModel.signUp(email, password, name, surname, age);
        }
    }

    private void validateAgeEditText(int age) {
        if (age <= 0) {
            editTextAge.setError(getString(R.string.are_should_be_positive));
            Log.d("RegistrationActivity", "ValidateAge Body");
            editTextAge.setBackgroundResource(R.drawable.error_background);
        } else {
            editTextAge.setBackgroundResource(R.drawable.normal_background);
        }
    }

    private void validateSurnameEditText(String surname) {
        if (surname.isEmpty()) {
            editTextSurname.setError("Fill the field!");
            editTextSurname.setBackgroundResource(R.drawable.error_background);
        } else {
            editTextSurname.setBackgroundResource(R.drawable.normal_background);
        }
    }

    private void validateNameEditText(String name) {
        if (name.isEmpty()) {
            editTextName.setError("Fill the field!");
            editTextName.setBackgroundResource(R.drawable.error_background);
        } else {
            editTextName.setBackgroundResource(R.drawable.normal_background);
        }
    }

    private void validatePasswordEditText(String password) {
        if (password.isEmpty()) {
            editTextPassword.setError("Fill the field!");
            editTextPassword.setBackgroundResource(R.drawable.error_background);
        } else {
            editTextPassword.setBackgroundResource(R.drawable.normal_background);
        }
    }

    private void validateEmailEditText(String email) {
        if (email.isEmpty()) {
            editTextEmail.setError("Fill the field!");
            editTextEmail.setBackgroundResource(R.drawable.error_background);
        } else {
            editTextEmail.setBackgroundResource(R.drawable.normal_background);
        }
    }

    private void observeViewModel() {
        viewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    Intent intent = UsersActivity.newIntent(
                            RegistrationActivity.this,
                            firebaseUser.getUid()
                    );
                    startActivity(intent);
                    finish();
                }
            }
        });

        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                if (errorMessage != null) {
                    Toast.makeText(
                            RegistrationActivity.this,
                            errorMessage,
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, RegistrationActivity.class);
    }

    private String getTrimmedValue(EditText editText) {
        return editText.getText().toString().trim();
    }

    private void initViews() {
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextName = findViewById(R.id.editTextName);
        editTextSurname = findViewById(R.id.editTextSurname);
        editTextAge = findViewById(R.id.editTextAge);
        buttonSignUp = findViewById(R.id.buttonSignUp);
    }
}