package com.example.onlinemedicineservice.sqlDatabase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Order_table")
public class SQLOrderModel {

    @PrimaryKey
    @NonNull
    private String orderID;
    @ColumnInfo
    private String orderTime;
    @ColumnInfo
    private String orderCost;
    @ColumnInfo
    private String paymentMethod;
    @ColumnInfo
    private String status;


    public SQLOrderModel(String orderID, String orderTime, String orderCost, String paymentMethod, String status) {
        this.orderID = orderID;
        this.orderTime = orderTime;
        this.orderCost = orderCost;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }


    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(String orderCost) {
        this.orderCost = orderCost;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
