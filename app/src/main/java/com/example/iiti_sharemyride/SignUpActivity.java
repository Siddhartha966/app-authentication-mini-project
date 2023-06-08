package com.example.iiti_sharemyride;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailEditText, usernameEditText, nameEditText, passwordEditText, ageEditText, contactEditText;
    private Button registerButton;
    private FirebaseAuth mAuth;
    private TextView loginRedirectText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Auth
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        // Get references to UI elements
        emailEditText = findViewById(R.id.signup_email);
        usernameEditText = findViewById(R.id.signup_username);
        nameEditText = findViewById(R.id.signup_name);
        passwordEditText = findViewById(R.id.signup_password);
        ageEditText = findViewById(R.id.signup_age);
        contactEditText = findViewById(R.id.signup_contact);
        registerButton = findViewById(R.id.signup_button);
        loginRedirectText = findViewById(R.id.loginRedirectText);

        // Set up click listener for Register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void registerUser() {
        String email = emailEditText.getText().toString().trim();
        String username = usernameEditText.getText().toString().trim();
        String name = nameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String age = ageEditText.getText().toString().trim();
        String contact = contactEditText.getText().toString().trim();

        // Validate input
        if (email.isEmpty()) {
            emailEditText.setError("Email is required");
            emailEditText.requestFocus();
            return;
        }

        if (username.isEmpty()) {
            usernameEditText.setError("Username is required");
            usernameEditText.requestFocus();
            return;
        }

        if (name.isEmpty()) {
            nameEditText.setError("Name is required");
            nameEditText.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            passwordEditText.setError("Password is required");
            passwordEditText.requestFocus();
            return;
        }

        if (age.isEmpty()) {
            ageEditText.setError("Age is required");
            ageEditText.requestFocus();
            return;
        }

        if (contact.isEmpty()) {
            contactEditText.setError("Contact number is required");
            contactEditText.requestFocus();
            return;
        }

        // Create new user using Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            // You can save the additional user information in the Firebase Realtime Database
                            // or Cloud Firestore using the user's UID as the key
                            // For example:
                            // DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
                            // User newUser = new User(name, username, age, contact);
                            // usersRef.child(user.getUid()).setValue(newUser);
                            // Then you can navigate to the next activity
                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpActivity.this, "Registration failed. Please try again later.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
}

