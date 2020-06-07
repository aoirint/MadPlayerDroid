package com.github.aoirint.madplayer;

import androidx.documentfile.provider.DocumentFile;

public interface MediaPlayManagerDelegate {
    void onImageUpdate(DocumentFile imageFile);
    void onMusicUpdate(DocumentFile musicFile);

}
