package com.nui.nuibookstore.card_view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nui.nuibookstore.BookDetailActivity;
import com.nui.nuibookstore.CartActivity;
import com.nui.nuibookstore.R;
import com.nui.nuibookstore.model.Book;
import com.nui.nuibookstore.model.BookCart;

import java.util.List;

public class CaptionedBookAdapter  extends RecyclerView.Adapter<CaptionedBookAdapter.ViewHolder> {
    private CartActivity cartActivity;
    private List<BookCart> bookCartList;
    public static DialogInterface dialogProcess;

    private Listener listener;
    public interface Listener{
        void onClick(int position);
    }

    public CaptionedBookAdapter(CartActivity cartActivity,List<BookCart> bookCartList) {
        this.cartActivity = cartActivity;
        this.bookCartList = bookCartList;
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;

        public ViewHolder(@NonNull CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }


    @NonNull
    @Override
    public CaptionedBookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView)
                LayoutInflater.from(parent.getContext()).inflate(R.layout.card_captioned_image_cart,parent,false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull CaptionedBookAdapter.ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView) cardView.findViewById(R.id.book_image_cart);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(bookCartList.get(position).getBook().getImageUrl());
        Glide.with(cartActivity)
                .load(storageReference)
                .into(imageView);
//        Drawable drawable = ContextCompat.getDrawable(cardView.getContext(),bookCartList.get(position).getBook().getPictureResourceId());
//        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(bookCartList.get(position).getBook().getName());
        TextView textViewName = (TextView) cardView.findViewById(R.id.book_name_cart);
        textViewName.setText(bookCartList.get(position).getBook().getName());
        TextView textViewAuthor = (TextView) cardView.findViewById(R.id.book_author_cart);
        textViewAuthor.setText(bookCartList.get(position).getBook().getAuthor());
        TextView textViewPrice = (TextView) cardView.findViewById(R.id.book_price_cart);
        textViewPrice.setText(String.valueOf(bookCartList.get(position).getBook().getPrice()));
        Button buttonDelete = (Button) cardView.findViewById(R.id.button_delete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookDetailActivity.bookCartList.remove(position);
                cartActivity.startActivity(cartActivity.getIntent());
                cartActivity.finish();
                cartActivity.overridePendingTransition(0,0);
            }
        });
        Button buttonDecrease = (Button) cardView.findViewById(R.id.button_decrease);
        buttonDecrease.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if (BookDetailActivity.bookCartList.get(position).getQuantity() > 1){
                    BookDetailActivity.bookCartList.get(position).setQuantity(BookDetailActivity.bookCartList.get(position).getQuantity()-1);
                }else {
                    BookDetailActivity.bookCartList.remove(position);
                }
                cartActivity.startActivity(cartActivity.getIntent());
                cartActivity.finish();
                cartActivity.overridePendingTransition(0,0);

            }
        });
        Button buttonIncrease = (Button) cardView.findViewById(R.id.button_increase);
        buttonIncrease.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                BookDetailActivity.bookCartList.get(position).setQuantity(BookDetailActivity.bookCartList.get(position).getQuantity()+1);
                cartActivity.startActivity(cartActivity.getIntent());
                cartActivity.finish();
                cartActivity.overridePendingTransition(0,0);
            }
        });
        TextView quantityTextView = (TextView) cardView.findViewById(R.id.quantity_cart);
        quantityTextView.setText(String.valueOf(bookCartList.get(position).getQuantity()));
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onClick(position);
                }
            }
        });

    }
    @Override
    public int getItemCount() {
        return BookDetailActivity.bookCartList.size();
    }
}
