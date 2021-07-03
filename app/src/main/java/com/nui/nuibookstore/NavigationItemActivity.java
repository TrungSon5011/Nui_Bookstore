package com.nui.nuibookstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;

import com.nui.nuibookstore.service.GetBooksByGenre;

public class NavigationItemActivity extends AppCompatActivity {
    public static final String GENRE_BOOK = "genreBook";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_navigation_layout);
        setSupportActionBar(toolbar);
        String genre = getIntent().getExtras().getString(GENRE_BOOK);
        new GetBooksByGenre(this,genre).execute();


    }

    public Context getConText(){
        return this;
    }
}