package com.obss.model;

public class GedikStock extends BaseStock {
	
	private Float priceDiff;

	@Override
	public String toString() {
		return "GedikStock [priceDiff=" + priceDiff + ", getStockName()="
				+ getStockName() + ", getStockPrice()=" + getStockPrice()
				+ ", getVolume()=" + getVolume() + ", getHighestPrice()="
				+ getHighestPrice() + ", getLowestPrice()=" + getLowestPrice()
				+ "]";
	}

	public Float getPriceDiff() {
		return priceDiff;
	}

	public void setPriceDiff(Float priceDiff) {
		this.priceDiff = priceDiff;
	}

}
