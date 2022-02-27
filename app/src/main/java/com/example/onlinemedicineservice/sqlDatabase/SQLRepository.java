package com.example.onlinemedicineservice.sqlDatabase;

import android.app.Application;
import java.util.List;

public class SQLRepository {

    private DatabaseDAO databaseDAO;
    private List<SQLProductModel> SQLProductModelList;
    private List<SQLOrderModel> SQLOrderModelList;

    SQLRepository(Application application){
        Database productDatabase = Database.getInstance(application);
        databaseDAO = productDatabase.productDAO();
        SQLProductModelList = databaseDAO.getAllProduct();
        SQLOrderModelList = databaseDAO.getALlOrder();
    }

    List<SQLProductModel> getAllProduct() {
         return SQLProductModelList;
    }

    void insertProduct(final SQLProductModel SQLProductModel) {
        Database.databaseWriteExecutor.execute(() -> {
            databaseDAO.insertProduct(SQLProductModel);
        });
    }

    void deleteProduct(final SQLProductModel SQLProductModel) {
        Database.databaseWriteExecutor.execute(() -> {
            databaseDAO.deleteProduct(SQLProductModel);
        });
    }

    void deleteAllProduct() {
        Database.databaseWriteExecutor.execute(() -> {
            databaseDAO.deleteAllProduct();
        });
    }

    List<SQLOrderModel> getALLOrder() {
        return SQLOrderModelList;
    }

    void insertOrder(final SQLOrderModel SQLOrderModel) {
        Database.databaseWriteExecutor.execute(() -> {
            databaseDAO.insertOrder(SQLOrderModel);
        });
    }

    void deleteOrder(final SQLOrderModel SQLOrderModel) {
        Database.databaseWriteExecutor.execute(() -> {
            databaseDAO.deleteOrder(SQLOrderModel);
        });
    }

    void deleteAllOrder() {
        Database.databaseWriteExecutor.execute(() -> {
            databaseDAO.deleteAllOrder();
        });
    }


}




