package ua.nure.model;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    private String content;

    @NotNull
    @ManyToOne
    private AppUser user;

    @NotNull
    @ManyToOne
    private Chat chat;

    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyyy'T'HH:mm")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime sendingTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public LocalDateTime getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(LocalDateTime sendingTime) {
        this.sendingTime = sendingTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (id != message.id) return false;
        if (content != null ? !content.equals(message.content) : message.content != null) return false;
        if (user != null ? !user.equals(message.user) : message.user != null) return false;
        if (chat != null ? !chat.equals(message.chat) : message.chat != null) return false;
        return sendingTime != null ? sendingTime.equals(message.sendingTime) : message.sendingTime == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (chat != null ? chat.hashCode() : 0);
        result = 31 * result + (sendingTime != null ? sendingTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", user=" + user +
                ", chat=" + chat +
                ", sendingTime=" + sendingTime +
                '}';
    }
}
