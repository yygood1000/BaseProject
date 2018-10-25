package com.yangy.baseproject.bean.extra;

import android.os.Parcel;
import android.os.Parcelable;

public class SecondActivityExtra implements Parcelable {
    private String TAG;
    private SimpleExtra mSimpleExtra;

    public SecondActivityExtra(String TAG, SimpleExtra simpleExtra) {
        this.TAG = TAG;
        mSimpleExtra = simpleExtra;
    }

    public String getTAG() {
        return TAG;
    }

    public void setTAG(String TAG) {
        this.TAG = TAG;
    }

    public SimpleExtra getSimpleExtra() {
        return mSimpleExtra;
    }

    public void setSimpleExtra(SimpleExtra simpleExtra) {
        mSimpleExtra = simpleExtra;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.TAG);
        dest.writeParcelable(this.mSimpleExtra, flags);
    }

    protected SecondActivityExtra(Parcel in) {
        this.TAG = in.readString();
        this.mSimpleExtra = in.readParcelable(SimpleExtra.class.getClassLoader());
    }

    public static final Creator<SecondActivityExtra> CREATOR = new Creator<SecondActivityExtra>() {
        @Override
        public SecondActivityExtra createFromParcel(Parcel source) {
            return new SecondActivityExtra(source);
        }

        @Override
        public SecondActivityExtra[] newArray(int size) {
            return new SecondActivityExtra[size];
        }
    };
}
