package ua.nure.model;

import java.io.Serializable;

public class AppUserChat implements Serializable {

    private static final long serialVersionUID = -8172940343036502076L;

    private long chatId;
    private long appUserId;

    public AppUserChat(long chatId, long appUserId) {
        this.chatId = chatId;
        this.appUserId = appUserId;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public long getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(long appUserId) {
        this.appUserId = appUserId;
    }

    @Override
    public String toString() {
        return "AppUserChatBean{" +
                "chatId=" + chatId +
                ", appUserId=" + appUserId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppUserChat that = (AppUserChat) o;

        if (chatId != that.chatId) return false;
        return appUserId == that.appUserId;
    }

    @Override
    public int hashCode() {
        int result = (int) (chatId ^ (chatId >>> 32));
        result = 31 * result + (int) (appUserId ^ (appUserId >>> 32));
        return result;
    }
}
