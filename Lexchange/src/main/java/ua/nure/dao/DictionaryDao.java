package ua.nure.dao;

import ua.nure.model.Dictionary;

import java.util.List;

public interface DictionaryDao {

    void createDictionary (Dictionary dictionary);

    Dictionary findDictionaryById(long dictionaryId);

    List<Dictionary> findDictionariesByUserId(long userId);
}
