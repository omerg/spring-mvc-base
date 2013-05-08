package com.obss.model.response;

import java.util.List;

import com.obss.model.GedikStock;

public class ResponseGedikStockList {

	private List<GedikStock> gedikStockList;
	private Long rowCount;

	public List<GedikStock> getGedikStockList() {
		return gedikStockList;
	}

	public void setGedikStockList(List<GedikStock> gedikStockList) {
		this.gedikStockList = gedikStockList;
	}

	public Long getRowCount() {
		return rowCount;
	}

	public void setRowCount(Long rowCount) {
		this.rowCount = rowCount;
	}

}
