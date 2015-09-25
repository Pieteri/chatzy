package org.httpinghelram.socketchat;

/**
 * Created by pinghelram on 25/09/15.
 */
public class ChatData {
    boolean isOwnText;
    String content, username;

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
}
