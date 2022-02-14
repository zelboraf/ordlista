package com.ellara.ordlista.controller;

import com.ellara.ordlista.entity.Dictionary;
import com.ellara.ordlista.service.DictionaryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class DictionaryController {

    @Autowired
    private DictionaryService dictionaryService;

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
    public String postHomeView(HttpSession httpSession,
                                 @RequestParam(required = false) String create) {
        if (create != null) {
            return "create";
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
    public String postCreateView(HttpSession httpSession,
                                 @Valid @ModelAttribute Dictionary dictionary, BindingResult bindingResult,
                                 @RequestParam(required = false) String cancel) {
        if (cancel != null) {
            return "redirect:/home";
        }
        dictionaryService.saveDictionary(dictionary);

        return "create_result";
    }

}
