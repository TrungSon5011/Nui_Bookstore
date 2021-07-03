package com.nui.nuibookstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nui.nuibookstore.common.ReplaceString;
import com.nui.nuibookstore.model.OrderInformation;
import com.nui.nuibookstore.model.User;
import com.nui.nuibookstore.prevalent.Prevalent;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    private EditText nameEditText ;
    private EditText phoneEditText;
    private EditText homeAddressEditText;
    private EditText cityEditText;
    private Button confirmButton;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main_layout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        nameEditText = (EditText) findViewById(R.id.name_edit_text);
        phoneEditText = (EditText) findViewById(R.id.phone_edit_text);
        homeAddressEditText = (EditText) findViewById(R.id.home_address_edit_text);
        cityEditText = (EditText) findViewById(R.id.city_edit_text);
        confirmButton = (Button) findViewById(R.id.confirm_order_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });

    }

    private void check() {
        if (TextUtils.isEmpty(nameEditText.getText().toString())){
            Toast.makeText(this,"Please provide your full name",Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(phoneEditText.getText().toString())){
            Toast.makeText(this,"Please provide your phone",Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(homeAddressEditText.getText().toString())){
            Toast.makeText(this,"Please provide home address",Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(cityEditText.getText().toString())){
            Toast.makeText(this,"Please provide your city",Toast.LENGTH_SHORT);
        }else {
            confirmOrder();
        }
    }

    private void confirmOrder() {
        String mail = ReplaceString.encodeString(Prevalent.userEmail);
        SimpleDateFormat dateFormat =  new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        String dateFormatted = dateFormat.format(date);
        Double totalPrice = 0.0;
        for (int i = 0;i < BookDetailActivity.bookCartList.size();i++){
            totalPrice += (BookDetailActivity.bookCartList.get(i).getBook().getPrice())*(BookDetailActivity.bookCartList.get(i).getQuantity());
        }
//        loadingBar = new ProgressDialog(this);
//        loadingBar.setTitle("Ordering");
//        loadingBar.setMessage("Please wait...");
//        loadingBar.setCanceledOnTouchOutside(false);
//        loadingBar.show();
        final DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference() ;
        HashMap<String,Object> orderMap = new HashMap<>();
        orderMap.put("name",nameEditText.getText().toString());
        orderMap.put("phone",phoneEditText.getText().toString());
        orderMap.put("homeAddress",homeAddressEditText.getText().toString());
        orderMap.put("city",cityEditText.getText().toString());
        orderMap.put("state","not shipped");
        orderMap.put("bookCarts",BookDetailActivity.bookCartList);
        orderMap.put("totalPrice",totalPrice);
        orderMap.put("date",dateFormatted);
        orderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderRef.child("Orders").child(mail).child(dateFormatted).updateChildren(orderMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(OrderActivity.this,"Order is successfully",Toast.LENGTH_SHORT).show();
                            if (BookDetailActivity.bookCartList != null){
                                BookDetailActivity.bookCartList.clear();
                            }
//                            loadingBar.dismiss();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Intent intent = new Intent(OrderActivity.this,OrderInformationActivity.class);
        startActivity(intent);

    }
}