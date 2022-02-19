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
@SessionAttributes({"message"})
public class DictionaryController {

    private static final Logger log = LogManager.getLogger(DictionaryController.class);

    @Autowired
    private DictionaryService dictionaryService;

    @ModelAttribute("message")
    public String setUpMessage() {
        return "empty message";
    }

    @ModelAttribute("searchString")
    public String setUpSearchString() { return ""; }

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
                               @RequestParam(required = false) String create,
                               @RequestParam(required = false) String action) {
        if (create != null) {
            return "create";
        }
        if (action != null) {
            if (action.contains("delete")) {
                // TODO delete confirmation dialog
                Long id = Long.valueOf(action.replace("delete", ""));
                dictionaryService.deleteEntryById(id);
                model.addAttribute("message", "Hasło zostało usunięte ze słownika");
                return "redirect:/home";
            }
        }
        if (!searchString.equals("")) {
            log.info("searching for >" + searchString + "<");
            List<Dictionary> dictionaries = dictionaryService.findAllContaining(searchString);
            model.addAttribute("dictionaryList", dictionaries);
            model.addAttribute("searchString", searchString);
            model.addAttribute("message", "Znaleziono " + dictionaries.size() + " rekordów");
            return "/home";
        }
        return "home";
    }

    // CREATE VIEW

    // GET
    @GetMapping("/create")
    public String getCreateView(Model model) {
        model.addAttribute("dictionary", new Dictionary());
        return "create";
    }

    // POST
    @PostMapping("/create")
    public String postCreateView(Model model,
                                 @ModelAttribute Dictionary dictionary,
                                 @RequestParam(required = false) String cancel) {
        if (cancel != null) {
            return "redirect:/home";
        }
        dictionaryService.saveDictionary(dictionary);
        model.addAttribute("message", "Nowe hasło w słowniku zostało utworzone pomyślnie.");
        return "redirect:/home";
    }
}
