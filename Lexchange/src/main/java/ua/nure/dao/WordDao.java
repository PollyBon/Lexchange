package ua.nure.dao;

import ua.nure.model.Word;

import java.util.List;

public interface WordDao {

    void createWord(Word word);

    void deleteWordByValue(long dictionaryId, String word);

    List<Word> findAllWords();

    Word findWordById(long wordId);
}
