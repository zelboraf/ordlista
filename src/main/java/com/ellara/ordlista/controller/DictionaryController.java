package com.ellara.ordlista.controller;

import com.ellara.ordlista.entity.Dictionary;
import com.ellara.ordlista.service.DictionaryService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class DictionaryController {

    private static final Logger log = LogManager.getLogger(DictionaryController.class);

    @Autowired
    private DictionaryService dictionaryService;

    @ModelAttribute("message")
    public String setUpMessage() {
        return dictionaryService.countAll() + " dictionary entries";
    }

    @ModelAttribute("searchString")
    public String setUpSearchString() { return ""; }

    @ModelAttribute("selectedDictionary")
    public String setUpDictionarySelector() { return "SE"; }

    // REDIRECT
    @GetMapping("/")
    public String getRoot() {
        return "redirect:/home";
    }

    // HOME VIEW

    // GET
    @GetMapping("/home")
    public String getHomeView(Model model) {
        return "home";
    }

    // POST
    @PostMapping("/home")
    public String postHomeView(Model model,
                               @RequestParam(required = false) String searchString,
                               @RequestParam String selectedDictionary,
                               @RequestParam(required = false) String create) {
        if (create != null) {
            Dictionary dictionary = new Dictionary();
            if (selectedDictionary.equals("SE")) {
                dictionary.setSwedishWord(searchString);
            } else {
                dictionary.setPolishWord(searchString);
            }
            model.addAttribute(dictionary);
            return "update";
        }
        if (!searchString.equals("")) {
            log.info("searching for >" + searchString + "<");
            List<Dictionary> dictionaries;
            if (selectedDictionary.equals("SE")) {
                dictionaries = dictionaryService.findAllContainingSwedish(searchString);
            } else {
                dictionaries = dictionaryService.findAllContainingPolish(searchString);
            }
            model.addAttribute("dictionaryList", dictionaries);
            model.addAttribute("selectedDictionary", selectedDictionary);
            model.addAttribute("searchString", searchString);
            model.addAttribute("message", "Found " + dictionaries.size() + " records.");
            return "/home";
        }
        return "home";
    }

    // UPDATE VIEW

    @GetMapping("/update")
    public String getCreateView(Model model,
                                Dictionary dictionary) {
        model.addAttribute("dictionary", dictionary);
        return "update";
    }

    @PostMapping("/update")
    public String postCreateView(Model model,
                                 @ModelAttribute Dictionary dictionary,
                                 @RequestParam(required = false) String cancel) {
        if (cancel != null) {
            return "redirect:/home";
        }
        // TODO: validate input before save
        dictionaryService.saveDictionary(dictionary);
        model.addAttribute("message", "Created new record.");
        return "/home";
    }

    // DELETE

    @GetMapping("/delete/{id}")
    public String getDeleteView(@PathVariable Long id, Model model) {
        // TODO: delete confirmation dialog
        dictionaryService.deleteEntryById(id);
        model.addAttribute("message", "Record deleted from dictionary.");
        return "/home";
    }
}
