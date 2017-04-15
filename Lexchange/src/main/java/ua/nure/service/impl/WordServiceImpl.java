package ua.nure.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.dao.WordDao;
import ua.nure.model.Word;
import ua.nure.service.WordService;

import java.util.List;

@Service("wordService")
@Transactional
public class WordServiceImpl implements WordService {

    @Autowired
    private WordDao dao;

    public void createWord(Word word) {
        dao.createWord(word);
    }

    public void deleteWordByValue(long dictionaryId, String word) {
        dao.deleteWordByValue(dictionaryId, word);
    }

    public List<Word> findAllWords() {
        return dao.findAllWords();
    }

    public Word findWordById(long wordId) {
        return dao.findWordById(wordId);
    }
}
