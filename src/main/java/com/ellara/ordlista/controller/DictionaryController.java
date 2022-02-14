package com.ellara.ordlista.controller;

import com.ellara.ordlista.entity.Dictionary;
import com.ellara.ordlista.service.DictionaryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@AllArgsConstructor
@SessionAttributes({"query", "response"})
public class DictionaryController {

    @Autowired
    private DictionaryService dictionaryService;

//    @ModelAttribute("query")
//    public String setUpQuery() { return "";};

//    @ModelAttribute("response")
//    public List<Dictionary> setUpResponse() {
//        return new List<Dictionary>;
//    }

    // REDIRECT
    @GetMapping("/")
    public String getRoot() {
        return "redirect:/dictionary";
    }

    // GET
    @GetMapping("/dictionary")
    public String getDictionary(Model model) {
        model.addAttribute("entryList", dictionaryService.fetchDictionaryList());
        return "dictionary";
    }

    // POST
    @PostMapping("/dictionary")
    public String saveDictionary(HttpSession httpSession,
                                     @Valid @RequestBody Dictionary dictionary) {
        dictionaryService.saveDictionary(dictionary);
        return "dictionary";
    }

//    // UPDATE
//    @PutMapping("/{id}")
//    public Dictionary updateDictionary(@RequestBody Dictionary dictionary, @PathVariable("id") Long dictionaryId) {
//        return dictionaryService.updateDictionary(dictionary, dictionaryId);
//    }
//
//    // DELETE
//    @DeleteMapping("/{id}")
//    public String deleteDictionaryById(@PathVariable("id") Long dictionaryId) {
//        dictionaryService.deleteEntryById(dictionaryId);
//        return "Deleted";
//    }

}
