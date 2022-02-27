package com.example.onlinemedicineservice;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class PasswordReset {

    boolean emailSent = false;
    public boolean resetPassword(Context context, String email){
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(context, "A link has sent to your email. Please check your email", Toast.LENGTH_SHORT).show();
                emailSent = true;
            }else{
                Toast.makeText(context, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return emailSent;
    }



}

