package de.tu_bs.cs.isf.e4cf.core.db;

import de.tu_bs.cs.isf.e4cf.core.db.model.Column;
import de.tu_bs.cs.isf.e4cf.core.db.model.Sorting;
import de.tu_bs.cs.isf.e4cf.core.db.model.ColumnValue;
import de.tu_bs.cs.isf.e4cf.core.db.model.Condition;

public class DataUtilities {

	/**
	 * Method of obtaining conditions of data from a table
	 * 
	 * @param condi
	 * @return
	 */
	public String wCondition(Condition condi) {
		final String sql;
		if (condi != null) {
			switch (condi.getConditionTyp()) {
			case "and":
				sql = andCondition(condi);
				break;
			case "or":
				sql = orCondition(condi);
				break;
			case "like":
				sql = likeCondition(condi);
				break;
			default:
				sql = defaultCondition(condi);

			}
			return sql;
		} else {
			return ";";
		}

	}

	public String andCondition(Condition condi) {
		String conditionsql = " WHERE ";
		for (ColumnValue c : condi.getConditionValue()) {
			conditionsql += c.getColumnName() + " " + c.getSymbol() + " " + c.getValue() + " AND ";
		}
		conditionsql = conditionsql.substring(0, conditionsql.length() - 4);
		conditionsql += ";";
		return conditionsql;
	}

	public String orCondition(Condition condi) {
		String conditionsql = " WHERE ";
		for (ColumnValue c : condi.getConditionValue()) {
			conditionsql += c.getColumnName() + " " + c.getSymbol() + " " + c.getValue() + " OR ";
		}
		conditionsql = conditionsql.substring(0, conditionsql.length() - 4);
		conditionsql += ";";
		return conditionsql;
	}

	public String likeCondition(Condition condi) {
		String conditionsql = " WHERE ";
		for (ColumnValue c : condi.getConditionValue()) {
			conditionsql += c.getColumnName() + " LIKE " + "'" + c.getValue() + "'";
		}
		conditionsql += ";";
		return conditionsql;
	}

	public String defaultCondition(Condition condi) {
		String conditionsql = " WHERE ";
		for (ColumnValue c : condi.getConditionValue()) {
			conditionsql += c.getColumnName() + " " + c.getSymbol() + " " + c.getValue();
		}
		conditionsql += ";";
		return conditionsql;

	}

	public String havingCondition(Condition condi) {
		String conditionsql = " HAVING ";
		for (ColumnValue c : condi.getConditionValue()) {
			conditionsql += c.getColumnName() + " " + c.getSymbol() + " " + c.getValue();
		}
		conditionsql += ";";
		return conditionsql;
	}

	public String sort(Sorting sorting) {
		final String sql;
		if (sorting != null) {
			switch (sorting.getSortingType()) {
			case "group by":
				sql = group(sorting);
				break;
			case "order by":
				sql = order(sorting);
				break;
			default:
				sql = "";
			}
			return sql;
		} else {
			return ";";
		}
	}

	public String group(Sorting sort) {
		String groupSql = " GROUP BY ";
		for (Column c : sort.getColumns()) {
			groupSql += c.getName() + " ";
		}
		if (sort.getGroupCondition() != null) {
			groupSql += havingCondition(sort.getGroupCondition());
			groupSql = groupSql.substring(0, groupSql.length() - 1);
		}
		groupSql += ";";
		return groupSql;
	}

	public String order(Sorting sort) {
		String orderSql = " ORDER BY ";
		for (Column c : sort.getColumns()) {
			orderSql += c.getName() + ", ";
		}
		orderSql = orderSql.substring(0, orderSql.length() - 2);
		orderSql += " " + sort.getOrderType() + ";";
		return orderSql;
	}
}
