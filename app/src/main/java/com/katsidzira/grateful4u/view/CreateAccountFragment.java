package com.katsidzira.grateful4u.view;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.katsidzira.grateful4u.R;

public class CreateAccountFragment extends Fragment {
    EditText createEmailEdit, createPasswordEdit;
    Button loginButton;
    String email;
    String password;
    FirebaseAuth firebaseAuth;

    public CreateAccountFragment() {
    }

    public static CreateAccountFragment newInstance() {
        CreateAccountFragment fragment = new CreateAccountFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();

        createEmailEdit = view.findViewById(R.id.create_email_edit);
        createPasswordEdit = view.findViewById(R.id.create_password_edit);
        loginButton = view.findViewById(R.id.finish_create_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = createEmailEdit.getText().toString();
                password = createPasswordEdit.getText().toString();
                if (email.trim().isEmpty()) {
                    createEmailEdit.setError("Please enter your email address");
                } else if (password.trim().isEmpty()) {
                    createPasswordEdit.setError("Please enter your password");
                } else if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(v.getContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
                } else if (!email.trim().isEmpty() && !password.trim().isEmpty()) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(getContext(), "Unable to register", Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(getContext(), MainActivity.class));
                            }
                        }
                    });
                }
            }
        });

    }
}
