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
    public List<Dictionary> findAllSwedishContaining(String searchString) {
        return dictionaryRepository.findAllSwedishContaining(searchString);
    }

    @Override
    public List<Dictionary> findAllPolishContaining(String searchString) {
        return dictionaryRepository.findAllPolishContaining(searchString);
    }

    @Override
    public Dictionary fetchDictionaryById(Long id) {
        return dictionaryRepository.getById(id);
    }

    @Override
    public List<Dictionary> fetchRandomDictionaries(int numberOfDictionaries) {
        return dictionaryRepository.getRandom(numberOfDictionaries);
    }

    @Override
    public List<String> fetchPartsOfSpeechList() {
        return dictionaryRepository.fetchPartsOfSpeechList();
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
    public String countAll() {
        return dictionaryRepository.countAll();
    }
}
