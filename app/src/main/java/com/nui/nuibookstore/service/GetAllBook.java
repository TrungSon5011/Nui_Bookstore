package com.nui.nuibookstore.service;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.nui.nuibookstore.BookDetailActivity;
import com.nui.nuibookstore.card_view.CaptionedImagesAdapter;
import com.nui.nuibookstore.HomeActivity;
import com.nui.nuibookstore.R;
import com.nui.nuibookstore.dao.BookDao;
import com.nui.nuibookstore.database.AppDatabase;
import com.nui.nuibookstore.model.Book;

import java.util.List;

public class GetAllBook extends AsyncTask<Void, Void, List<Book>> {

    private Context context;
    private HomeActivity homeActivity;
    public static List<Book> getAllBook;

    public GetAllBook(HomeActivity homeActivity) {

        this.homeActivity = homeActivity;
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(homeActivity.getContextToDB(), "Please wait...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected List<Book> doInBackground(Void... voids) {
        AppDatabase database = AppDatabase.getAppDatabase(homeActivity.getContextToDB());
        BookDao bookDao = database.bookDao();
        return bookDao.getAll();
    }

    @Override
    protected void onPostExecute(List<Book> books) {
        RecyclerView recyclerView = (RecyclerView) homeActivity.findViewById(R.id.home_recycler);
        List<Book> bookList = books;
        getAllBook = books;
        CaptionedImagesAdapter captionedImagesAdapter = new CaptionedImagesAdapter(books, homeActivity);
        recyclerView.setAdapter(captionedImagesAdapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        captionedImagesAdapter.setListener(new CaptionedImagesAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(homeActivity, BookDetailActivity.class);
                intent.putExtra(BookDetailActivity.BOOK_POSITION, position);
                intent.putExtra(BookDetailActivity.CHECK, 1);
                homeActivity.startActivity(intent);
            }
        });
    }
}
