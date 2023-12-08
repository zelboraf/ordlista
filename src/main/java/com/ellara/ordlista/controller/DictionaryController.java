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

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
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
    @ModelAttribute(value = "dictionaryLang")
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
    public String getHomeView(Model model, HttpSession session,
                              @ModelAttribute("searchString") String searchString,
                              @ModelAttribute("dictionaryLang") String dictionaryLang,
                              @ModelAttribute("message") String message) {
        log.info("GET");
        setHomeView(model, searchString, dictionaryLang);
        model.addAttribute("message", message);
        session.setAttribute("searchString", searchString);
        return "/home";
    }

    @PostMapping("/home")
    public String postHomeView(Model model, HttpSession session,
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
        searchString = searchString.toLowerCase(Locale.ROOT);
        setHomeView(model, searchString, dictionaryLang);
        session.setAttribute("searchString", searchString);
        return "/home";
    }

    // EDIT
    @GetMapping("/edit/{id}")
    public String getEditView(Model model, @PathVariable Long id) {
        Dictionary dictionary = dictionaryService.fetchDictionaryById(id);
        List<String> partsOfSpeechList = dictionaryService.fetchPartsOfSpeechList();
        model.addAttribute("dictionary", dictionary);
        model.addAttribute("partsOfSpeechList", partsOfSpeechList);
        return "edit";
    }

    @PostMapping("/edit")
    public String postEditView(Model model, HttpSession session,
                               @ModelAttribute Dictionary dictionary,
                               @RequestParam(required = false) String cancel,
                               RedirectAttributes redirectAttributes) {
        String searchString = (String) session.getAttribute("searchString");
        redirectAttributes.addFlashAttribute("searchString", searchString);
        if (cancel != null) {
            return "redirect:/home";
        }
        dictionaryService.saveDictionary(dictionary);
        redirectAttributes.addFlashAttribute("message", "updated");
        return "redirect:/home";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String getDeleteView(HttpSession session, @PathVariable Long id, RedirectAttributes redirectAttributes) {
        dictionaryService.deleteEntryById(id);
        String searchString = (String) session.getAttribute("searchString");
        redirectAttributes.addFlashAttribute("searchString", searchString);
        redirectAttributes.addFlashAttribute("message", "deleted");
        return "redirect:/home";
    }

    // QUIZ
    @GetMapping("/quiz")
    public String getQuizView(Model model) {
        List<Dictionary> questions = dictionaryService.fetchRandomDictionaries(1);
        Dictionary question = questions.get(0);
        String swedish = question.getSwedishWord().replaceAll("[|,]", "");
        question.setSwedishWord(swedish);

        List<Dictionary> answers = dictionaryService.fetchRandomDictionaries(4);
        answers.add(question);
        Collections.shuffle(answers);

        model.addAttribute("question", question);
        model.addAttribute("answers", answers);
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

}
