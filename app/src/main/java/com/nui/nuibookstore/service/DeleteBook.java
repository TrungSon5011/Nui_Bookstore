package com.nui.nuibookstore.service;

import android.content.Context;
import android.os.AsyncTask;

import com.nui.nuibookstore.R;
import com.nui.nuibookstore.dao.BookDao;
import com.nui.nuibookstore.database.AppDatabase;
import com.nui.nuibookstore.model.Book;

import java.util.ArrayList;
import java.util.List;

public class DeleteBook extends AsyncTask<Void, Void, Void> {

    private Context context;

    public DeleteBook(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        AppDatabase database = AppDatabase.getAppDatabase(context);
        BookDao bookDao = database.bookDao();
        List<Book> bookList = getAllBookToDelete();
        for (int i = 0; i < bookList.size(); i++) {
            bookDao.delete(bookList.get(i));
        }
        return null;
    }

    private List<Book> getAllBookToDelete() {
        List<Book> bookList = new ArrayList<>();
        return bookList;
    }
}
