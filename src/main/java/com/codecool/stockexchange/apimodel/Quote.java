package com.codecool.stockexchange.apimodel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Quote {

	@JsonProperty("symbol")
	private String symbol;

	@JsonProperty("highTime")
	private long highTime;

	@JsonProperty("avgTotalVolume")
	private int avgTotalVolume;

	@JsonProperty("companyName")
	private String companyName;

	@JsonProperty("openSource")
	private String openSource;

	@JsonProperty("delayedPrice")
	private double delayedPrice;

	@JsonProperty("iexMarketPercent")
	private Object iexMarketPercent;

	@JsonProperty("primaryExchange")
	private String primaryExchange;

	@JsonProperty("latestUpdate")
	private long latestUpdate;

	@JsonProperty("high")
	private int high;

	@JsonProperty("iexOpenTime")
	private long iexOpenTime;

	@JsonProperty("delayedPriceTime")
	private long delayedPriceTime;

	@JsonProperty("extendedPrice")
	private double extendedPrice;

	@JsonProperty("week52Low")
	private double week52Low;

	@JsonProperty("highSource")
	private String highSource;

	@JsonProperty("latestPrice")
	private double latestPrice;

	@JsonProperty("marketCap")
	private long marketCap;

	@JsonProperty("iexClose")
	private double iexClose;

	@JsonProperty("volume")
	private int volume;

	@JsonProperty("ytdChange")
	private double ytdChange;

	@JsonProperty("lastTradeTime")
	private long lastTradeTime;

	@JsonProperty("closeSource")
	private String closeSource;

	@JsonProperty("extendedChange")
	private double extendedChange;

	@JsonProperty("iexRealtimePrice")
	private Object iexRealtimePrice;

	@JsonProperty("calculationPrice")
	private String calculationPrice;

	@JsonProperty("extendedChangePercent")
	private double extendedChangePercent;

	@JsonProperty("latestSource")
	private String latestSource;

	@JsonProperty("iexOpen")
	private double iexOpen;

	@JsonProperty("iexBidPrice")
	private Object iexBidPrice;

	@JsonProperty("previousClose")
	private double previousClose;

	@JsonProperty("peRatio")
	private double peRatio;

	@JsonProperty("isUSMarketOpen")
	private boolean isUSMarketOpen;

	@JsonProperty("low")
	private double low;

	@JsonProperty("oddLotDelayedPrice")
	private double oddLotDelayedPrice;

	@JsonProperty("extendedPriceTime")
	private long extendedPriceTime;

	@JsonProperty("closeTime")
	private long closeTime;

	@JsonProperty("changePercent")
	private double changePercent;

	@JsonProperty("week52High")
	private double week52High;

	@JsonProperty("openTime")
	private long openTime;

	@JsonProperty("close")
	private double close;

	@JsonProperty("iexCloseTime")
	private long iexCloseTime;

	@JsonProperty("oddLotDelayedPriceTime")
	private long oddLotDelayedPriceTime;

	@JsonProperty("previousVolume")
	private int previousVolume;

	@JsonProperty("iexRealtimeSize")
	private Object iexRealtimeSize;

	@JsonProperty("iexLastUpdated")
	private Object iexLastUpdated;

	@JsonProperty("change")
	private double change;

	@JsonProperty("latestVolume")
	private int latestVolume;

	@JsonProperty("iexAskPrice")
	private Object iexAskPrice;

	@JsonProperty("lowSource")
	private String lowSource;

	@JsonProperty("iexVolume")
	private Object iexVolume;

	@JsonProperty("iexAskSize")
	private Object iexAskSize;

	@JsonProperty("latestTime")
	private String latestTime;

	@JsonProperty("open")
	private double open;

	@JsonProperty("lowTime")
	private long lowTime;

	@JsonProperty("iexBidSize")
	private Object iexBidSize;

	public void setSymbol(String symbol){
		this.symbol = symbol;
	}

	public String getSymbol(){
		return symbol;
	}

	public void setHighTime(long highTime){
		this.highTime = highTime;
	}

	public long getHighTime(){
		return highTime;
	}

	public void setAvgTotalVolume(int avgTotalVolume){
		this.avgTotalVolume = avgTotalVolume;
	}

	public int getAvgTotalVolume(){
		return avgTotalVolume;
	}

	public void setCompanyName(String companyName){
		this.companyName = companyName;
	}

	public String getCompanyName(){
		return companyName;
	}

	public void setOpenSource(String openSource){
		this.openSource = openSource;
	}

	public String getOpenSource(){
		return openSource;
	}

	public void setDelayedPrice(double delayedPrice){
		this.delayedPrice = delayedPrice;
	}

	public double getDelayedPrice(){
		return delayedPrice;
	}

	public void setIexMarketPercent(Object iexMarketPercent){
		this.iexMarketPercent = iexMarketPercent;
	}

	public Object getIexMarketPercent(){
		return iexMarketPercent;
	}

	public void setPrimaryExchange(String primaryExchange){
		this.primaryExchange = primaryExchange;
	}

	public String getPrimaryExchange(){
		return primaryExchange;
	}

	public void setLatestUpdate(long latestUpdate){
		this.latestUpdate = latestUpdate;
	}

	public long getLatestUpdate(){
		return latestUpdate;
	}

	public void setHigh(int high){
		this.high = high;
	}

	public int getHigh(){
		return high;
	}

	public void setIexOpenTime(long iexOpenTime){
		this.iexOpenTime = iexOpenTime;
	}

	public long getIexOpenTime(){
		return iexOpenTime;
	}

	public void setDelayedPriceTime(long delayedPriceTime){
		this.delayedPriceTime = delayedPriceTime;
	}

	public long getDelayedPriceTime(){
		return delayedPriceTime;
	}

	public void setExtendedPrice(double extendedPrice){
		this.extendedPrice = extendedPrice;
	}

	public double getExtendedPrice(){
		return extendedPrice;
	}

	public void setWeek52Low(double week52Low){
		this.week52Low = week52Low;
	}

	public double getWeek52Low(){
		return week52Low;
	}

	public void setHighSource(String highSource){
		this.highSource = highSource;
	}

	public String getHighSource(){
		return highSource;
	}

	public void setLatestPrice(double latestPrice){
		this.latestPrice = latestPrice;
	}

	public double getLatestPrice(){
		return latestPrice;
	}

	public void setMarketCap(long marketCap){
		this.marketCap = marketCap;
	}

	public long getMarketCap(){
		return marketCap;
	}

	public void setIexClose(double iexClose){
		this.iexClose = iexClose;
	}

	public double getIexClose(){
		return iexClose;
	}

	public void setVolume(int volume){
		this.volume = volume;
	}

	public int getVolume(){
		return volume;
	}

	public void setYtdChange(double ytdChange){
		this.ytdChange = ytdChange;
	}

	public double getYtdChange(){
		return ytdChange;
	}

	public void setLastTradeTime(long lastTradeTime){
		this.lastTradeTime = lastTradeTime;
	}

	public long getLastTradeTime(){
		return lastTradeTime;
	}

	public void setCloseSource(String closeSource){
		this.closeSource = closeSource;
	}

	public String getCloseSource(){
		return closeSource;
	}

	public void setExtendedChange(double extendedChange){
		this.extendedChange = extendedChange;
	}

	public double getExtendedChange(){
		return extendedChange;
	}

	public void setIexRealtimePrice(Object iexRealtimePrice){
		this.iexRealtimePrice = iexRealtimePrice;
	}

	public Object getIexRealtimePrice(){
		return iexRealtimePrice;
	}

	public void setCalculationPrice(String calculationPrice){
		this.calculationPrice = calculationPrice;
	}

	public String getCalculationPrice(){
		return calculationPrice;
	}

	public void setExtendedChangePercent(double extendedChangePercent){
		this.extendedChangePercent = extendedChangePercent;
	}

	public double getExtendedChangePercent(){
		return extendedChangePercent;
	}

	public void setLatestSource(String latestSource){
		this.latestSource = latestSource;
	}

	public String getLatestSource(){
		return latestSource;
	}

	public void setIexOpen(double iexOpen){
		this.iexOpen = iexOpen;
	}

	public double getIexOpen(){
		return iexOpen;
	}

	public void setIexBidPrice(Object iexBidPrice){
		this.iexBidPrice = iexBidPrice;
	}

	public Object getIexBidPrice(){
		return iexBidPrice;
	}

	public void setPreviousClose(double previousClose){
		this.previousClose = previousClose;
	}

	public double getPreviousClose(){
		return previousClose;
	}

	public void setPeRatio(double peRatio){
		this.peRatio = peRatio;
	}

	public double getPeRatio(){
		return peRatio;
	}

	public void setIsUSMarketOpen(boolean isUSMarketOpen){
		this.isUSMarketOpen = isUSMarketOpen;
	}

	public boolean isIsUSMarketOpen(){
		return isUSMarketOpen;
	}

	public void setLow(double low){
		this.low = low;
	}

	public double getLow(){
		return low;
	}

	public void setOddLotDelayedPrice(double oddLotDelayedPrice){
		this.oddLotDelayedPrice = oddLotDelayedPrice;
	}

	public double getOddLotDelayedPrice(){
		return oddLotDelayedPrice;
	}

	public void setExtendedPriceTime(long extendedPriceTime){
		this.extendedPriceTime = extendedPriceTime;
	}

	public long getExtendedPriceTime(){
		return extendedPriceTime;
	}

	public void setCloseTime(long closeTime){
		this.closeTime = closeTime;
	}

	public long getCloseTime(){
		return closeTime;
	}

	public void setChangePercent(double changePercent){
		this.changePercent = changePercent;
	}

	public double getChangePercent(){
		return changePercent;
	}

	public void setWeek52High(double week52High){
		this.week52High = week52High;
	}

	public double getWeek52High(){
		return week52High;
	}

	public void setOpenTime(long openTime){
		this.openTime = openTime;
	}

	public long getOpenTime(){
		return openTime;
	}

	public void setClose(double close){
		this.close = close;
	}

	public double getClose(){
		return close;
	}

	public void setIexCloseTime(long iexCloseTime){
		this.iexCloseTime = iexCloseTime;
	}

	public long getIexCloseTime(){
		return iexCloseTime;
	}

	public void setOddLotDelayedPriceTime(long oddLotDelayedPriceTime){
		this.oddLotDelayedPriceTime = oddLotDelayedPriceTime;
	}

	public long getOddLotDelayedPriceTime(){
		return oddLotDelayedPriceTime;
	}

	public void setPreviousVolume(int previousVolume){
		this.previousVolume = previousVolume;
	}

	public int getPreviousVolume(){
		return previousVolume;
	}

	public void setIexRealtimeSize(Object iexRealtimeSize){
		this.iexRealtimeSize = iexRealtimeSize;
	}

	public Object getIexRealtimeSize(){
		return iexRealtimeSize;
	}

	public void setIexLastUpdated(Object iexLastUpdated){
		this.iexLastUpdated = iexLastUpdated;
	}

	public Object getIexLastUpdated(){
		return iexLastUpdated;
	}

	public void setChange(double change){
		this.change = change;
	}

	public double getChange(){
		return change;
	}

	public void setLatestVolume(int latestVolume){
		this.latestVolume = latestVolume;
	}

	public int getLatestVolume(){
		return latestVolume;
	}

	public void setIexAskPrice(Object iexAskPrice){
		this.iexAskPrice = iexAskPrice;
	}

	public Object getIexAskPrice(){
		return iexAskPrice;
	}

	public void setLowSource(String lowSource){
		this.lowSource = lowSource;
	}

	public String getLowSource(){
		return lowSource;
	}

	public void setIexVolume(Object iexVolume){
		this.iexVolume = iexVolume;
	}

	public Object getIexVolume(){
		return iexVolume;
	}

	public void setIexAskSize(Object iexAskSize){
		this.iexAskSize = iexAskSize;
	}

	public Object getIexAskSize(){
		return iexAskSize;
	}

	public void setLatestTime(String latestTime){
		this.latestTime = latestTime;
	}

	public String getLatestTime(){
		return latestTime;
	}

	public void setOpen(double open){
		this.open = open;
	}

	public double getOpen(){
		return open;
	}

	public void setLowTime(long lowTime){
		this.lowTime = lowTime;
	}

	public long getLowTime(){
		return lowTime;
	}

	public void setIexBidSize(Object iexBidSize){
		this.iexBidSize = iexBidSize;
	}

	public Object getIexBidSize(){
		return iexBidSize;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"symbol = '" + symbol + '\'' + 
			",highTime = '" + highTime + '\'' + 
			",avgTotalVolume = '" + avgTotalVolume + '\'' + 
			",companyName = '" + companyName + '\'' + 
			",openSource = '" + openSource + '\'' + 
			",delayedPrice = '" + delayedPrice + '\'' + 
			",iexMarketPercent = '" + iexMarketPercent + '\'' + 
			",primaryExchange = '" + primaryExchange + '\'' + 
			",latestUpdate = '" + latestUpdate + '\'' + 
			",high = '" + high + '\'' + 
			",iexOpenTime = '" + iexOpenTime + '\'' + 
			",delayedPriceTime = '" + delayedPriceTime + '\'' + 
			",extendedPrice = '" + extendedPrice + '\'' + 
			",week52Low = '" + week52Low + '\'' + 
			",highSource = '" + highSource + '\'' + 
			",latestPrice = '" + latestPrice + '\'' + 
			",marketCap = '" + marketCap + '\'' + 
			",iexClose = '" + iexClose + '\'' + 
			",volume = '" + volume + '\'' + 
			",ytdChange = '" + ytdChange + '\'' + 
			",lastTradeTime = '" + lastTradeTime + '\'' + 
			",closeSource = '" + closeSource + '\'' + 
			",extendedChange = '" + extendedChange + '\'' + 
			",iexRealtimePrice = '" + iexRealtimePrice + '\'' + 
			",calculationPrice = '" + calculationPrice + '\'' + 
			",extendedChangePercent = '" + extendedChangePercent + '\'' + 
			",latestSource = '" + latestSource + '\'' + 
			",iexOpen = '" + iexOpen + '\'' + 
			",iexBidPrice = '" + iexBidPrice + '\'' + 
			",previousClose = '" + previousClose + '\'' + 
			",peRatio = '" + peRatio + '\'' + 
			",isUSMarketOpen = '" + isUSMarketOpen + '\'' + 
			",low = '" + low + '\'' + 
			",oddLotDelayedPrice = '" + oddLotDelayedPrice + '\'' + 
			",extendedPriceTime = '" + extendedPriceTime + '\'' + 
			",closeTime = '" + closeTime + '\'' + 
			",changePercent = '" + changePercent + '\'' + 
			",week52High = '" + week52High + '\'' + 
			",openTime = '" + openTime + '\'' + 
			",close = '" + close + '\'' + 
			",iexCloseTime = '" + iexCloseTime + '\'' + 
			",oddLotDelayedPriceTime = '" + oddLotDelayedPriceTime + '\'' + 
			",previousVolume = '" + previousVolume + '\'' + 
			",iexRealtimeSize = '" + iexRealtimeSize + '\'' + 
			",iexLastUpdated = '" + iexLastUpdated + '\'' + 
			",change = '" + change + '\'' + 
			",latestVolume = '" + latestVolume + '\'' + 
			",iexAskPrice = '" + iexAskPrice + '\'' + 
			",lowSource = '" + lowSource + '\'' + 
			",iexVolume = '" + iexVolume + '\'' + 
			",iexAskSize = '" + iexAskSize + '\'' + 
			",latestTime = '" + latestTime + '\'' + 
			",open = '" + open + '\'' + 
			",lowTime = '" + lowTime + '\'' + 
			",iexBidSize = '" + iexBidSize + '\'' + 
			"}";
		}
}