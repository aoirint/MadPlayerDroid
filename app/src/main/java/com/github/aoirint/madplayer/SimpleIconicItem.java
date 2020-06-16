package com.github.aoirint.madplayer;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class SimpleIconicItem implements IIconicItem {
    private Uri iconUri; // TODO: directly use bitmap?
    private String text;

    public SimpleIconicItem(Uri iconUri, String text) {
        this.iconUri = iconUri;
        this.text = text;
    }

    @Override
    public Uri getItemIconUri() {
        return iconUri;
    }

    @Override
    public String getItemText() {
        return text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeParcelable(iconUri, flags);
        parcel.writeString(text);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator<SimpleIconicItem>() {
        @Override
        public SimpleIconicItem createFromParcel(Parcel parcel) {
            Uri iconUri = parcel.readParcelable(Uri.class.getClassLoader());
            String text = parcel.readString();

            return new SimpleIconicItem(iconUri, text);
        }

        @Override
        public SimpleIconicItem[] newArray(int size) {
            return new SimpleIconicItem[size];
        }
    };

}
