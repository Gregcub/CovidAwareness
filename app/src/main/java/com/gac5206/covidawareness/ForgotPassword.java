package com.gac5206.covidawareness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText userEmail;
    private Button resetPass;
    private ProgressBar progressBar;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        userEmail = findViewById(R.id.userEmail);
        resetPass = findViewById(R.id.reset_password);
        progressBar = findViewById(R.id.progressbar);

        mAuth = FirebaseAuth.getInstance();

        resetPass.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
                resetPassword();

            }
        });

    }

    private void resetPassword() {
        String email = userEmail.getText().toString().trim();

        if(email.isEmpty()){
            userEmail.setError("Email Required!");
            userEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            userEmail.setError("Please provide a valid email!");
            userEmail.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {

            if(task.isSuccessful()){
                Toast.makeText(ForgotPassword.this, "Password reset sent to email!", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(ForgotPassword.this, "Error, Try again!", Toast.LENGTH_LONG).show();
            }


            progressBar.setVisibility(View.INVISIBLE);


        });
    }
}