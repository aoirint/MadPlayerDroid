package com.github.aoirint.madplayer;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class LibraryActivity extends AppCompatActivity {
    private static final int REQUEST_ADD_MY_ALBUM = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        findViewById(R.id.backButton).setOnClickListener(view -> {
            finish();
        });

        findViewById(R.id.addButton).setOnClickListener(view -> {
            openAddMyAlbum();
        });

    }

    void openAddMyAlbum() {
        Intent intent = new Intent(this, MyAlbumActivity.class);

        startActivityForResult(intent, REQUEST_ADD_MY_ALBUM);
    }

}