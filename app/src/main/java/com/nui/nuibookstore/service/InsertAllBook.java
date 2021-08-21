package com.nui.nuibookstore.service;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nui.nuibookstore.R;
import com.nui.nuibookstore.dao.BookDao;
import com.nui.nuibookstore.database.AppDatabase;
import com.nui.nuibookstore.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InsertAllBook extends AsyncTask<Void, Void, Boolean> {
    private Context context;
    private List<Book> books = new ArrayList<>();

    public InsertAllBook(Context context, List<Book> books) {
        this.context = context;
        this.books = books;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        AppDatabase database = AppDatabase.getAppDatabase(context);
        BookDao bookDao = database.bookDao();
        for (int i = 0; i < books.size(); i++) {
            bookDao.insertBook(books.get(i));
        }
        return true;
    }
}
