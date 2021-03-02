package com.codecool.stockexchange.service;

import com.codecool.stockexchange.util.TextReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AboutPageService {

    TextReader textReader;

    @Value("${about.txt.path}")
    String path;

    public String returnMarkdownText(String lang){
        textReader = new TextReader(String.format(path, lang));
        return textReader.readMarkdown();
    }

}
