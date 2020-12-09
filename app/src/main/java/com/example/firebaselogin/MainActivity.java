package com.example.firebaselogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonRegister;

    private EditText textEmail, textPassword;
    private Button buttonLogin;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(this);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonRegister:
                startActivity(new Intent(this, register.class));
                break;
            case R.id.buttonLogin:
                login();
        }

    }

    private void login() {

        String email = textEmail.getText().toString().trim();
        String password = textPassword.getText().toString().trim();

        if (email.isEmpty()){
            textEmail.setError("Email address is required!");
            textEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            textEmail.setError("Please provide a valid email address!");
            textEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            textPassword.setError("Password is required!");
            textPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //Redirect to user profile
                }
                else{
                    Toast.makeText(MainActivity.this, "Incorrect credentials!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}