package com.example.onlinemedicineservice.sqlDatabase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Product_table")
public class SQLProductModel {

     @PrimaryKey
     @NonNull
     private String productId;
     @ColumnInfo
     private String chemicalFormula;
     @ColumnInfo
     private String companyId;
     @ColumnInfo
     private String dosageForm;
     @ColumnInfo
     @NonNull
     private String price;
     @ColumnInfo
     @NonNull
     private String productName;
     @ColumnInfo
     private String strength;
     @ColumnInfo
     private int selectedQuantity;

    public SQLProductModel(@NonNull String productId, String chemicalFormula, String companyId, String dosageForm,
                           String price, String productName, String strength, int selectedQuantity) {

        this.productId = productId;
        this.chemicalFormula = chemicalFormula;
        this.companyId = companyId;
        this.dosageForm = dosageForm;
        this.price = price;
        this.productName = productName;
        this.strength = strength;
        this.selectedQuantity = selectedQuantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getChemicalFormula() {
        return chemicalFormula;
    }

    public void setChemicalFormula(String chemicalFormula) {
        this.chemicalFormula = chemicalFormula;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getDosageForm() {
        return dosageForm;
    }

    public void setDosageForm(String dosageForm) {
        this.dosageForm = dosageForm;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public int getSelectedQuantity() {
        return selectedQuantity;
    }

    public void setSelectedQuantity(int selectedQuantity) {
        this.selectedQuantity = selectedQuantity;
    }
}
