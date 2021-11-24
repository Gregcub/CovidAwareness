package com.gac5206.covidawareness.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gac5206.covidawareness.R;
import com.gac5206.covidawareness.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{

    EditText email, username, password, country, state, city;
    TextView fieldsError;
    ProgressBar progressBar;
    Button register;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        email = findViewById(R.id.email);
        username = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        country = findViewById(R.id.country);
        state = findViewById(R.id.state);
        city = findViewById(R.id.city);
        register = findViewById(R.id.register);
        progressBar = findViewById(R.id.progressbar);
        fieldsError = findViewById(R.id.required_fields_error);

        mAuth = FirebaseAuth.getInstance();


        register.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.register) {
            registerUser();
        }

    }

    private void registerUser() {
        String userEmail = email.getText().toString().trim();
        String userName = username.getText().toString().trim();
        String userPassword = password.getText().toString().trim();
        String userCountry = country.getText().toString().trim();
        String userState = state.getText().toString().trim();
        String userCity = city.getText().toString().trim();


        //Validate Email
        if(!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
            email.setError("Please provide valid email!");
            email.requestFocus();
            return;
        }

        //Validate Password
        if(userPassword.length() < 8){
            password.setError("Password must be at least 8 characters!");
            password.requestFocus();
            return;
        }

        //Validate all fields
        if(validate()){
            fieldsError.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), username.getText().toString()+" Registered!", Toast.LENGTH_SHORT).show();

        }else {
            fieldsError.setVisibility(View.VISIBLE);
            Toast.makeText(RegistrationActivity.this, "All fields required!", Toast.LENGTH_SHORT).show();
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            User user = new User(userName, userEmail, userCountry, userCity, userState);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>(){

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegistrationActivity.this, userName+" has been registered successfully!", Toast.LENGTH_LONG).show();

                                    }else{
                                        Toast.makeText(RegistrationActivity.this, "Failed to register!", Toast.LENGTH_LONG).show();
                                    }
                                    progressBar.setVisibility(View.INVISIBLE);

                                }
                            });

                        }else{
                            Toast.makeText(RegistrationActivity.this, "Failed to register!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }

                });




    }




    private Boolean validate() {

        return !email.getText().toString().isEmpty() || !username.getText().toString().isEmpty() || !password.getText().toString().isEmpty() ||
                !country.getText().toString().isEmpty() || !state.getText().toString().isEmpty() || !city.getText().toString().isEmpty();
    }
}