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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@SessionAttributes("searchString")
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
    public String setUpSearchString() { return new String(""); }
    @ModelAttribute("dictionaryLang")
    public String setUpDictionaryLang() { return new String("SE"); }

    // REDIRECT
    @GetMapping("/")
    public String getRoot() {
        return "redirect:/home";
    }

    // HOME VIEW
    @GetMapping("/home")
    public String getHomeView(Model model,
                              @ModelAttribute("searchString") String searchString,
                              @ModelAttribute("dictionaryLang") String dictionaryLang,
                              @ModelAttribute("message") String message) {
        log.info("GET");
        setHomeView(model, searchString, dictionaryLang);
        model.addAttribute("message", message);
        return "/home";
    }

    @PostMapping("/home")
    public String postHomeView(Model model,
                               @RequestParam String searchString,
                               @RequestParam String dictionaryLang,
                               @RequestParam(required = false) String create) {
        log.info("POST");
        if (create != null) {
            Dictionary dictionary = new Dictionary();
            setDictionaryLang(searchString, dictionaryLang, dictionary);
            model.addAttribute(dictionary);
            return "edit";
        }
        setHomeView(model, searchString, dictionaryLang);
        return "/home";
    }

    // EDIT
    @GetMapping("/edit/{id}")
    public String getEditView(Model model, @PathVariable Long id) {
        Dictionary dictionary = dictionaryService.fetchDictionaryById(id);
        model.addAttribute("dictionary", dictionary);
        return "edit";
    }

    @PostMapping("/edit")
    public String postEditView(Model model,
                               @ModelAttribute Dictionary dictionary,
                               @RequestParam(required = false) String cancel,
                               RedirectAttributes redirectAttributes) {
        if (cancel != null) {
            return "redirect:/home";
        }
        // TODO: validate input before save
        dictionaryService.saveDictionary(dictionary);
        updateSearchString(model, dictionary);
        redirectAttributes.addFlashAttribute("message", "updated");
        return "redirect:/home";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String getDeleteView(@PathVariable Long id, Model model, String searchString) {
        // TODO: delete confirmation dialog
        dictionaryService.deleteEntryById(id);
        model.addAttribute("searchString", searchString);
        model.addAttribute("message", "deleted");
        return "redirect:/home";
    }

    // METHODS

    private void setHomeView(Model model, String searchString, String dictionaryLang) {
        if (!searchString.equals("")) {
            log.info("searching for >" + searchString + "<");
            List<Dictionary> dictionaryStartingWith = getDictionaryStartingWith(searchString, dictionaryLang);
            List<Dictionary> dictionaryContaining = getDictionaryContaining(searchString, dictionaryLang);
            dictionaryContaining.removeAll(dictionaryStartingWith);
            addModelAttributes(model, dictionaryStartingWith, dictionaryContaining, dictionaryLang, searchString);
        }
    }

    private List<Dictionary> getDictionaryStartingWith(String searchString, String dictionaryLang) {
        List<Dictionary> dictionary;
        if (dictionaryLang.equals("SE")) {
            dictionary = dictionaryService.findAllSwedishStartingWith(searchString);
        } else {
            dictionary = dictionaryService.findAllPolishStartingWith(searchString);
        }
        return dictionary;
    }

    private List<Dictionary> getDictionaryContaining(String searchString, String dictionaryLang) {
        List<Dictionary> dictionary;
        if (dictionaryLang.equals("SE")) {
            dictionary = dictionaryService.findAllSwedishContaining(searchString);
        } else {
            dictionary = dictionaryService.findAllPolishContaining(searchString);
        }
        return dictionary;
    }

    private void addModelAttributes(Model model, List<Dictionary> dictionaryStartingWith, List<Dictionary> dictionaryContaining, String dictionaryLang, String searchString) {
        model.addAttribute("dictionaryStartingWith", dictionaryStartingWith);
        model.addAttribute("dictionaryContaining", dictionaryContaining);
        model.addAttribute("dictionaryLang", dictionaryLang);
        model.addAttribute("searchString", searchString);
    }

    private void setDictionaryLang(String searchString, String dictionaryLang, Dictionary dictionary) {
        if (dictionaryLang.equals("SE")) {
            dictionary.setSwedishWord(searchString);
        } else {
            dictionary.setPolishWord(searchString);
        }
    }

    private void updateSearchString(Model model, Dictionary dictionary) {
        String[] splitSwedishWord = dictionary.getSwedishWord().split("\\s+");
        String searchString = splitSwedishWord[0].replace("|", "");
        model.addAttribute("searchString", searchString);
    }

}
