package com.qsa.metallurgy_material_engineering_premium.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.qsa.metallurgy_material_engineering_premium.Model.Books;
import com.qsa.metallurgy_material_engineering_premium.PdfDetail;
import com.qsa.metallurgy_material_engineering_premium.R;

import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.holder> {

    Context context;
    List<Books> dataList;

    public BooksAdapter(Context context, List<Books> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.image_icon_recycler, parent, false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, final int position) {
        final Books books = dataList.get(getItemCount() - position - 1);

        Glide.with(context)
                .load(books.getBookImage())
                .into(holder.cardImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PdfDetail.class);
                i.putExtra("bookImage", books.getBookImage());
                i.putExtra("bookName", books.getBookName());
                i.putExtra("authorName", books.getAuthorName());
                i.putExtra("bookCategory", books.getCategoryName());
                i.putExtra("description", books.getDescription());
                i.putExtra("pdfUrl", books.getPdfUrl());
                i.putExtra("id", books.getId());
                i.putExtra("bookDownloads", books.getDownloads());
                i.putExtra("youtubeUrl", books.getYoutubeUrl());
                i.putExtra("type","normal");
                context.startActivity(i);
                Log.e("TAGDES", "onClick: " + books.getDescription());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class holder extends RecyclerView.ViewHolder {

        ImageView cardImage;

        public holder(@NonNull View itemView) {
            super(itemView);

            cardImage = itemView.findViewById(R.id.card_image);

        }
    }
}
