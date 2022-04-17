package com.example.vhsince81.DBPOJOpackage;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


@Database(entities = {CARTITEMDTO.class }, version = 2)
public  abstract class Database_Room extends RoomDatabase {


    public static Database_Room VH_DB;
    public abstract CartItem_Dao getCartDao();
    public static Database_Room getInstance(Context ctx)
    {

        if (null == Database_Room.VH_DB) {
            Database_Room.VH_DB = Room.databaseBuilder(ctx,
                    Database_Room.class, "VH_Databasenew").allowMainThreadQueries().build();
        }
        return Database_Room.VH_DB;

    }
    public void cleanUp() {
        VH_DB = null;
    }

}
