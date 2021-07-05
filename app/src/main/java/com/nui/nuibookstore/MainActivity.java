package com.nui.nuibookstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nui.nuibookstore.common.ReplaceString;
import com.nui.nuibookstore.model.Book;
import com.nui.nuibookstore.model.User;
import com.nui.nuibookstore.prevalent.Prevalent;
import com.nui.nuibookstore.service.DeleteBook;
import com.nui.nuibookstore.service.InsertAllBook;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private Button joinNowButton;
    private Button loginButton;
    private ProgressDialog loadingBar;
    private Context context = this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        joinNowButton = (Button) findViewById(R.id.main_join_btn);
        loginButton = (Button) findViewById(R.id.main_login_btn);
        loadingBar = new ProgressDialog(this);
        Paper.init(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });
        joinNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        String emailUserKey = Paper.book().read(Prevalent.userEmailKey);
        String passwordUserKey = Paper.book().read(Prevalent.userPasswordKey);

        if (emailUserKey != "" && passwordUserKey != ""){
            Prevalent.userEmail = emailUserKey;
            if (!TextUtils.isEmpty(emailUserKey) && !TextUtils.isEmpty(passwordUserKey)){
                allowAccess(emailUserKey,passwordUserKey);
                LoginActivity.userEmail = ReplaceString.decodeString(emailUserKey);
                loadingBar.setTitle("Already logged in");
                loadingBar.setMessage("Please wait...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
            }
        }


        List<Book> bookList = getAllBookFromFirebase();

    }
    private List<Book> getAllBookFromFirebase(){
        List<Book> bookList = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.child("Books").getChildren()){
                    Book book = dataSnapshot.getValue(Book.class);
                    Log.i("BookFirebase",book.toString());
                    bookList.add(book);
                    InsertAllBook insertAllBook = new InsertAllBook(context, bookList);

                    insertAllBook.execute();
                }
            }

            @Override
            public void onCancelled( DatabaseError error) {

            }
        });
        return bookList;
    }

    private void allowAccess(String email, String password) {
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Users").child(email).exists()){
                    User user = snapshot.child("Users").child(email).getValue(User.class);
                    if (user.getEmail().equals(email)){
                        if (user.getPassword().equals(password)){
//                            Toast.makeText(MainActivity.this,"Logged in successfully",Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                            startActivity(intent);

                        }else {
                            Toast.makeText(MainActivity.this,"Password is wrong",Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }

                }else {
                    Toast.makeText(MainActivity.this,"Account with this "+ email.replace(",",".")  + " do not exists",Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(MainActivity.this,"You may need to create account",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void login(View view){
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
    }
}