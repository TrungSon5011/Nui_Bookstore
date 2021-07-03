package com.nui.nuibookstore;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nui.nuibookstore.model.Book;
import com.nui.nuibookstore.model.BookCart;
import com.nui.nuibookstore.service.FindByName;
import com.nui.nuibookstore.service.GetAllBook;
import com.nui.nuibookstore.service.GetBooksByGenre;

import java.util.ArrayList;
import java.util.List;


public class BookDetailActivity extends AppCompatActivity {
    public static final String BOOK_POSITION = "bookPosition";
    public static final String CHECK = "check";
    public static List<BookCart> bookCartList = new ArrayList<>();
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_book_detail);
        setSupportActionBar(toolbar);
        ActionBar actionBar = (ActionBar) getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        int position = (Integer) getIntent().getExtras().get(BOOK_POSITION);
        int check = (int) getIntent().getExtras().get(CHECK);
        List<Book> bookList;
        if (check == 1){
            bookList = GetAllBook.getAllBook;
            book = bookList.get(position);
        }else if(check == 3){
            book = bookCartList.get(position).getBook();
        }else if(check == 2){
            bookList = FindByName.bookListFindByName;
            book = bookList.get(position);
        }else if (check == 4){
            bookList = GetBooksByGenre.getBooksByGenre;
            book = bookList.get(position);
        }
        setView(book);
    }
    private void setView(Book book){
        TextView name = (TextView) findViewById(R.id.detail_book_name);
        name.setText(book.getName());
        ImageView imageView = (ImageView) findViewById(R.id.book_detail_image);
        imageView.setImageResource(book.getPictureResourceId());
        imageView.setContentDescription(book.getName());
        TextView author = (TextView) findViewById(R.id.detail_book_author);
        author.setText(book.getAuthor());
        TextView price = (TextView) findViewById(R.id.detail_book_price);
        price.setText(book.getPrice().toString());
        TextView description = (TextView) findViewById(R.id.detail_book_description);
        description.setText(book.getDescription());
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addToCart(View view){
        if (bookCartList.size()== 0){
            bookCartList.add(new BookCart(book,1));
        }else {
            boolean check = false;
            for (int i = 0; i < bookCartList.size();i++){
                if(bookCartList.get(i).getBook().equals(book)) {
                    bookCartList.get(i).setQuantity(bookCartList.get(i).getQuantity()+1);
                    check = true;
                    break;
                }
            }
            if (!check){
                bookCartList.add(new BookCart(book,1));
            }
        }

        Toast.makeText(this,"Have added to cart!",Toast.LENGTH_SHORT).show();

    }
}