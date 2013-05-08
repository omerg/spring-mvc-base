package com.obss.model;

public class BaseStock {
	
	private String stockName;
	private Float stockPrice;
	private Float volume;
	private Float highestPrice;
	private Float lowestPrice;
	
	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public Float getStockPrice() {
		return stockPrice;
	}

	public void setStockPrice(Float stockPrice) {
		this.stockPrice = stockPrice;
	}
	
	public Float getVolume() {
		return volume;
	}

	public void setVolume(Float volume) {
		this.volume = volume;
	}

	public Float getHighestPrice() {
		return highestPrice;
	}

	public void setHighestPrice(Float highestPrice) {
		this.highestPrice = highestPrice;
	}

	public Float getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(Float lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	@Override
	public String toString() {
		return "BaseStock [stockName=" + stockName + ", stockPrice="
				+ stockPrice + ", volume=" + volume + ", highestPrice="
				+ highestPrice + ", lowestPrice=" + lowestPrice + "]";
	}

}