package com.github.aoirint.madplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class IconicListAdapter<T extends IIconicItem> extends RecyclerView.Adapter<IconicListViewHolder> {
    Context context;
    List<T> iconicItems;

    public IconicListAdapter(@NonNull Context context, @NonNull List<T> iconicItems) {
        this.context = context;
        this.iconicItems = iconicItems;
    }

    @NonNull
    @Override
    public IconicListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_iconic_list_item, parent, false);
        IconicListViewHolder viewHolder = new IconicListViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IconicListViewHolder holder, int position) {
        T iconicItem = iconicItems.get(position);

        Uri iconUri = iconicItem.getItemIconUri();
        Bitmap icon = null;
        try {
            InputStream is = context.getContentResolver().openInputStream(iconUri);
            icon = BitmapFactory.decodeStream(is);

            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.imageView.setImageBitmap(icon);
        holder.textView.setText(iconicItem.getItemText());
    }

    @Override
    public int getItemCount() {
        return this.iconicItems.size();
    }

}
