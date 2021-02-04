package com.codecool.stockexchange.apimodel;

import com.codecool.stockexchange.entity.stock.NewsItem;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NewsItemAPI {

	@JsonProperty("summary")
	private String summary;

	@JsonProperty("image")
	private String image;

	@JsonProperty("datetime")
	private long datetime;

	@JsonProperty("related")
	private String related;

	@JsonProperty("source")
	private String source;

	@JsonProperty("lang")
	private String lang;

	@JsonProperty("hasPaywall")
	private boolean hasPaywall;

	@JsonProperty("headline")
	private String headline;

	@JsonProperty("url")
	private String url;

	public void setSummary(String summary){
		this.summary = summary;
	}

	public String getSummary(){
		return summary;
	}

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setDatetime(long datetime){
		this.datetime = datetime;
	}

	public long getDatetime(){
		return datetime;
	}

	public void setRelated(String related){
		this.related = related;
	}

	public String getRelated(){
		return related;
	}

	public void setSource(String source){
		this.source = source;
	}

	public String getSource(){
		return source;
	}

	public void setLang(String lang){
		this.lang = lang;
	}

	public String getLang(){
		return lang;
	}

	public void setHasPaywall(boolean hasPaywall){
		this.hasPaywall = hasPaywall;
	}

	public boolean isHasPaywall(){
		return hasPaywall;
	}

	public void setHeadline(String headline){
		this.headline = headline;
	}

	public String getHeadline(){
		return headline;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	@Override
 	public String toString(){
		return 
			"NewsItem{" + 
			"summary = '" + summary + '\'' + 
			",image = '" + image + '\'' + 
			",datetime = '" + datetime + '\'' + 
			",related = '" + related + '\'' + 
			",source = '" + source + '\'' + 
			",lang = '" + lang + '\'' + 
			",hasPaywall = '" + hasPaywall + '\'' + 
			",headline = '" + headline + '\'' + 
			",url = '" + url + '\'' + 
			"}";
		}

    public static NewsItemAPI createNewsItem(NewsItem newsItem) {
        NewsItemAPI apiItem = new NewsItemAPI();
        apiItem.setDatetime(newsItem.getDatetime());
        apiItem.setHeadline(newsItem.getHeadline());
        apiItem.setImage(newsItem.getImage());
        apiItem.setSource(newsItem.getSource());
        apiItem.setLang(newsItem.getLang());
        apiItem.setUrl(newsItem.getUrl());
        return apiItem;
    }
}