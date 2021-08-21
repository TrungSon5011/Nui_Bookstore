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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nui.nuibookstore.model.Book;
import com.nui.nuibookstore.prevalent.Prevalent;
import com.nui.nuibookstore.service.InsertAllBook;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private Button joinNowButton;
    private Button loginButton;
    private ProgressDialog loadingBar;
    private final Context context = this;
    private FirebaseAuth firebaseAuth;
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        joinNowButton = (Button) findViewById(R.id.main_join_btn);
        loginButton = (Button) findViewById(R.id.main_login_btn);
        loadingBar = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
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
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        String emailUserKey = Paper.book().read(Prevalent.userEmailKey);
        String passwordUserKey = Paper.book().read(Prevalent.userPasswordKey);
        if (emailUserKey != null && passwordUserKey != null) {
            if (!TextUtils.isEmpty(emailUserKey) && !TextUtils.isEmpty(passwordUserKey)) {
                allowAccess(emailUserKey, passwordUserKey);
                loadingBar.setTitle("Already logged in");
                loadingBar.setMessage("Please wait...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
            }
        }
        List<Book> bookList = getAllBookFromFirebase();
    }

    private void allowAccess(String emailUserKey, String passwordUserKey) {
        firebaseAuth.signInWithEmailAndPassword(emailUserKey, passwordUserKey).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Log.i(TAG, "Failed to remember password: " + task.getException());
                }
                loadingBar.dismiss();
            }
        });
    }

    private List<Book> getAllBookFromFirebase() {
        List<Book> bookList = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.child("Books").getChildren()) {
                    Book book = dataSnapshot.getValue(Book.class);
                    Log.i("BookFirebase", book.toString());
                    bookList.add(book);
                    InsertAllBook insertAllBook = new InsertAllBook(context, bookList);

                    insertAllBook.execute();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        return bookList;
    }
}
