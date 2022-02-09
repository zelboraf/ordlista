package com.ellara.ordlista.controller;

import com.ellara.ordlista.entity.Dictionary;
import com.ellara.ordlista.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class DictionaryController {

    @Autowired
    private DictionaryService dictionaryService;

    // SAVE
    @PostMapping("/dictionary")
    public Dictionary saveDictionary(@Valid @RequestBody Dictionary dictionary) {
        return dictionaryService.saveDictionary(dictionary);
    }

    // READ
    @GetMapping("/dictionary")
    public List<Dictionary> fetchDictionaryList() {
        return dictionaryService.fetchDictionaryList();
    }

    // UPDATE
    @PutMapping("/dictionary/{id}")
    public Dictionary updateDictionary(@RequestBody Dictionary dictionary, @PathVariable("id") Long dictionaryId) {
        return dictionaryService.updateDictionary(dictionary, dictionaryId);
    }

    // DELETE
    @DeleteMapping("/dictionary/{id}")
    public String deleteDictionaryById(@PathVariable("id") Long dictionaryId) {
        dictionaryService.deleteEntryById(dictionaryId);
        return "Deleted";
    }

}
