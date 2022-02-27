package com.example.onlinemedicineservice.drawermenuitems.profile;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.onlinemedicineservice.Model.FirebaseUserModel;
import com.example.onlinemedicineservice.R;
import com.example.onlinemedicineservice.CustomerAuthentication.SignInActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.onlinemedicineservice.HomeActivity.CHANGE_PROFILE_TAG;


public class ProfileFragment extends Fragment {



    private TextView emailView;
    private TextView phoneNumberView;
    private TextView nameView;
    private Button editButton;
    private Button deleteButton;
    private String currentUserID;
    private FirebaseUserModel currentUser;

    interface FetchUser{
        void onCallBack(FirebaseUserModel user);
    }

    public ProfileFragment(){

        currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View view  = inflater.inflate(R.layout.fragment_profile, container, false);
        assignID(view);
        getUserInstance(user -> {
            currentUser = user;
            nameView.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
            emailView.setText(currentUser.getEmail());
            phoneNumberView.setText(currentUser.getPhoneNumber());
        });



        editButton.setOnClickListener(view1 -> editButtonAction());
        deleteButton.setOnClickListener(view12 -> new AlertDialog.Builder(getContext())
                .setMessage("Confirm Delete?")
                .setPositiveButton("yes", (dialogInterface, i) -> deleteAccount())
                .setNegativeButton("no", (dialogInterface, i) -> {
                })
                .setIcon(R.drawable.ic_error_white_24dp)
                .show());

        return view;
    }

    private void getUserInstance(final FetchUser fetchUser) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference("Users");


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                fetchUser.onCallBack(dataSnapshot.child(currentUserID).getValue(FirebaseUserModel.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }




    private void assignID(@NonNull View view) {
        emailView = view.findViewById(R.id.emailAddress);
        phoneNumberView = view.findViewById(R.id.phoneNumber);
        nameView = view.findViewById(R.id.name);
        editButton = view.findViewById(R.id.editButton);
        deleteButton = view.findViewById(R.id.deleteButton);

    }

    private void deleteAccount() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userReference = database.getReference("Users");
        FirebaseAuth auth = FirebaseAuth.getInstance();
        final FirebaseUser user= auth.getCurrentUser();

        if(user != null) {

            userReference.child(user.getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    Toast.makeText(getContext(), "Account Removed", Toast.LENGTH_SHORT).show();
                }
            });

            user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Intent intent = new Intent(getContext(), SignInActivity.class);
                    startActivity(intent);
                }
            });


        }
    }


    private void editButtonAction() {

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.host,

                new ChangeProfile(nameView.getText().toString(),
                                  phoneNumberView.getText().toString(),
                                  currentUser,currentUserID),
                CHANGE_PROFILE_TAG).commit();

    }







}
