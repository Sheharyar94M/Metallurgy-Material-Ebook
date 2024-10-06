package com.qsa.metallurgy_material_engineering_premium.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.qsa.metallurgy_material_engineering_premium.Booksdb;
import com.qsa.metallurgy_material_engineering_premium.R;
import com.qsa.metallurgy_material_engineering_premium.VideoPlayerActivity;

import java.util.ArrayList;

public class DownloadVideoAdapter extends RecyclerView.Adapter<DownloadVideoAdapter.MyVideoViewHolder>
{
    Context context;
    ArrayList<Booksdb> dataList;

    public DownloadVideoAdapter(Context context, ArrayList<Booksdb> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyVideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.design_for_pdf_video, parent, false);
        return new MyVideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVideoViewHolder holder, int position) {

        final Booksdb books = dataList.get(position);
        Glide.with(context)
                .load(books.getBookImage())
                .into(holder.bookImage);
        holder.heading.setText(books.getBookName());
        holder.description.setText(books.getDescription());

        holder.videoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoPlayerActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("categoryName",books.getCategoryName());
                intent.putExtra("youtubeUrl", books.getYoutubeUrl());
                intent.putExtra("type", "download");
                context.startActivity(intent);
            }
        });

        holder.playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, VideoPlayerActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("categoryName",books.getCategoryName());
                intent.putExtra("youtubeUrl", books.getYoutubeUrl());
                intent.putExtra("type", "download");
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyVideoViewHolder extends RecyclerView.ViewHolder
    {
        CardView videoLayout;
        ImageView playBtn;
        ImageView bookImage;
        TextView heading;
        TextView description;

        public MyVideoViewHolder(@NonNull View itemView) {
            super(itemView);

            videoLayout = itemView.findViewById(R.id.videoLayout);
            playBtn = itemView.findViewById(R.id.favBtn);
            heading = itemView.findViewById(R.id.text1);
            description = itemView.findViewById(R.id.text2);
            bookImage = itemView.findViewById(R.id.bookImageFavourite);
        }
    }
}
