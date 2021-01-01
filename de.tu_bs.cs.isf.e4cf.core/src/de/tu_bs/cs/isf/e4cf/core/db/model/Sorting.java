package de.tu_bs.cs.isf.e4cf.core.db.model;

import java.util.ArrayList;
import java.util.List;

public class Sorting {

	private Condition groupCondition = null;
	private Condition orderCondition = null;
	private String groupSortingType = null;
	private String orderType = null;

	public Sorting(Condition groupCondition, Condition orderCondition, String groupSortingType, String orderType) {
		this.groupCondition = groupCondition;
		this.orderCondition = orderCondition;
		this.groupSortingType = orderType;
		this.orderType = orderType;
	}

	public String getSortingAsSql() {
		return groupByAsSql() + orderByAsSql();
	}

	private String groupByAsSql() {
		String groupSql = "";
		if (null != groupCondition) {
			groupSql = " GROUP BY ";
			for (ColumnValue c : groupCondition.getColumnValuesList()) {
				groupSql += c.getColumnName() + ", ";
			}
			groupSql = groupSql.substring(0, groupSql.length() - 2);
		}
		return groupSql;
	}

	private String orderByAsSql() {
		String orderSql = "";
		if (null != orderCondition) {
			orderSql = " ORDER BY ";
			for (ColumnValue c : orderCondition.getColumnValuesList()) {
				orderSql += c.getColumnName() + ", ";
			}
			orderSql = orderSql.substring(0, orderSql.length() - 2);
		}
		return orderSql;
	}

}
