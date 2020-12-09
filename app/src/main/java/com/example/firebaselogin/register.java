package com.example.firebaselogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    private EditText textRegName, textRegEmailAddress, textRegPassword, textRegComment;
    private Button buttonRegRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        textRegEmailAddress = (EditText) findViewById(R.id.textRegEmailAddress);
        textRegName = (EditText) findViewById(R.id.textRegName);
        textRegPassword = (EditText) findViewById(R.id.textRegPassword);
        textRegComment = (EditText) findViewById(R.id.textRegComment);

        buttonRegRegister = (Button) findViewById(R.id.buttonRegRegister);
        buttonRegRegister.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.buttonRegRegister:
                registerUser();
        }
    }

    private void registerUser() {
        final String email = textRegEmailAddress.getText().toString().trim();
        String password = textRegPassword.getText().toString().trim();
        final String name = textRegName.getText().toString().trim();
        final String comment = textRegComment.getText().toString().trim();

        if (name.isEmpty()){
            textRegName.setError("Full name is required!");
            textRegName.requestFocus();
            return;
        }

        if (email.isEmpty()){
            textRegEmailAddress.setError("Email address is required!");
            textRegEmailAddress.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            textRegEmailAddress.setError("Please provide a valid email address!");
            textRegEmailAddress.requestFocus();
            return;
        }

        if (password.isEmpty()){
            textRegPassword.setError("Password is required!");
            textRegPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            textRegPassword.setError("Password must be at least six characters!");
            textRegPassword.requestFocus();
            return;
        }

        if (comment.isEmpty()){
            textRegComment.setError("Comment is required!");
            textRegComment.requestFocus();
            return;
        }

        //progressbar set visible

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        user User = new user(name, email, comment);

                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(User).addOnCompleteListener(new OnCompleteListener<Void>(){

                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(register.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                                //Progress bar set invisible
                            }
                        });
                    }
                    else{
                        Toast.makeText(register.this, "Failed to register user!", Toast.LENGTH_LONG).show();
                        //Progress bar set invisible
                    }
                }
        });

        Toast.makeText(this, "Details have been entered correctly!", Toast.LENGTH_SHORT).show();
        return;
    }

}