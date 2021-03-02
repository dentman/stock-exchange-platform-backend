package com.codecool.stockexchange.util;

import com.codecool.stockexchange.entity.StockBaseData;
import com.codecool.stockexchange.exception.resource.ResourceNotFoundException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;


public class TextReader {

    File file;
    Scanner sc;

    public TextReader(String path){
        file = new File(path);
        try{
            sc = new Scanner(file);
        } catch (FileNotFoundException e){
            throw new ResourceNotFoundException();
        }
    }

    public List<StockBaseData> readLines(){
        List<StockBaseData> stocks = new LinkedList<>();
        while (sc.hasNextLine()){
            stocks.add(new StockBaseData(sc.nextLine().split(",")));
        }
        return stocks;
    }

    public String readMarkdown() {
        StringJoiner stringJoiner = new StringJoiner("\n\n");
        while (sc.hasNextLine()){
            stringJoiner.add(sc.nextLine());
        }
        return stringJoiner.toString();
    }
}
