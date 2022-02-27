package com.example.onlinemedicineservice.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class FirebaseProductModel implements Parcelable {

    private String chemicalFormula;
    private String companyId;
    private String dosageForm;
    private String price;
    private String productName;
    private String strength;
    private String quantity;
    private String productId;

    public FirebaseProductModel(String chemicalFormula, String companyId, String dosageForm, String price, String productName,
                                String quantity, String strength) {

        setChemicalFormula(chemicalFormula);
        setCompanyId(companyId);
        setDosageForm(dosageForm);
        setPrice(price);
        setProductName(productName);
        setStrength(strength);
        setQuantity(quantity);
    }

    private FirebaseProductModel(Parcel in) {
        chemicalFormula = in.readString();
        companyId = in.readString();
        dosageForm = in.readString();
        price = in.readString();
        productName = in.readString();
        strength = in.readString();
        quantity = in.readString();
        productId = in.readString();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(chemicalFormula);
        dest.writeString(companyId);
        dest.writeString(dosageForm);
        dest.writeString(price);
        dest.writeString(productName);
        dest.writeString(strength);
        dest.writeString(quantity);
        dest.writeString(productId);
    }
    @Override
    public int describeContents() {
        return 0;
    }
    public static final Creator<FirebaseProductModel> CREATOR = new Creator<FirebaseProductModel>() {
        @Override
        public FirebaseProductModel createFromParcel(Parcel in) {
            return new FirebaseProductModel(in);
        }

        @Override
        public FirebaseProductModel[] newArray(int size) {
            return new FirebaseProductModel[size];
        }
    };


    public String getQuantity() {
        return quantity;
    }
    private void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getChemicalFormula() {
        return chemicalFormula;
    }
    private void setChemicalFormula(String chemicalFormula) {
        this.chemicalFormula = chemicalFormula;
    }

    public String getCompanyId() {
        return companyId;
    }
    private void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getDosageForm() {
        return dosageForm;
    }
    private void setDosageForm(String dosageForm) {
        this.dosageForm = dosageForm;
    }

    public String getPrice() {
        return price;
    }
    private void setPrice(String price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }
    private void setProductName(String productName) {
        this.productName = productName;
    }

    public String getStrength() {
        return strength;
    }
    private void setStrength(String strength) {
        this.strength = strength;
    }

    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
}
