package com.github.aoirint.madplayer;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class IconicListViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;
    public TextView textView;

    public IconicListViewHolder(@NonNull View itemView) {
        super(itemView);

        this.imageView = itemView.findViewById(R.id.imageView);
        this.textView = itemView.findViewById(R.id.textView);
    }

}
