package com.obss.model;

import java.util.List;

public class ResponseAccountList {
	
	private List<Account> accountList;
	private Long rowCount;

	public List<Account> getAccountList() {
		return accountList;
	}

	public void setAccountList(List<Account> accountList) {
		this.accountList = accountList;
	}

	public Long getRowCount() {
		return rowCount;
	}

	public void setRowCount(Long rowCount) {
		this.rowCount = rowCount;
	}

}
