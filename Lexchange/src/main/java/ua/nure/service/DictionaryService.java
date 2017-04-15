package ua.nure.service;

import ua.nure.model.Dictionary;

import java.util.List;

public interface DictionaryService {

    void createDictionary (Dictionary dictionary);

    List<Dictionary> findDictionariesByUserId(long userId);

    Dictionary findDictionaryById(long dictionaryId);
}
