package com.nui.nuibookstore.service;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.nui.nuibookstore.BookDetailActivity;
import com.nui.nuibookstore.card_view.CaptionedImagesAdapter;
import com.nui.nuibookstore.R;
import com.nui.nuibookstore.SearchResultActivity;
import com.nui.nuibookstore.dao.BookDao;
import com.nui.nuibookstore.database.AppDatabase;
import com.nui.nuibookstore.model.Book;

import java.util.List;

public class FindByName extends AsyncTask<Void, Void, List<Book>> {
    private SearchResultActivity searchResultActivity;
    private String name;
    public static List<Book> bookListFindByName;

    public FindByName(SearchResultActivity searchResultActivity, String name) {
        this.searchResultActivity = searchResultActivity;
        this.name = name;
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(searchResultActivity.getContext(), "Please wait...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected List<Book> doInBackground(Void... voids) {
        AppDatabase appDatabase = AppDatabase.getAppDatabase(searchResultActivity.getContext());
        BookDao bookDao = appDatabase.bookDao();
        List<Book> books = bookDao.findByName(name);
        return books;
    }

    @Override
    protected void onPostExecute(List<Book> bookList) {
        RecyclerView recyclerView = (RecyclerView) searchResultActivity.findViewById(R.id.result_recycler);
        bookListFindByName = bookList;
        if (bookList.size() == 0) {
            Toast.makeText(searchResultActivity.getContext(), "Found 0 book", Toast.LENGTH_SHORT).show();
        } else {
            CaptionedImagesAdapter captionedImagesAdapter = new CaptionedImagesAdapter(bookList, searchResultActivity);
            recyclerView.setAdapter(captionedImagesAdapter);
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(staggeredGridLayoutManager);
            captionedImagesAdapter.setListener(new CaptionedImagesAdapter.Listener() {
                @Override
                public void onClick(int position) {
                    Intent intent = new Intent(searchResultActivity, BookDetailActivity.class);
                    intent.putExtra(BookDetailActivity.BOOK_POSITION, position);
                    intent.putExtra(BookDetailActivity.CHECK, 2);
                    searchResultActivity.startActivity(intent);
                }
            });
        }
    }
}
