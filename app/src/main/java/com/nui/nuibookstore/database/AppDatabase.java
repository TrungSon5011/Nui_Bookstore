package com.nui.nuibookstore.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.nui.nuibookstore.dao.BookDao;
import com.nui.nuibookstore.model.Book;

@Database(entities = {Book.class} , version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE ;
    public abstract BookDao bookDao();
    public static AppDatabase getAppDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"book_database").build();
        }
        return INSTANCE;
    }
    public static void destroyInstance(){
        INSTANCE = null;
    }
}
