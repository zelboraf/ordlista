package com.ellara.ordlista.service;

import com.ellara.ordlista.entity.Dictionary;

import java.util.List;

public interface DictionaryService {

    List<Dictionary> findAllSwedishContaining(String searchString);

    List<Dictionary> findAllPolishContaining(String searchString);

    Dictionary fetchDictionaryById(Long id);

    void deleteEntryById(Long id);

    void saveDictionary(Dictionary dictionary);

    String countAll();
}
