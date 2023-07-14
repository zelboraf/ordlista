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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@SessionAttributes("searchString, dictionaryLang, autoRefresh")
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
    @ModelAttribute("dictionaryLang")
    public String setUpDictionaryLang() { return "SE"; }
    @ModelAttribute("autoRefresh")
    public Boolean setUpAutoRefresh() { return Boolean.TRUE; }

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
            if (dictionaryLang.equals("SE")) {
                dictionary.setSwedishWord(searchString);
            } else {
                dictionary.setPolishWord(searchString);
            }
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

        String[] splitSwedishWord = dictionary.getSwedishWord().split("\\s+");
        String searchString = splitSwedishWord[0].replace("|", "");

        redirectAttributes.addFlashAttribute("searchString", searchString);
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

    // QUIZ
    @GetMapping("/quiz")
    public String getQuizView(Model model) {
        long entriesCount = Long.parseLong(dictionaryService.countAll());

        Dictionary question = getRandomDictionary(entriesCount);
        model.addAttribute("question", question);

        List<Dictionary> answers = new ArrayList<>();
        answers.add(question);
        for (int x = 0; x < 4; x++){
            answers.add(getRandomDictionary(entriesCount));
        }
        Collections.shuffle(answers);
        model.addAttribute("answers", answers);
        return "quiz";
    }

    @PostMapping("/quiz")
    public String postQuizView(Model model) {
        return "quiz";
    }



    //
    // METHODS
    //
    private void setHomeView(Model model, String searchString, String dictionaryLang) {
        if (!searchString.isEmpty()) {
            log.info("searching for >" + searchString + "<");

            List<Dictionary> dictionaryContaining;
            List<Dictionary> dictionaryStartingWith;
            if (dictionaryLang.equals("SE")) {
                dictionaryContaining = dictionaryService.findAllSwedishContaining(searchString);
                dictionaryStartingWith = dictionaryContaining
                        .stream()
                        .filter(c -> c.getSwedishWord().startsWith(searchString))
                        .collect(Collectors.toList());
            } else {
                dictionaryContaining = dictionaryService.findAllPolishContaining(searchString);
                dictionaryStartingWith = dictionaryContaining
                        .stream()
                        .filter(c -> c.getPolishWord().startsWith(searchString))
                        .collect(Collectors.toList());
            }
            dictionaryContaining.removeAll(dictionaryStartingWith);

            model.addAttribute("searchString", searchString);
            model.addAttribute("dictionaryLang", dictionaryLang);
            model.addAttribute("dictionaryStartingWith", dictionaryStartingWith);
            model.addAttribute("dictionaryContaining", dictionaryContaining);
        }
    }

    private Dictionary getRandomDictionary(Long maxRandom) {
        long randomId;
        Dictionary dictionary = null;

        while (dictionary == null) {
            randomId = (long) (Math.random() * maxRandom + 1);
            dictionary = dictionaryService.fetchDictionaryById(randomId);
        }
        return dictionary;
    }

}
