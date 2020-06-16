package com.github.aoirint.madplayer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.VectorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MyAlbumActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_album);

        findViewById(R.id.backButton).setOnClickListener(view -> {
            finish();
        });

        findViewById(R.id.saveButton).setOnClickListener(view -> {
            saveAndFinish();
        });

        findViewById(R.id.coverButton).setOnClickListener(view -> {
            openCoverSelection();
        });

        findViewById(R.id.musicListButton).setOnClickListener(view -> {
            openMusicList();
        });

        findViewById(R.id.visualListButton).setOnClickListener(view -> {
            openVisualList();
        });

    }

    void saveAndFinish() {
        finish();
    }

    void openCoverSelection() {

    }

    void openMusicList() {
        VectorDrawable drawable = (VectorDrawable) getDrawable(R.drawable.ic_baseline_image_24);
        Bitmap icon = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(icon);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        Uri iconUri = null;
        try {
            File file = File.createTempFile("dummy", ".png", getCacheDir());
            iconUri = Uri.fromFile(file);

            FileOutputStream fos = new FileOutputStream(file);
            icon.compress(Bitmap.CompressFormat.PNG, 100, fos);

            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<IIconicItem> iconicItems = new ArrayList<>();
        for (int i=0; i<10; i++)
            iconicItems.add(new SimpleIconicItem(iconUri, "item " + i));

        Intent intent = new Intent(this, IconicListActivity.class);
        intent.putExtra("iconicItems", iconicItems);

        startActivity(intent);
    }

    void openVisualList() {

    }


}