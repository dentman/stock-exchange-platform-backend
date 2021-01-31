package com.codecool.stockexchange.entity.stockinfo;

import com.codecool.stockexchange.apimodel.NewsItemAPI;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class NewsItem {

    @Id
    @GeneratedValue
    private Long id;

    private String image;
    private String url;
    private String headline;
    //@Column( columnDefinition = "TEXT")
    @Type( type = "text")
    private String summary;
    private String source;
    private long datetime;
    private String lang;

    @ManyToOne
    private StockInfo stockInfo;

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
