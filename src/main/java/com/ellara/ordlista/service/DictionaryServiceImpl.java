package com.ellara.ordlista.service;

import com.ellara.ordlista.entity.Dictionary;
import com.ellara.ordlista.repository.DictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictionaryServiceImpl implements DictionaryService {

    @Autowired
    private DictionaryRepository dictionaryRepository;

    @Override
    public List<Dictionary> findAllContainingSwedish(String searchString) {
        return dictionaryRepository.findAllContainingSwedish(searchString);
    }

    @Override
    public List<Dictionary> findAllContainingPolish(String searchString) {
        return dictionaryRepository.findAllContainingPolish(searchString);
    }

    @Override
    public Dictionary fetchDictionaryById(Long id) {
        return dictionaryRepository.getById(id);
    }

    @Override
    public void deleteEntryById(Long id) {
        dictionaryRepository.deleteById(id);
    }

    @Override
    public void saveDictionary(Dictionary dictionary) {
        dictionaryRepository.save(dictionary);
    }

    @Override
    public Dictionary updateDictionary(Dictionary dictionary, Long dictionaryId) {
        Dictionary dictDB = dictionaryRepository.findById(dictionaryId).get();

        // TODO: update conditions here

        return dictionaryRepository.save(dictDB);
    }

    @Override
    public String countAll() {
        return dictionaryRepository.countAll();
    }
}
