package com.codecool.stockexchange.util;

import com.codecool.stockexchange.entity.StockBaseData;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

//@Component
public class TextReader {

    File file;
    Scanner sc;

    public TextReader(String path){
        file = new File(path);
        try{
            sc = new Scanner(file);
        } catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    public List<StockBaseData> readLines(){
        List<StockBaseData> stocks = new LinkedList<>();
        while (sc.hasNextLine()){
            stocks.add(new StockBaseData(sc.nextLine().split(",")));
        }
        return stocks;
    }
}
