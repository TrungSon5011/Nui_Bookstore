package com.nui.nuibookstore.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.nui.nuibookstore.model.Book;

import java.util.List;

@Dao
public interface BookDao {
    @Query("SELECT * FROM book")
    List<Book> getAll();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertBook(Book book);
    @Query("SELECT * FROM book WHERE book_name like :name")
    List<Book> findByName(String name);
    @Update
    void update(Book book);
    @Delete
    void delete(Book book);
    @Query("SELECT * FROM book WHERE genre = :genre")
    List<Book> findByGenre(String genre);
}
