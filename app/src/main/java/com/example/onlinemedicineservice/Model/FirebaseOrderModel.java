package com.example.onlinemedicineservice.Model;

import android.os.LocaleList;
import android.os.Parcelable;

import com.example.onlinemedicineservice.ProductDetails;
import com.example.onlinemedicineservice.sqlDatabase.SQLProductModel;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class FirebaseOrderModel {

     private LocalDateTime orderTime;
     private String userId;
     private String userName;
     private String userPhoneNumber;
     private List<String> ProductList;
     private String address;
     private String totalPayment;
     private String paymentMethod;
     private String transactionID;
     private String transactionNumber;
     private String status;

    public FirebaseOrderModel(String address,
                              LocalDateTime orderTime,
                              String userId,
                              String userName,
                              String userPhoneNumber,
                              List<String> ProductList,
                              String totalPayment,
                              String paymentMethod,
                              String transactionID,
                              String transactionNumber,
                              String status) {

        this.orderTime = orderTime;
        this.userId = userId;
        this.userName = userName;
        this.userPhoneNumber = userPhoneNumber;
        this.ProductList = ProductList;
        this.address = address;
        this.status = status;
        this.totalPayment = totalPayment;
        this.paymentMethod = paymentMethod;
        this.transactionID = transactionID;
        this.transactionNumber = transactionNumber;


    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }


    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getProductList() {
        return ProductList;
    }

    public void setProductList(List<String> productList) {
        this.ProductList = productList;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(String totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
