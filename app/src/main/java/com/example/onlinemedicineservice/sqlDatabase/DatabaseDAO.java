package com.example.onlinemedicineservice.sqlDatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DatabaseDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProduct(SQLProductModel SQLProductModel);

    @Query("SELECT * FROM product_table")
    List<SQLProductModel> getAllProduct();

    @Delete
    void deleteProduct(SQLProductModel SQLProductModel);

    @Query("DELETE FROM product_table")
    void deleteAllProduct();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrder(SQLOrderModel SQLOrderModel);

    @Query("SELECT * FROM Order_table")
    List<SQLOrderModel> getALlOrder();

    @Delete
    void deleteOrder(SQLOrderModel SQLOrderModel);

    @Query("DELETE FROM Order_table")
    void deleteAllOrder();


}
