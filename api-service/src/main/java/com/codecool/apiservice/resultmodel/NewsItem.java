package com.codecool.apiservice.resultmodel;

import com.codecool.apiservice.apimodel.NewsItemAPI;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class NewsItem {

    private Long id;

    private String image;
    private String url;
    private String headline;
    private String summary;
    private String source;
    private long datetime;
    private String lang;

    private long stockId;

    public NewsItem(NewsItemAPI news){
        this.image = news.getImage();
        this.url = news.getUrl();
        this.headline = news.getHeadline();
        this.summary = news.getSummary();
        this.source = news.getSource();
        this.datetime = news.getDatetime();
        this.lang = news.getLang();
    }

}
