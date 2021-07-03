package com.nui.nuibookstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nui.nuibookstore.common.ReplaceString;
import com.nui.nuibookstore.model.User;
import com.nui.nuibookstore.prevalent.Prevalent;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmailEditText;
    private EditText inputPasswordEditText;
    private Button loginButton;
    private ProgressDialog loadingBar;
    private CheckBox rememberCheckBox;
    private String stringDbName = "Users";
    public static String userEmail ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmailEditText = (EditText) findViewById(R.id.register_email_input);
        inputPasswordEditText = (EditText) findViewById(R.id.register_password_input);
        loginButton = (Button) findViewById(R.id.login_btn);
        loadingBar = new ProgressDialog(this);

        rememberCheckBox = (CheckBox) findViewById(R.id.remember_me_checkbox);
        Paper.init(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

    }

    private void loginUser() {
        String email = inputEmailEditText.getText().toString();
        if (Prevalent.userEmail == null ){
            Prevalent.userEmail = email;
        }
        userEmail = email;
        email = ReplaceString.encodeString(email);
        String password = inputPasswordEditText.getText().toString();
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please write your email",Toast.LENGTH_SHORT).show();

        }else if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please write your password",Toast.LENGTH_SHORT).show();
        }else {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            allowAccessToAccount(email,password);
        }
    }

    private void allowAccessToAccount(String email, String password) {

        if (rememberCheckBox.isChecked()){
            Paper.book().write(Prevalent.userEmailKey,email);
            Paper.book().write(Prevalent.userPasswordKey,password);
        }
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(stringDbName).child(email).exists()){
                    User user = snapshot.child(stringDbName).child(email).getValue(User.class);
                    if (user.getEmail().equals(email)){
                        if (user.getPassword().equals(password)){
                            Toast.makeText(LoginActivity.this,"Logged in successfully",Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                            startActivity(intent);

                        }else {
                            Toast.makeText(LoginActivity.this,"Password is wrong",Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }

                }else {
                    Toast.makeText(LoginActivity.this,"Account with this "+ email.replace(",",".")  + " do not exists",Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(LoginActivity.this,"You may need to create account",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}