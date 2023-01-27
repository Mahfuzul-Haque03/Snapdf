package com.example.snapdf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText logInEmailEditText, logInPasswordEditText;
    TextView signUpTextView, skipLogInTextView;
    Button logInButton;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("LogIn");

        mAuth = FirebaseAuth.getInstance();

        logInEmailEditText = findViewById(R.id.logInEmail);
        logInPasswordEditText = findViewById(R.id.logInPassword);
        signUpTextView = findViewById(R.id.signUpTextView);
        skipLogInTextView = findViewById(R.id.skipLogInTextView);

        logInButton = findViewById(R.id.logInButton);
        progressBar = findViewById(R.id.logInProgressBar);

       FirebaseUser user = mAuth.getCurrentUser();

        /*if(user != null){

            finish();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }*/


    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.logInButton:
                userLogin();

                break;
            case R.id.signUpTextView:
                finish();
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
                break;
            case R.id.skipLogInTextView:
                skipLogIn();
                break;
        }

    }
    public void skipLogIn(){
        finish();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void userLogin() {
        String email = logInEmailEditText.getText().toString().trim();
        String password = logInPasswordEditText.getText().toString().trim();

        if(email.isEmpty()){
            logInEmailEditText.setError("Please Enter an email");
            logInEmailEditText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            logInEmailEditText.setError("Please Enter a valid email");
            logInEmailEditText.requestFocus();
            return;
        }
        if(password.isEmpty()){
            logInPasswordEditText.setError("Please Enter a password");
            logInPasswordEditText.requestFocus();
            return;
        }
        if(password.length() < 6){
            logInPasswordEditText.setError("Minimum length of password should be 6");
            logInPasswordEditText.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    skipLogIn();

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(getApplicationContext(), "Login Unsuccessful", Toast.LENGTH_LONG).show();

                }
            }
        });


    }
}
