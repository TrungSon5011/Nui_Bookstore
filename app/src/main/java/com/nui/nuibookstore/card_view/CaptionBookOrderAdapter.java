package com.nui.nuibookstore.card_view;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nui.nuibookstore.R;
import com.nui.nuibookstore.model.BookCart;

import java.util.List;

public class CaptionBookOrderAdapter extends RecyclerView.Adapter<CaptionBookOrderAdapter.ViewHolder> {
    private List<BookCart> bookCartList;
    private Activity activity;
    public CaptionBookOrderAdapter(List<BookCart> bookCartList) {
        this.bookCartList = bookCartList;
    }

    public CaptionBookOrderAdapter(List<BookCart> bookCartList, Activity activity){
        this.bookCartList = bookCartList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CaptionBookOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_captioned_image_order,parent,false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull  CaptionBookOrderAdapter.ViewHolder holder, int position) {
        BookCart bookCart = bookCartList.get(position);
        holder.bind(bookCart);
    }

    @Override
    public int getItemCount() {
        return bookCartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        public ViewHolder(@NonNull CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }

        public void bind(BookCart bookCart){
            ImageView imageView = (ImageView) cardView.findViewById(R.id.image);
            TextView  nameTextView = (TextView) cardView.findViewById(R.id.name);
            TextView authorTextView = (TextView) cardView.findViewById(R.id.author);
            TextView priceQuantityTextView = (TextView) cardView.findViewById(R.id.price_quantity);
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(bookCart.getBook().getImageUrl());
            Glide.with(activity)
                    .load(storageReference)
                    .into(imageView);
            nameTextView.setText(bookCart.getBook().getName());
            authorTextView.setText(bookCart.getBook().getAuthor());
            priceQuantityTextView.setText("$"+bookCart.getBook().getPrice()+" x "+bookCart.getQuantity());

        }
    }
}
