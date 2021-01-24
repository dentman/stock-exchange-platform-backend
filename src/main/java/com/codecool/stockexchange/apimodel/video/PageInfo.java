package com.codecool.stockexchange.apimodel.video;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PageInfo{

	@JsonProperty("totalResults")
	private int totalResults;

	@JsonProperty("resultsPerPage")
	private int resultsPerPage;

	public void setTotalResults(int totalResults){
		this.totalResults = totalResults;
	}

	public int getTotalResults(){
		return totalResults;
	}

	public void setResultsPerPage(int resultsPerPage){
		this.resultsPerPage = resultsPerPage;
	}

	public int getResultsPerPage(){
		return resultsPerPage;
	}

	@Override
 	public String toString(){
		return 
			"PageInfo{" + 
			"totalResults = '" + totalResults + '\'' + 
			",resultsPerPage = '" + resultsPerPage + '\'' + 
			"}";
		}
}