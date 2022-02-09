package com.ellara.ordlista.service;

import com.ellara.ordlista.entity.Dictionary;

import java.util.List;

public interface DictionaryService {

    Dictionary saveDictionary(Dictionary dictionary);

    List<Dictionary> fetchDictionaryList();

    Dictionary updateDictionary(Dictionary dictionary, Long entryId);

    void deleteEntryById(Long entryId);
}
