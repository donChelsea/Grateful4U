package com.katsidzira.grateful4u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticateActivity extends AppCompatActivity {
    EditText emailEt, passwordEt;
    Button loginButton;
    TextView signUpTv;
    FirebaseAuth firebaseAuth;
    String email;
    String password;
    FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);

        firebaseAuth = FirebaseAuth.getInstance();
        emailEt = findViewById(R.id.edittext_user_email);
        passwordEt = findViewById(R.id.edittext_user_pw);
        signUpTv = findViewById(R.id.textview_signup);
        loginButton = findViewById(R.id.button_login);


        signUpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailEt.getText().toString();
                password = passwordEt.getText().toString();
                if (email.trim().isEmpty()) {
                    emailEt.setError("Please enter your email address");
                } else if (password.trim().isEmpty()) {
                    passwordEt.setError("Please enter your password");
                } else if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(AuthenticateActivity.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                } else if (!email.trim().isEmpty() && !password.trim().isEmpty()) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(AuthenticateActivity.this, "Unable to register", Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(AuthenticateActivity.this, MainActivity.class));
                            }
                        }
                    });
                }
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    Toast.makeText(AuthenticateActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AuthenticateActivity.this, MainActivity.class));
                }
            }
        };

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailEt.getText().toString();
                password = passwordEt.getText().toString();
                if (email.trim().isEmpty()) {
                    emailEt.setError("Please enter your email address");
                } else if (password.trim().isEmpty()) {
                    passwordEt.setError("Please enter your password");
                } else if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(AuthenticateActivity.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                } else if (!email.trim().isEmpty() && !password.trim().isEmpty()) {
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(AuthenticateActivity.this, "Unable to log in", Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(AuthenticateActivity.this, MainActivity.class));
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
}
