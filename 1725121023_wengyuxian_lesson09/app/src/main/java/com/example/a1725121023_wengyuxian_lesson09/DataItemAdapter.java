package com.example.a1725121023_wengyuxian_lesson09;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DataItemAdapter extends RecyclerView.Adapter<DataItemAdapter.MyViewHolder> {
    private List<DataItem> mDataItemList;

    public DataItemAdapter(List<DataItem> dataItemList) {
        mDataItemList = dataItemList;
    }

    @Override
    public DataItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.data_item_view, parent, false);
        final MyViewHolder vh = new MyViewHolder(v);
        vh.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int postition = vh.getAdapterPosition();
                DataItem dataItem = mDataItemList.get(postition);
                if (dataItem.getFavorite() == false) {
                    dataItem.setFavorite(true);
                } else {
                    dataItem.setFavorite(false);
                }
                mDataItemList.set(postition, dataItem);
                notifyItemChanged(postition);
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DataItem dataItem = mDataItemList.get(position);
        holder.textView.setText(dataItem.getName());
        if (dataItem.getFavorite() == false) {
            holder.imageView.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        } else {
            holder.imageView.setImageResource(R.drawable.ic_favorite_black_24dp);
        }
    }

    @Override
    public int getItemCount() {
        return mDataItemList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;

        public MyViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.textView);
            imageView = v.findViewById(R.id.imageView);
        }
    }


}
