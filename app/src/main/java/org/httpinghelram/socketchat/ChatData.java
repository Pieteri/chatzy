package org.httpinghelram.socketchat;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pinghelram on 25/09/15.
 */
public class ChatData implements Parcelable {
    boolean isOwnText;
    String content, username;

    protected ChatData(Parcel in) {
        content = in.readString();
        username = in.readString();
    }

    public ChatData(boolean isOwnText, String content, String username){
        this.isOwnText = isOwnText;
        this.content = content;
        this.username = username;
    }

    public static final Creator<ChatData> CREATOR = new Creator<ChatData>() {
        @Override
        public ChatData createFromParcel(Parcel in) {
            return new ChatData(in);
        }

        @Override
        public ChatData[] newArray(int size) {
            return new ChatData[size];
        }
    };

    public boolean isOwnText() {
        return isOwnText;
    }

    public void setIsOwnText(boolean isOwnText) {
        this.isOwnText = isOwnText;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(content);
        dest.writeString(username);
    }
}
