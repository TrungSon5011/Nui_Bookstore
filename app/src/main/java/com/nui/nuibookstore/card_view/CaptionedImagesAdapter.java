package com.nui.nuibookstore.card_view;

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

import com.nui.nuibookstore.R;
import com.nui.nuibookstore.model.Book;

import java.util.List;

public class CaptionedImagesAdapter extends RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder> {

    private List<Book> bookList;
    private Listener listener;
    public interface Listener{
        void onClick(int position);
    }
    public CaptionedImagesAdapter(List<Book> bookList){
        this.bookList = bookList;
    }
    public void setListener(Listener listener){
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;


        public ViewHolder(@NonNull CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }

    @NonNull
    @Override
    public CaptionedImagesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_captioned_image,parent,false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull CaptionedImagesAdapter.ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        addToCardView(cardView,position);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }
    private void addToCardView(CardView cardView, int position){
        ImageView imageView = (ImageView) cardView.findViewById(R.id.book_image);
        Drawable drawable = ContextCompat.getDrawable(cardView.getContext(),bookList.get(position).getPictureResourceId());
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(bookList.get(position).getName());
        TextView textViewName = (TextView) cardView.findViewById(R.id.book_name);
        textViewName.setText(bookList.get(position).getName());
        TextView textViewAuthor = (TextView) cardView.findViewById(R.id.book_author);
        textViewAuthor.setText(bookList.get(position).getAuthor());
        TextView textViewPrice = (TextView) cardView.findViewById(R.id.book_price);
        textViewPrice.setText(String.valueOf(bookList.get(position).getPrice()));
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onClick(position);
                }
            }
        });

    }
}
