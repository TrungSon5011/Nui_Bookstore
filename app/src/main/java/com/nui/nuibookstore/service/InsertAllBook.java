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

public class InsertAllBook extends AsyncTask<Void,Void,Boolean> {
       private Context context;
       private List<Book> books = new ArrayList<>();
   public InsertAllBook(Context context, List<Book> books){
       this.context = context;
       this.books = books;
   }

    @Override
    protected Boolean doInBackground(Void... voids) {
        Log.i("GetBookFromFirebase",books.toString());
        AppDatabase database = AppDatabase.getAppDatabase(context);
        BookDao bookDao = database.bookDao();
        for (int i = 0; i < books.size(); i++) {
            bookDao.insertBook(books.get(i));
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean success){
        System.err.println("Insert test done");
    }



//    private List<Book> setAllBook(){
//       List bookList = new ArrayList();
//       bookList.add(new Book("A minor fall","Price Ainsworth","The Minor Fall is a modern interpretation of an Old Testament saga,","Novel",30.2, R.drawable.a_minor_fall));
//       bookList.add(new Book("Five ways to fall out to love","Emily martin","This whip-smart rom-com explores the risks and rewards of letting love in, for fans of Jennifer E. Smith, Julie Buxbaum, and Sandhya Menon.","Novel",28.8,R.drawable.five_ways_to_fall_out_of_love));
//       bookList.add(new Book("Like home","Louisa Onome","a girl whose life is turned upside down after one local act of vandalism throws her relationships and even her neighborhood into turmoil.","Novel",14.3,R.drawable.like_home));
//       bookList.add(new Book("Some girls do","Jennifer Dugan","In this YA contemporary queer romance from the author of Hot Dog Girl , an openly gay track star falls for a closeted, bisexual teen beauty queen with a penchant for fixing up old cars.","Novel",24.3,R.drawable.some_girls_do));
//       bookList.add(new Book("The diary of bookseller","Shayun Bythell","Shaun Bythell owns the Bookshop, Scotland's largest secondhand bookshop. It contains 100,000 books spread over a mile of shelving, with twisting corridors and roaring fires, all set in a beautiful, rural town by the edge of the sea. ","Novel",45.4,R.drawable.the_diary_of_bookseller));
//       bookList.add(new Book("The great Gatsby","F.Scott Fitzgerad","The Great Gatsby is a 1925 novel by American writer F. Scott Fitzgerald. Set in the Jazz Age on Long Island, the novel depicts narrator Nick Carraway's interactions with mysterious millionaire Jay Gatsby and Gatsby's obsession to reunite with his former lover, Daisy Buchanan.","Novel",32.1,R.drawable.the_great_gatsby));
//       bookList.add(new Book("The light jar","Lisa Thompson","The Light Jar is a compelling mystery that celebrates imagination and the light within. Nate and his mother are running away","Novel",12.3,R.drawable.the_light_jar));
//       bookList.add(new Book("The lonely city","Olivia Laing","A dazzling work of biography, memoir, and cultural criticism on the subject of loneliness, told through the lives of iconic artists","Novel",14.5,R.drawable.the_lonely_city));
//       bookList.add(new Book("The loneliest in the universe","Lauren James","Romy tries to make the best of her lonely situation, but with only brief messages from ... I didn't want to read a cheesy book about Romeo and Juliet set in space","Novel",32.7,R.drawable.the_lonelyest_in_the_universe));
//       bookList.add(new Book("The transciptionist","Amy rowland","This powerful debut follows a woman who sets out to challenge the absurdity of the world around her.","Novel",23.5,R.drawable.the_transcriptionist));
//       return bookList;
//    }
}
