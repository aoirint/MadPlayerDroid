package com.github.aoirint.madplayer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;

import com.github.aoirint.madplayer.util.ContentUriUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PlayerActivity extends AppCompatActivity implements MediaPlayManagerDelegate, PopupMenu.OnMenuItemClickListener {
    private static final int REQUEST_SELECT_IMAGE = 1000;
    private static final int REQUEST_SELECT_MUSIC = 1001;

    MediaPlayManager mediaPlayManager;
    MediaPlayer musicPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        findViewById(R.id.menuButton).setOnClickListener(view -> {
            openMenu();
        });

        mediaPlayManager = new MediaPlayManager(getApplicationContext());
        mediaPlayManager.delegate = this;
    }

    void openMenu() {
        ImageButton menuButton = findViewById(R.id.menuButton);
        PopupMenu popup = new PopupMenu(this, menuButton);
        popup.setOnMenuItemClickListener(this);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_player, popup.getMenu());

        popup.show();

        // Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        // startActivityForResult(intent, REQUEST_SELECT_IMAGE);
    }

    void openLibrary() {
        Intent intent = new Intent(this, LibraryActivity.class);

        startActivity(intent);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.open_queue:
                // TODO:
                return true;
            case R.id.open_library:
                openLibrary();
                return true;
        }

        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SELECT_IMAGE) {
                Uri uri = resultData.getData();
                List<DocumentFile> files = ContentUriUtil.getFileTree(this, uri);
                // TODO: filter (do in intent config?)
                mediaPlayManager.updateImageFiles(files);
            }

            if (requestCode == REQUEST_SELECT_MUSIC) {
                Uri uri = resultData.getData();
                List<DocumentFile> files = ContentUriUtil.getFileTree(this, uri);
                // TODO: filter (do in intent config?)
                mediaPlayManager.updateMusicFiles(files);
            }

        }

    }

    @Override
    public void onImageUpdate(DocumentFile imageFile) {
        Uri imageUri = imageFile.getUri();

        Bitmap bitmap = null;
        try {
            InputStream is = getContentResolver().openInputStream(imageUri);
            bitmap = BitmapFactory.decodeStream(is);

            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final Bitmap _bitmap = bitmap;
        runOnUiThread(() -> {
            ImageView mainImageView = findViewById(R.id.mainImageView);
            mainImageView.setImageBitmap(_bitmap);
        });

    }

    @Override
    public void onMusicUpdate(DocumentFile musicFile) {
        final Uri musicUri = musicFile.getUri();
        System.out.println(musicUri);

        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(this, musicUri);

        String title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        String album = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
        String artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        String detailText = String.format("%s - %s", artist, album);

        runOnUiThread(() -> {
            if (musicPlayer != null) {
                musicPlayer.setOnCompletionListener(null);
                musicPlayer.stop();
            }

            musicPlayer = MediaPlayer.create(this, musicUri);
            musicPlayer.setOnCompletionListener(mediaPlayer -> {
                mediaPlayManager.goNextMusic();
            });
            musicPlayer.start();

            TextView titleView = findViewById(R.id.musicTitleView);
            titleView.setText(title);

            TextView detailView = findViewById(R.id.musicDetailView);
            detailView.setText(detailText);

        });

    }

}