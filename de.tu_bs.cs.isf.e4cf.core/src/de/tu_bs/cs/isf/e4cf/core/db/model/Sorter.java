package de.tu_bs.cs.isf.e4cf.core.db.model;

/**
 * 
 * The class which stands for the sorting condition in an sql statement.
 *
 */
public class Sorter {

	private Condition groupCondition = null;
	private Condition orderCondition = null;
	private String orderType = null;

	public Sorter(Condition groupCondition, Condition orderCondition, String orderType) {
		this.groupCondition = groupCondition;
		this.orderCondition = orderCondition;
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
			if (null != orderType) {
				orderSql += " " + orderType;
			}
		}
		return orderSql;
	}
}
