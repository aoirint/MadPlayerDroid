package com.github.aoirint.madplayer;

import android.content.Context;

import androidx.documentfile.provider.DocumentFile;

import java.util.List;

public class MediaPlayManager implements Runnable {
    private Context context;
    private Thread mediaThread;
    private volatile boolean isRunning = true;
    private volatile boolean isDead = false;

    private volatile List<DocumentFile> imageFiles;
    private volatile List<DocumentFile> musicFiles;
    private volatile List<DocumentFile> nextImageFiles;
    private volatile List<DocumentFile> nextMusicFiles;

    private volatile int imageIndex;
    private volatile int musicIndex;
    private volatile long imagePlayStartedAt;
    private volatile long imagePlayIntervalMillis = 3000;

    public volatile MediaPlayManagerDelegate delegate;


    public MediaPlayManager(Context context) {
        this.context = context;
        this.mediaThread = new Thread(this, "MediaPlayManager");
        this.mediaThread.start();
    }

    @Override
    public void run() {
        while (isRunning) {
            if (nextImageFiles != null) {
                imageFiles = nextImageFiles;
                nextImageFiles = null;
                setNextImage(0);
            }
            if (nextMusicFiles != null) {
                musicFiles = nextMusicFiles;
                nextMusicFiles = null;
                setNextMusic(0);
            }

            if (imageFiles != null && imageFiles.size() > 0) {
                if (System.currentTimeMillis() - imagePlayStartedAt > imagePlayIntervalMillis) {
                    goNextImage();
                }
            }

            try { mediaThread.sleep(10);  }
            catch (InterruptedException e) {  }
        }

        isDead = true;
    }

    public void updateImageFiles(List<DocumentFile> imageFiles) {
        this.nextImageFiles = imageFiles;
    }
    public void updateMusicFiles(List<DocumentFile> musicFiles) {
        this.nextMusicFiles = musicFiles;
    }


    public void setNextImage(int nextImageIndex) {
        if (imageFiles.size() <= nextImageIndex) nextImageIndex = 0;

        this.imageIndex = nextImageIndex;
        if (delegate != null) delegate.onImageUpdate(imageFiles.get(this.imageIndex));
        imagePlayStartedAt = System.currentTimeMillis();
    }

    public void goNextImage() {
        setNextImage(imageIndex + 1);
    }

    public void setNextMusic(int nextMusicIndex) {
        if (musicFiles.size() <= nextMusicIndex) nextMusicIndex = 0;

        this.musicIndex = nextMusicIndex;
        if (delegate != null) delegate.onMusicUpdate(musicFiles.get(this.musicIndex));
    }
    public void goNextMusic() {
        setNextMusic(musicIndex + 1);
    }

}
