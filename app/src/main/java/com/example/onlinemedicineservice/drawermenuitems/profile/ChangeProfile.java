package com.example.onlinemedicineservice.drawermenuitems.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.onlinemedicineservice.Model.FirebaseUserModel;
import com.example.onlinemedicineservice.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangeProfile extends Fragment {

    private EditText firstnameeditText;
    private EditText lastnameEdittext;
    private EditText phonenumbereditText;
    private Button commitButton;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference userReference;
    private FirebaseAuth auth;

    private String name;
    private String phonenumber;
    private String userID;
    private FirebaseUserModel currentUser;

    public ChangeProfile(String name, String phonenumber, FirebaseUserModel user, String id){

        this.name = name;
        this.phonenumber = phonenumber;
        this.currentUser = user;
        this.userID = id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profilechange,container,false);

        assignId(rootView);

        firebaseDatabase = FirebaseDatabase.getInstance();
        userReference = firebaseDatabase.getReference("Users");
        auth = FirebaseAuth.getInstance();

        firstnameeditText.setText(currentUser.getFirstName());
        lastnameEdittext.setText(currentUser.getLastName());
        phonenumbereditText.setText(currentUser.getPhoneNumber());

        commitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commitButtonAction();
            }
        });

        return rootView;
    }

    private void commitButtonAction() {

        if (!checkForChanges()){
            Toast.makeText(getContext(),"No Changes Found!",Toast.LENGTH_SHORT).show();
        }else {

            userReference.child(userID).setValue(currentUser)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                               getActivity().getSupportFragmentManager().beginTransaction()
                                       .replace(R.id.host,new ProfileFragment())
                                       .commit();
                               Toast.makeText(getContext(), "Changes Commited", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }



    private boolean checkForChanges() {

        boolean changesOccured = false;

        if (!name.equals(firstnameeditText.getText() + " " + lastnameEdittext.getText())) {

            currentUser.setFirstName(firstnameeditText.getText().toString());
            currentUser.setLastName(lastnameEdittext.getText().toString());
            changesOccured = true;

        }

        if (!phonenumber.equals(phonenumbereditText.getText().toString())) {

            currentUser.setPhoneNumber(phonenumbereditText.getText().toString());
            changesOccured = true;

        }

        return changesOccured;

    }


    private void assignId(View view) {
        firstnameeditText = view.findViewById(R.id.name1Edittext);
        lastnameEdittext = view.findViewById(R.id.name2Edittext);
        phonenumbereditText = view.findViewById(R.id.profilephoneNumberEdittext);
        commitButton = view.findViewById(R.id.commitchangeButton);
    }


}
