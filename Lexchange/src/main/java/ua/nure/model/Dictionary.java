package ua.nure.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Dictionary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @NotEmpty
    private String language;

    @OneToMany(mappedBy = "dictionary", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Word> words;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    public String getWordsAsJSON() {
        JSONArray array = new JSONArray();
        for (Word word : words) {
            JSONObject object = new JSONObject();
            object.put("id", word.getId());
            object.put("value", word.getValue());
            object.put("translation", word.getTranslation());
            object.put("comment", word.getComment());
            object.put("dictionaryId", word.getDictionary().getId());
            array.add(object);
        }
        return array.toJSONString().replaceAll("\"", "&quot;");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dictionary that = (Dictionary) o;

        if (id != that.id) return false;
        if (user.getId() != that.user.getId()) return false;
        return language != null ? language.equals(that.language) : that.language == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (user.getId() ^ (user.getId() >>> 32));
        result = 31 * result + (language != null ? language.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Dictionary{" +
                "id=" + id +
                ", userId=" + user.getId() +
                ", language='" + language + '\'' +
                '}';
    }
}
