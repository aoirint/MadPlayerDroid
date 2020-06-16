package com.github.aoirint.madplayer;

import android.net.Uri;
import android.os.Parcelable;

public interface IIconicItem extends Parcelable {
    Uri getItemIconUri();
    String getItemText();

}
