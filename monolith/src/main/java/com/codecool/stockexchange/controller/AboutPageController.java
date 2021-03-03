package com.codecool.stockexchange.controller;

import com.codecool.stockexchange.entity.Message;
import com.codecool.stockexchange.service.AboutPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(value = "${cors.allowed.path}")
public class AboutPageController {

    private final AboutPageService aboutPageService;

    @Autowired
    public AboutPageController(AboutPageService aboutPageService){ this.aboutPageService = aboutPageService; }

    @GetMapping("/about/{lang}")
    public Message getAboutPageInfo(@PathVariable String lang){
        return new Message(true, aboutPageService.returnMarkdownText(lang));
    }
}
