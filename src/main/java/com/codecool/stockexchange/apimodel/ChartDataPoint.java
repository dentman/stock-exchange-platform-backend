package com.codecool.stockexchange.apimodel;

import com.codecool.stockexchange.entity.stockinfo.StockPrice;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ChartDataPoint {

	@JsonProperty("date")
	private String date;

	@JsonProperty("symbol")
	private String symbol;

	@JsonProperty("subkey")
	private String subkey;

	@JsonProperty("fClose")
	private double fClose;

	@JsonProperty("uOpen")
	private double uOpen;

	@JsonProperty("uClose")
	private double uClose;

	@JsonProperty("high")
	private double high;

	@JsonProperty("fOpen")
	private double fOpen;

	@JsonProperty("low")
	private double low;

	@JsonProperty("changeOverTime")
	private int changeOverTime;

	@JsonProperty("fHigh")
	private double fHigh;

	@JsonProperty("changePercent")
	private int changePercent;

	@JsonProperty("id")
	private String id;

	@JsonProperty("marketChangeOverTime")
	private int marketChangeOverTime;

	@JsonProperty("close")
	private double close;

	@JsonProperty("key")
	private String key;

	@JsonProperty("fLow")
	private double fLow;

	@JsonProperty("uHigh")
	private double uHigh;

	@JsonProperty("uVolume")
	private int uVolume;

	@JsonProperty("change")
	private int change;

	@JsonProperty("uLow")
	private double uLow;

	@JsonProperty("fVolume")
	private int fVolume;

	@JsonProperty("label")
	private String label;

	@JsonProperty("volume")
	private int volume;

	@JsonProperty("updated")
	private long updated;

	@JsonProperty("open")
	private double open;

	public static ChartDataPoint createChartDataPoint(StockPrice stockPrice) {
		ChartDataPoint chartDataPoint = new ChartDataPoint();
		chartDataPoint.setDate(stockPrice.getDate().toString());
		chartDataPoint.setClose(stockPrice.getPrice().doubleValue());
		return chartDataPoint;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDate() {
		return date;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSubkey(String subkey) {
		this.subkey = subkey;
	}

	public String getSubkey() {
		return subkey;
	}

	public void setFClose(double fClose) {
		this.fClose = fClose;
	}

	public double getFClose() {
		return fClose;
	}

	public void setUOpen(double uOpen) {
		this.uOpen = uOpen;
	}

	public double getUOpen() {
		return uOpen;
	}

	public void setUClose(double uClose) {
		this.uClose = uClose;
	}

	public double getUClose() {
		return uClose;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getHigh() {
		return high;
	}

	public void setFOpen(double fOpen) {
		this.fOpen = fOpen;
	}

	public double getFOpen() {
		return fOpen;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public double getLow() {
		return low;
	}

	public void setChangeOverTime(int changeOverTime) {
		this.changeOverTime = changeOverTime;
	}

	public int getChangeOverTime() {
		return changeOverTime;
	}

	public void setFHigh(double fHigh) {
		this.fHigh = fHigh;
	}

	public double getFHigh() {
		return fHigh;
	}

	public void setChangePercent(int changePercent) {
		this.changePercent = changePercent;
	}

	public int getChangePercent() {
		return changePercent;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setMarketChangeOverTime(int marketChangeOverTime) {
		this.marketChangeOverTime = marketChangeOverTime;
	}

	public int getMarketChangeOverTime() {
		return marketChangeOverTime;
	}

	public void setClose(double close) {
		this.close = close;
	}

	public double getClose() {
		return close;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setFLow(double fLow) {
		this.fLow = fLow;
	}

	public double getFLow() {
		return fLow;
	}

	public void setUHigh(double uHigh) {
		this.uHigh = uHigh;
	}

	public double getUHigh() {
		return uHigh;
	}

	public void setUVolume(int uVolume) {
		this.uVolume = uVolume;
	}

	public int getUVolume() {
		return uVolume;
	}

	public void setChange(int change) {
		this.change = change;
	}

	public int getChange() {
		return change;
	}

	public void setULow(double uLow) {
		this.uLow = uLow;
	}

	public double getULow() {
		return uLow;
	}

	public void setFVolume(int fVolume) {
		this.fVolume = fVolume;
	}

	public int getFVolume() {
		return fVolume;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public int getVolume() {
		return volume;
	}

	public void setUpdated(long updated) {
		this.updated = updated;
	}

	public long getUpdated() {
		return updated;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public double getOpen() {
		return open;
	}

	@Override
	public String toString() {
		return "ChartDataPoint{" + "date = '" + date + '\'' + ",symbol = '" + symbol + '\'' + ",subkey = '" + subkey
				+ '\'' + ",fClose = '" + fClose + '\'' + ",uOpen = '" + uOpen + '\'' + ",uClose = '" + uClose + '\''
				+ ",high = '" + high + '\'' + ",fOpen = '" + fOpen + '\'' + ",low = '" + low + '\''
				+ ",changeOverTime = '" + changeOverTime + '\'' + ",fHigh = '" + fHigh + '\'' + ",changePercent = '"
				+ changePercent + '\'' + ",id = '" + id + '\'' + ",marketChangeOverTime = '" + marketChangeOverTime
				+ '\'' + ",close = '" + close + '\'' + ",key = '" + key + '\'' + ",fLow = '" + fLow + '\''
				+ ",uHigh = '" + uHigh + '\'' + ",uVolume = '" + uVolume + '\'' + ",change = '" + change + '\''
				+ ",uLow = '" + uLow + '\'' + ",fVolume = '" + fVolume + '\'' + ",label = '" + label + '\''
				+ ",volume = '" + volume + '\'' + ",updated = '" + updated + '\'' + ",open = '" + open + '\'' + "}";
	}
}