package com.example.onlinemedicineservice.CustomerAuthentication;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.onlinemedicineservice.HomeActivity;
import com.example.onlinemedicineservice.PasswordReset;
import com.example.onlinemedicineservice.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Objects;

public class SignInActivity extends AppCompatActivity {

    private EditText passwordText;
    private EditText emailText;
    private Button loginButton;
    private Button resetPasswordButton;
    private Button createAccountButton;
    private PasswordReset passwordReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        passwordReset = new PasswordReset();

        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);
        loginButton = findViewById(R.id.loginButton);
        resetPasswordButton = findViewById(R.id.resetPasswordButtonID);
        createAccountButton = findViewById(R.id.createAccountButtonID);

        if(userIsActive()){
            navigateToHomePage();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loginButton.setOnClickListener(view -> validateUser());
        createAccountButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });
        resetPasswordButton.setOnClickListener(view -> {
            if(emailText.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), "Inappropriate Email Address!",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            passwordReset.resetPassword(this, emailText.getText().toString());
        });
    }

    private boolean userIsActive() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser activeUser = auth.getCurrentUser();
        if(activeUser != null) {
            return activeUser.isEmailVerified();
        }
        return false;
    }

    private void validateUser() {

        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (!validateInputFields()) {
            return;
        }
        auth.signInWithEmailAndPassword(
                emailText.getText().toString(),
                passwordText.getText().toString())
                .addOnCompleteListener(this, task -> {

                    if (task.isSuccessful()) {
                        if (Objects.requireNonNull(auth.getCurrentUser()).isEmailVerified()) {
                            navigateToHomePage();
                        } else {
                            Toast.makeText(getApplicationContext(), "Verify Your Account",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });

    }

    private boolean validateInputFields() {
        if(emailText.getText().toString().isEmpty() || passwordText.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Input Missing",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void navigateToHomePage() {
        Intent intent  = new Intent(SignInActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
