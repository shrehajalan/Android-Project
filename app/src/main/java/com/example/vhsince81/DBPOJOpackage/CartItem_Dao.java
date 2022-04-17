package com.example.vhsince81.DBPOJOpackage;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CartItem_Dao {

    @Insert
    void insert(CARTITEMDTO cartItem);

    @Delete
    void delete(CARTITEMDTO cartItem);

    @Update
    void update(CARTITEMDTO cartItem);

    @Query("DELETE FROM VH_CartTable")
    void deleteAll();

    @Query("SELECT * FROM VH_CartTable")
    List<CARTITEMDTO> fetchAll();

    @Query("SELECT * FROM VH_CartTable WHERE `Style Id` = :styleId")
    CARTITEMDTO getCartItemBy(String styleId);

    @Query("SELECT COUNT(*) FROM VH_CartTable")
    int countCartItems();

}
