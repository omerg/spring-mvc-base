package com.obss.model.response;

import java.util.List;

import com.obss.model.BaseStock;

public class ResponseStockList {

	private List<BaseStock> stockList;
	private Long rowCount;

	public List<BaseStock> getStockList() {
		return stockList;
	}

	public void setStockList(List<BaseStock> stockList) {
		this.stockList = stockList;
	}

	public Long getRowCount() {
		return rowCount;
	}

	public void setRowCount(Long rowCount) {
		this.rowCount = rowCount;
	}

}
