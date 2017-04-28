package ua.nure.model;

import javax.persistence.*;

@Entity
public class Invite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long fromUserId;

    @ManyToOne
    @JoinColumn(name = "to_user_id")
    private AppUser toUser;

    public Invite() {
    }

    public Invite(long fromUserId, AppUser toUser) {
        this.fromUserId = fromUserId;
        this.toUser = toUser;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public AppUser getToUser() {
        return toUser;
    }

    public void setToUser(AppUser toUser) {
        this.toUser = toUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Invite invite = (Invite) o;

        if (id != invite.id) return false;
        if (fromUserId != invite.fromUserId) return false;
        return toUser != null ? toUser.equals(invite.toUser) : invite.toUser == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (fromUserId ^ (fromUserId >>> 32));
        result = 31 * result + (toUser != null ? toUser.hashCode() : 0);
        return result;
    }
}
