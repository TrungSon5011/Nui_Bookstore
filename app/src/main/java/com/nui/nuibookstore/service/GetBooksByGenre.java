package com.nui.nuibookstore.service;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.nui.nuibookstore.BookDetailActivity;
import com.nui.nuibookstore.NavigationItemActivity;
import com.nui.nuibookstore.R;
import com.nui.nuibookstore.card_view.CaptionedImagesAdapter;
import com.nui.nuibookstore.dao.BookDao;
import com.nui.nuibookstore.database.AppDatabase;
import com.nui.nuibookstore.model.Book;

import java.util.List;

public class GetBooksByGenre extends AsyncTask<Void,Void,List<Book>> {
    private NavigationItemActivity navigationItemActivity;
    private String genre;
    public static List<Book> getBooksByGenre;

    public GetBooksByGenre(NavigationItemActivity navigationItemActivity, String genre) {
        this.navigationItemActivity = navigationItemActivity;
        this.genre = genre;
    }
    @Override
    protected void onPreExecute(){
        Toast.makeText(navigationItemActivity.getConText(),"Please wait...",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected List<Book> doInBackground(Void... voids) {
        AppDatabase database = AppDatabase.getAppDatabase(navigationItemActivity.getConText());
        BookDao bookDao = database.bookDao();
        List<Book> bookList = bookDao.findByGenre(genre);
        return bookList;
    }
    @Override
    protected void onPostExecute(List<Book> bookList){
        RecyclerView recyclerView = (RecyclerView) navigationItemActivity.findViewById(R.id.result_navigation_recycler);
        getBooksByGenre = bookList;
//        int [] imageResourceIds = new int[bookList.size()];
//        String [] names = new String[bookList.size()];
//        String [] authors = new String[bookList.size()];
//        double [] price = new double[bookList.size()];
//        for (int i = 0; i < bookList.size() ; i++) {
//            imageResourceIds[i] = bookList.get(i).getPictureResourceId();
//            names[i] = bookList.get(i).getName();
//            authors[i] = bookList.get(i).getAuthor();
//            price[i] = bookList.get(i).getPrice();
//        }
        CaptionedImagesAdapter captionedImagesAdapter = new CaptionedImagesAdapter(bookList);
        recyclerView.setAdapter(captionedImagesAdapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        captionedImagesAdapter.setListener(new CaptionedImagesAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(navigationItemActivity, BookDetailActivity.class);
                intent.putExtra(BookDetailActivity.BOOK_POSITION,position);
                intent.putExtra(BookDetailActivity.CHECK,4);
                navigationItemActivity.startActivity(intent);
            }
        });
    }

}
