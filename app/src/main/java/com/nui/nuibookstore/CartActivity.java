package com.nui.nuibookstore;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nui.nuibookstore.card_view.CaptionedBookAdapter;
import com.nui.nuibookstore.model.BookCart;
import com.nui.nuibookstore.model.OrderInformation;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main_cart);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Button orderInformationButton = (Button) findViewById(R.id.order_information_button);
        orderInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, OrderInformationActivity.class);
                startActivity(intent);
            }
        });

        List<BookCart> bookCartList = BookDetailActivity.bookCartList;
        double totalPrice = 0.0;
        for (int i = 0; i < bookCartList.size();i++){
            totalPrice += bookCartList.get(i).getBook().getPrice()*bookCartList.get(i).getQuantity();
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.home_recycler_cart);
        CaptionedBookAdapter captionedImagesAdapter = new CaptionedBookAdapter(this,bookCartList);
        recyclerView.setAdapter(captionedImagesAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        TextView totalPriceTextView = (TextView) findViewById(R.id.total_price);
        totalPriceTextView.setText(""+ totalPrice+" $");
        Button orderButton = (Button) findViewById(R.id.order_button);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bookCartList.isEmpty()){
                    Toast.makeText(CartActivity.this,"Please choose the book to order",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(CartActivity.this,OrderActivity.class);
                    startActivity(intent);
                }
            }
        });

        captionedImagesAdapter.setListener(new CaptionedBookAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getContext(),BookDetailActivity.class);
                intent.putExtra(BookDetailActivity.BOOK_POSITION,position);
                intent.putExtra(BookDetailActivity.CHECK,3);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onStop() {
        if (CaptionedBookAdapter.dialogProcess != null){
            CaptionedBookAdapter.dialogProcess.dismiss();
            CaptionedBookAdapter.dialogProcess = null;
        }

        super.onStop();
    }

    public Context getContext(){
        return this;
    }

}