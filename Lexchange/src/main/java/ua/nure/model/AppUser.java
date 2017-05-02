package ua.nure.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.springframework.format.annotation.DateTimeFormat;
import ua.nure.model.enumerated.Role;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    @Email
    @Column(unique = true)
    private String email;

    @NotEmpty
    @Size(min = 6, max = 30)
    private String password;

    @Transient
    private String rePassword;

    @NotEmpty
    @Size(min = 1, max = 50)
    private String firstName;

    @NotEmpty
    @Size(min = 1, max = 50)
    private String lastName;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Role role;

    @NotEmpty
    private String nativeLanguage;

    @NotEmpty
    private String country;

    private String url;

    private int religion;

    private int sport;

    private int music;

    private int games;

    private int politics;

    private int trips;

    private int art;

    private int science;

    private String interestedIn;

    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Invite> invites;

    @Transient
    private String[] interested;

    @ManyToMany(mappedBy = "users", cascade=CascadeType.ALL)
    @JsonIgnore
    private List<Chat> chats;

    private String approvementCode;

    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Dictionary> dictionaries;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public int getAge() {
        return Years.yearsBetween(getBirthDate(), new LocalDate()).getYears();
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getNativeLanguage() {
        return nativeLanguage;
    }

    public void setNativeLanguage(String nativeLanguage) {
        this.nativeLanguage = nativeLanguage;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getReligion() {
        return religion;
    }

    public void setReligion(int religion) {
        this.religion = religion;
    }

    public int getSport() {
        return sport;
    }

    public void setSport(int sport) {
        this.sport = sport;
    }

    public int getMusic() {
        return music;
    }

    public void setMusic(int music) {
        this.music = music;
    }

    public int getGames() {
        return games;
    }

    public void setGames(int games) {
        this.games = games;
    }

    public int getPolitics() {
        return politics;
    }

    public void setPolitics(int politics) {
        this.politics = politics;
    }

    public int getTrips() {
        return trips;
    }

    public void setTrips(int trips) {
        this.trips = trips;
    }

    public int getArt() {
        return art;
    }

    public void setArt(int art) {
        this.art = art;
    }

    public int getScience() {
        return science;
    }

    public void setScience(int science) {
        this.science = science;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public List<Chat> getChats() {
        return chats;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }

    public String getApprovementCode() {
        return approvementCode;
    }

    public void setApprovementCode(String approvementCode) {
        this.approvementCode = approvementCode;
    }

    public String getInterestedIn() {
        if(interestedIn == null && interested != null && interested.length != 0) {
            return interested[0] + ";" + interested[1] + ";" + interested[2];
        }
        return interestedIn;
    }

    public void setInterestedIn(String interestedIn) {
        this.interestedIn = interestedIn;
    }

    public String[] getInterested() {
        if(interested == null) {
            setInterested(new String [3]);
            if(interestedIn != null) {
                setInterested(interestedIn.split(";"));
            }
        }
        return interested;
    }

    public void setInterested(String[] interested) {
        this.interested = interested;
    }

    public List<Invite> getInvites() {
        if (invites == null) {
            setInvites(new ArrayList<>());
        }
        return invites;
    }

    public void setInvites(List<Invite> invites) {
        this.invites = invites;
    }

    public boolean haveInviteFrom(Long id) {
        return getInvites().stream()
                .anyMatch(i -> i.getFromUserId() == id);
    }

    public List<Dictionary> getDictionaries() {
        return dictionaries;
    }

    public void setDictionaries(List<Dictionary> dictionaries) {
        this.dictionaries = dictionaries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppUser appUser = (AppUser) o;

        if (id != appUser.id) return false;
        if (religion != appUser.religion) return false;
        if (sport != appUser.sport) return false;
        if (music != appUser.music) return false;
        if (games != appUser.games) return false;
        if (politics != appUser.politics) return false;
        if (trips != appUser.trips) return false;
        if (art != appUser.art) return false;
        if (science != appUser.science) return false;
        if (email != null ? !email.equals(appUser.email) : appUser.email != null) return false;
        if (password != null ? !password.equals(appUser.password) : appUser.password != null) return false;
        if (firstName != null ? !firstName.equals(appUser.firstName) : appUser.firstName != null) return false;
        if (lastName != null ? !lastName.equals(appUser.lastName) : appUser.lastName != null) return false;
        if (birthDate != null ? !birthDate.equals(appUser.birthDate) : appUser.birthDate != null) return false;
        if (role != null ? !role.equals(appUser.role) : appUser.role != null) return false;
        if (nativeLanguage != null ? !nativeLanguage.equals(appUser.nativeLanguage) : appUser.nativeLanguage != null)
            return false;
        if (country != null ? !country.equals(appUser.country) : appUser.country != null) return false;
        return url != null ? url.equals(appUser.url) : appUser.url == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (nativeLanguage != null ? nativeLanguage.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (chats != null ? chats.hashCode() : 0);
        result = 31 * result + religion;
        result = 31 * result + sport;
        result = 31 * result + music;
        result = 31 * result + games;
        result = 31 * result + politics;
        result = 31 * result + trips;
        result = 31 * result + art;
        result = 31 * result + science;
        return result;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", role=" + role +
                ", nativeLanguage='" + nativeLanguage + '\'' +
                ", country='" + country + '\'' +
                ", url='" + url + '\'' +
                ", religion=" + religion +
                ", sport=" + sport +
                ", music=" + music +
                ", games=" + games +
                ", politics=" + politics +
                ", trips=" + trips +
                ", art=" + art +
                ", science=" + science +
                '}';
    }
}
