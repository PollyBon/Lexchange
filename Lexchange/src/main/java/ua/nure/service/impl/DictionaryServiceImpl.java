package ua.nure.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.dao.DictionaryDao;
import ua.nure.model.Dictionary;
import ua.nure.service.DictionaryService;

import java.util.List;

@Service("dictionaryService")
@Transactional
public class DictionaryServiceImpl implements DictionaryService {

    @Autowired
    private DictionaryDao dao;

    public void createDictionary(Dictionary dictionary) {
        dao.createDictionary(dictionary);
    }

    public List<Dictionary> findDictionariesByUserId(long userId) {
        return dao.findDictionariesByUserId(userId);
    }

    public Dictionary findDictionaryById(long dictionaryId) {
        return dao.findDictionaryById(dictionaryId);
    }

}
