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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUpActivity extends AppCompatActivity {
    EditText signUpEmailEditText, signUpPasswordEditText, signUpaddressEditText;
    TextView signInTextView;
    Button signUpButton;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Sign Up Activity");
        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_sign_up);
        signUpEmailEditText = findViewById(R.id.signUpEmail);
        signUpPasswordEditText = findViewById(R.id.signUpPassword);
        signUpaddressEditText = findViewById(R.id.signUpPassword);
        signInTextView = findViewById(R.id.signInTextView);
        signUpButton = findViewById(R.id.signUpButton);

        progressBar = findViewById(R.id.signUpProgressBar);


    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signUpButton:

                userRegister();
                break;

            case R.id.signInTextView:
                finish();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                break;

        }

    }

    private void userRegister() {
        String email = signUpEmailEditText.getText().toString().trim();
        String password = signUpPasswordEditText.getText().toString().trim();
        String address = signUpPasswordEditText.getText().toString();

        if(email.isEmpty()){
            signUpEmailEditText.setError("Please Enter an email");
            signUpEmailEditText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            signUpEmailEditText.setError("Please Enter a valid email");
            signUpEmailEditText.requestFocus();
            return;
        }
        if(password.isEmpty()){
            signUpPasswordEditText.setError("Please Enter a password");
            signUpPasswordEditText.requestFocus();
            return;
        }
        if(password.length() < 6){
            signUpPasswordEditText.setError("Minimum length of password should be 6");
            signUpPasswordEditText.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);

                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    finish();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                } else {
                    // If sign in fails, display a message to the user.
                    if(task.getException() instanceof FirebaseAuthUserCollisionException) {
                        signUpEmailEditText.requestFocus();
                        Toast.makeText(getApplicationContext(), "This email is already registered", Toast.LENGTH_LONG).show();
                    }else
                        Toast.makeText(getApplicationContext(), "Error" + task.getException().getMessage(), Toast.LENGTH_LONG).show();



                }

            }
        });



    }
}
