package ua.nure.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "dictionary_id")
    private Dictionary dictionary;

    @NotEmpty
    private String value;

    @NotEmpty
    private String translation;

    @NotEmpty
    private String comment;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word = (Word) o;

        if (id != word.id) return false;
        if (dictionary.getId() != word.dictionary.getId()) return false;
        if (value != null ? !value.equals(word.value) : word.value != null) return false;
        if (translation != null ? !translation.equals(word.translation) : word.translation != null) return false;
        return comment != null ? comment.equals(word.comment) : word.comment == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (dictionary.getId() ^ (dictionary.getId() >>> 32));
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (translation != null ? translation.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", dictionaryId=" + dictionary.getId() +
                ", value='" + value + '\'' +
                ", translation='" + translation + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
