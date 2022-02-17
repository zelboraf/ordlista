package com.ellara.ordlista.controller;

import com.ellara.ordlista.entity.Dictionary;
import com.ellara.ordlista.service.DictionaryService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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

    // REDIRECT
    @GetMapping("/")
    public String getRoot() {
        return "redirect:/home";
    }

    //
    // HOME VIEW
    //

    // GET
    @GetMapping("/home")
    public String getHomeView(Model model) {
        model.addAttribute("dictionaryList", dictionaryService.fetchDictionaryList());
        return "home";
    }

    // POST
    @PostMapping("/home")
    public String postHomeView(Model model,
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
        return "home";
    }

    //
    // CREATE VIEW
    //

    // GET
    @GetMapping("/create")
    public String getCreateView(Model model) {
        model.addAttribute("dictionary", new Dictionary());
        return "create";
    }

    // POST
    @PostMapping("/create")
    public String postCreateView(Model model,
                                 @Valid @ModelAttribute Dictionary dictionary, BindingResult bindingResult,
                                 @RequestParam(required = false) String cancel) {
        if (cancel != null) {
            return "redirect:/home";
        }
        dictionaryService.saveDictionary(dictionary);
        model.addAttribute("message", "Nowe hasło w słowniku zostało utworzone pomyślnie.");
        return "redirect:/home";
    }


}
