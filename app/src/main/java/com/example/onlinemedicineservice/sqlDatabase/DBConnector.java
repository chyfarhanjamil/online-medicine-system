package com.example.onlinemedicineservice.sqlDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

public class DBConnector extends AndroidViewModel {

    private SQLRepository sqlRepository;
    private List<SQLProductModel> SQLProductModelList;
    private List<SQLOrderModel> SQLOrderModelList;


    public DBConnector(@NonNull Application application) {
        super(application);
        sqlRepository = new SQLRepository(application);
        SQLProductModelList = sqlRepository.getAllProduct();
        SQLOrderModelList = sqlRepository.getALLOrder();
    }

    public void insertProduct(SQLProductModel SQLProductModel){
        sqlRepository.insertProduct(SQLProductModel);
    }


    public List<SQLProductModel> getAllProduct(){
       return SQLProductModelList;
    }


    public void deleteProduct(SQLProductModel SQLProductModel){
        sqlRepository.deleteProduct(SQLProductModel);
    }

    public void deleteAllProduct(){
        sqlRepository.deleteAllProduct();
    }

    public void insertOrder(SQLOrderModel SQLOrderModel){
        sqlRepository.insertOrder(SQLOrderModel);
    }


    public List<SQLOrderModel> getALlOrder(){
        return SQLOrderModelList;
    }


    public void deleteOrder(SQLOrderModel SQLOrderModel){
        sqlRepository.deleteOrder(SQLOrderModel);
    }

    public void deleteAllOrder(){
        sqlRepository.deleteAllOrder();
    }

}
