package com.nui.nuibookstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.nui.nuibookstore.model.Book;
import com.nui.nuibookstore.service.GetAllBook;

import java.util.List;

import io.paperdb.Paper;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        new GetAllBook(this).execute();
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout) ;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.nav_open_drawer,R.string.nav_close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView emailTextView  = (TextView) header.findViewById(R.id.email_text_view);
        emailTextView.setText(LoginActivity.userEmail);
        navigationView.setNavigationItemSelectedListener(this);
        Paper.init(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.cart:
                Intent intent = new Intent(this,CartActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public Context getContextToDB(){
        return this;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent intent = null;
        switch (id){
            case R.id.novel:
                intent = new Intent(this,NavigationItemActivity.class);
                intent.putExtra(NavigationItemActivity.GENRE_BOOK,"Novel");
                break;
            case R.id.politics:
                intent = new Intent(this,NavigationItemActivity.class);
                intent.putExtra(NavigationItemActivity.GENRE_BOOK,"Politics");
                break;
            case R.id.children:
                intent = new Intent(this,NavigationItemActivity.class);
                intent.putExtra(NavigationItemActivity.GENRE_BOOK,"Children");
                break;
            case R.id.history:
                intent = new Intent(this,NavigationItemActivity.class);
                intent.putExtra(NavigationItemActivity.GENRE_BOOK,"History");
                break;
            case R.id.science:
                intent = new Intent(this,NavigationItemActivity.class);
                intent.putExtra(NavigationItemActivity.GENRE_BOOK,"Science");
                break;
            case R.id.logout:
                Paper.book().destroy();
                intent = new Intent(this,MainActivity.class);
                break;
        }
        startActivity(intent);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}