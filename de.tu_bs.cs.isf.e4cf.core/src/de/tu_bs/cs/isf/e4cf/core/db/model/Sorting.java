package de.tu_bs.cs.isf.e4cf.core.db.model;

import java.util.ArrayList;
import java.util.List;

public class Sorting {

	private List<Column> columns;
	private Condition groupCondition;
	private String sortingType;
	private String orderType;

	public Sorting(String pSortingType, Column... col) {
		sortingType = pSortingType;
		for (Column c : col) {
			columns.add(c);
		}
	}

	public Sorting(String pSortingType, Condition pGroupCondition, Column... col) {
		sortingType = pSortingType;
		groupCondition = pGroupCondition;
		columns = new ArrayList<>();
		for (Column c : col) {
			columns.add(c);
		}
	}

	public Sorting(String pSortingType, String pOrderType, Column... col) {
		sortingType = pSortingType;
		orderType = pOrderType;
		columns = new ArrayList<>();
		for (Column c : col) {
			columns.add(c);
		}
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(Column... col) {
		for (Column c : col) {
			columns.add(c);
		}
	}

	public Condition getGroupCondition() {
		return groupCondition;
	}

	public void setGroupCondition(Condition groupCondition) {
		this.groupCondition = groupCondition;
	}

	public String getSortingType() {
		return sortingType;
	}

	public void setSortingType(String sortingType) {
		this.sortingType = sortingType;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
}
