package com.ellara.ordlista.service;

import com.ellara.ordlista.entity.Dictionary;
import com.ellara.ordlista.repository.DictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictionaryServiceImpl implements DictionaryService{

    @Autowired
    private DictionaryRepository dictionaryRepository;

    @Override
    public Dictionary saveDictionary(Dictionary dictionary) {
        return dictionaryRepository.save(dictionary);
    }

    @Override
    public List<Dictionary> fetchDictionaryList() {
        return (List<Dictionary>) dictionaryRepository.findAll();
    }

    @Override
    public Dictionary updateDictionary(Dictionary dictionary, Long dictionaryId) {
        Dictionary dictDB = dictionaryRepository.findById(dictionaryId).get();

        // TODO: update conditions here

        return dictionaryRepository.save(dictDB);
    }

    @Override
    public void deleteEntryById(Long dictionaryId) {
        dictionaryRepository.deleteById(dictionaryId);

    }
}
