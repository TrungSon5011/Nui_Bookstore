package com.nui.nuibookstore;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nui.nuibookstore.model.BookCart;
import com.nui.nuibookstore.model.OrderInformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OrderProcessingFragment extends Fragment {

    private List<HashMap<String, Object>> orderInformationProcessing = new ArrayList<>();
    private ProgressDialog loadingBar;
    private FirebaseAuth firebaseAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = (LinearLayout) inflater.inflate(R.layout.fragment_order_processing, container, false);
        ListView listView = (ListView) view.findViewById(R.id.list_view);
        firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getCurrentUser().getUid();
        loadingBar = new ProgressDialog(getActivity());
        loadingBar.setTitle("Order Information");
        loadingBar.setMessage("Please wait...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    Map<String, OrderInformation> stringDate = (Map<String, OrderInformation>) task.getResult().child("Orders").child(uid).getValue();
                    if (stringDate == null) {
                        Toast.makeText(getContext(), "You haven't ordered yet", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), CartActivity.class);
                        startActivity(intent);
                        ((Activity) getActivity()).overridePendingTransition(0, 0);

                    } else {
                        System.out.println(stringDate);
                        List<String> dateKeys = new ArrayList<>();
                        for (Map.Entry<String, OrderInformation> pair : stringDate.entrySet()) {
                            dateKeys.add(pair.getKey());
                        }
                        orderInformationProcessing.clear();
                        System.out.println(dateKeys);
                        for (int i = 0; i < dateKeys.size(); i++) {
                            OrderInformation orderInformation = task.getResult().child("Orders")
                                    .child(uid)
                                    .child(dateKeys.get(i)).getValue(OrderInformation.class);
                            if (orderInformation.getState().equals("not shipped")) {
                                orderInformationProcessing.add(setHashMapOrder(orderInformation));
                            }
                        }
                        loadingBar.dismiss();
                        ListAdapter adapter = new SimpleAdapter(
                                getContext(),
                                orderInformationProcessing,
                                R.layout.list_view,
                                new String[]{"date", "totalPrice"},
                                new int[]{R.id.date_list_view, R.id.total_price_list_view}
                        );
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                HashMap<String, Object> object = (HashMap<String, Object>) parent.getAdapter().getItem(position);
                                List<BookCart> bookCarts = (List<BookCart>) object.get("bookCarts");
                                System.out.println(object);
                                Double totalPrice = (Double) object.get("totalPrice");
                                OrderInformation orderInformation = new OrderInformation(
                                        object.get("name").toString(),
                                        object.get("phone").toString(),
                                        object.get("homeAddress").toString(),
                                        object.get("city").toString(),
                                        object.get("state").toString(),
                                        bookCarts,
                                        totalPrice,
                                        object.get("date").toString()
                                );
                                Intent intent = new Intent(getActivity(), OrderInformationDetailActivity.class);
                                intent.putExtra(OrderInformationDetailActivity.ORDER_INFORMATION, orderInformation);
                                startActivity(intent);
                                ((Activity) getActivity()).overridePendingTransition(0, 0);
                            }
                        });
                    }
                } else {
                    Log.d("OrderProcessingFragment", "Error getting data " + task);
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private HashMap<String, Object> setHashMapOrder(OrderInformation orderInformation) {
        HashMap<String, Object> orderHashMap = new HashMap<>();
        orderHashMap.put("name", orderInformation.getName());
        orderHashMap.put("phone", orderInformation.getPhone());
        orderHashMap.put("homeAddress", orderInformation.getHomeAddress());
        orderHashMap.put("city", orderInformation.getCity());
        orderHashMap.put("state", orderInformation.getState());
        orderHashMap.put("bookCarts", orderInformation.getBookCarts());
        orderHashMap.put("totalPrice", orderInformation.getTotalPrice());
        orderHashMap.put("date", orderInformation.getDate());
        return orderHashMap;
    }
}
