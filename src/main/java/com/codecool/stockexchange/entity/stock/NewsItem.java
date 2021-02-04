package com.codecool.stockexchange.entity.stock;

import com.codecool.stockexchange.apimodel.NewsItemAPI;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class NewsItem {

    @Id
    @GeneratedValue
    private Long id;

    private String image;
    private String url;
    private String headline;
    @Type( type = "text")
    private String summary;
    private String source;
    private long datetime;
    private String lang;

    @ManyToOne
    private Stock stock;

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
