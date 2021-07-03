package com.nui.nuibookstore;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.nui.nuibookstore.card_view.CaptionBookOrderAdapter;
import com.nui.nuibookstore.model.OrderInformation;

import org.w3c.dom.Text;

public class OrderInformationDetailActivity extends AppCompatActivity {
    public  static final String ORDER_INFORMATION  = "OrderInformation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_information_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        OrderInformation orderInformation = (OrderInformation) getIntent().getSerializableExtra(ORDER_INFORMATION);
        TextView nameTextView = (TextView) findViewById(R.id.name);
        TextView phoneTextView = (TextView) findViewById(R.id.phone);
        TextView addressTextView = (TextView) findViewById(R.id.address);
        TextView cityTextView = (TextView) findViewById(R.id.city);
        TextView stateTextView = (TextView) findViewById(R.id.state);
        nameTextView.setText(orderInformation.getName());
        phoneTextView.setText(orderInformation.getPhone());
        addressTextView.setText(orderInformation.getHomeAddress());
        cityTextView.setText(orderInformation.getCity());
        stateTextView.setText(R.string.processing_tab);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.order_recycler);
        CaptionBookOrderAdapter captionBookOrderAdapter = new CaptionBookOrderAdapter(orderInformation.getBookCarts());
        recyclerView.setAdapter(captionBookOrderAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

    }
}