package com.example.onlinemedicineservice.Model;


import android.os.Parcel;
import android.os.Parcelable;

public class FirebaseUserModel implements Parcelable {

    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String Uid;

    public FirebaseUserModel() {
    }


    public FirebaseUserModel(String email, String firtsName, String lastName , String phoneNumber) {

        setFirstName(firtsName);
        setLastName(lastName);
        setEmail(email);
        setPhoneNumber(phoneNumber);

    }


    protected FirebaseUserModel(Parcel in) {
        email = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        phoneNumber = in.readString();
        Uid = in.readString();
    }

    public static final Creator<FirebaseUserModel> CREATOR = new Creator<FirebaseUserModel>() {
        @Override
        public FirebaseUserModel createFromParcel(Parcel in) {
            return new FirebaseUserModel(in);
        }

        @Override
        public FirebaseUserModel[] newArray(int size) {
            return new FirebaseUserModel[size];
        }
    };



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(phoneNumber);
        dest.writeString(Uid);
    }
}
