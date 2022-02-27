package com.example.onlinemedicineservice.CustomerAuthentication;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.onlinemedicineservice.Model.FirebaseUserModel;
import com.example.onlinemedicineservice.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    private Button signUpButton;
    private TextInputEditText firstNameText;
    private TextInputEditText lastNameText;
    private TextInputEditText emailText;
    private TextInputEditText phoneNumberText;
    private TextInputEditText passwordText;
    private TextInputEditText rePasswordText;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        signUpButton = findViewById(R.id.signupbutton);
        firstNameText = findViewById(R.id.name1Edittext);
        lastNameText = findViewById(R.id.name2Edittext);
        emailText = findViewById(R.id.emailEdittext);
        phoneNumberText = findViewById(R.id.phonenumberEdittext);
        passwordText = findViewById(R.id.passwordEdittext);
        rePasswordText = findViewById(R.id.repasswordEdittext);
        auth = FirebaseAuth.getInstance();

        signUpButton.setOnClickListener(view -> createAccount());

    }

    private void createAccount() {

        if(validateInputFields()) {
            auth.createUserWithEmailAndPassword(
                    Objects.requireNonNull(emailText.getText()).toString(),
                    Objects.requireNonNull(passwordText.getText()).toString())
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {

                            sendVerificationEmail();
                            if(assignUserInOnlineDatabase(Objects.requireNonNull(auth.getCurrentUser()).getUid())) {

                                Toast.makeText(getApplicationContext(),
                                        "Account Created! Please Check your email for verification",
                                        Toast.LENGTH_LONG).show();

                                navigateToSignInPage();
                            }

                        } else {
                            Toast.makeText(SignUpActivity.this, Objects.requireNonNull(task.getException()).getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void sendVerificationEmail() {

        if(auth.getCurrentUser() != null) {

            auth.getCurrentUser().sendEmailVerification()
                    .addOnCompleteListener(task -> {
                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getMessage(),
                                    Toast.LENGTH_SHORT).show();

                        }
                    });
        }

    }

    private void navigateToSignInPage() {
        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        SignUpActivity.this.finish();
    }

    private boolean assignUserInOnlineDatabase(String Uid) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference userReference = database.getReference("Users");

        FirebaseUserModel user = new FirebaseUserModel(
                emailText.getText().toString(),
                firstNameText.getText().toString(),
                lastNameText.getText().toString(),
                phoneNumberText.getText().toString()
        );

        if(!Uid.isEmpty()){
            userReference.child(Uid).setValue(user);
            return true;
        }else{
            return false;
        }

    }

    private boolean validateInputFields(){

        if(emailText.getText().toString().isEmpty()){
            emailText.setError("This Field can't be empty");
            return false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emailText.getText().toString()).matches()){
            emailText.setError("Give a valid email Address");
            return false;
        }
        if(phoneNumberText.getText().toString().isEmpty()){
            phoneNumberText.setError("This Field can't be empty");
            return false;
        }
        if(phoneNumberText.getText().toString().length() < 11){
            phoneNumberText.setError("Invalid Phone number");
            return false;
        }
        if(passwordText.getText().toString().isEmpty()){
            passwordText.setError("This Field can't be empty");
            return false;
        }
        if(passwordText.getText().toString().length() < 8){
            passwordText.setError("Password should be at least 8 charecters");
            return false;
        }
        if(rePasswordText.getText().toString().isEmpty()){
            rePasswordText.setError("This Field can't be empty");
            return false;
        }
        if(!passwordText.getText().toString().equals(rePasswordText.getText().toString())){
            rePasswordText.setError("This field should match with Password field");
            return false;
        }

        return true;
    }


}

