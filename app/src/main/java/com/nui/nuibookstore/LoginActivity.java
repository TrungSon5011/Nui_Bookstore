package com.nui.nuibookstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.nui.nuibookstore.prevalent.Prevalent;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmailEditText;
    private EditText inputPasswordEditText;
    private Button loginButton;
    private ProgressDialog loadingBar;
    private CheckBox rememberCheckBox;
    private String stringDbName = "Users";
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inputEmailEditText = (EditText) findViewById(R.id.register_email_input);
        inputPasswordEditText = (EditText) findViewById(R.id.register_password_input);
        loginButton = (Button) findViewById(R.id.login_btn);
        loadingBar = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
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
        String email = inputEmailEditText.getText().toString().trim();
        String password = inputPasswordEditText.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please write your email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please write your password", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            loginByEmailAndPasswordToFirebase(email, password);
        }
    }

    private void loginByEmailAndPasswordToFirebase(String email, String password) {
        if (rememberCheckBox.isChecked()) {
            Paper.book().write(Prevalent.userEmailKey, email);
            Paper.book().write(Prevalent.userPasswordKey, password);
        }
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    loadingBar.dismiss();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    loadingBar.dismiss();
                    Toast.makeText(LoginActivity.this, "Failed to login: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
