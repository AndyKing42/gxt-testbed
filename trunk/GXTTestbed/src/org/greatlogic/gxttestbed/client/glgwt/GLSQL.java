package org.greatlogic.gxttestbed.client.glgwt;
/*
 * Copyright 2006-2014 Andy King (GreatLogic.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;
import org.greatlogic.gxttestbed.shared.glgwt.IGLColumn;
import org.greatlogic.gxttestbed.shared.glgwt.IGLTable;
import org.greatlogic.gxttestbed.shared.glgwt.IGLEnums.EGLDBConj;
import org.greatlogic.gxttestbed.shared.glgwt.IGLEnums.EGLDBException;
import org.greatlogic.gxttestbed.shared.glgwt.IGLEnums.EGLDBOp;
import org.greatlogic.gxttestbed.shared.glgwt.IGLEnums.EGLJoinType;
import org.greatlogic.gxttestbed.shared.glgwt.IGLEnums.EGLSQLType;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class GLSQL {
//--------------------------------------------------------------------------------------------------
private static final String            CloseParens = "))))))))))";
private static final String            OpenParens  = "((((((((((";

private final TreeMap<String, String>  _columnMap;                // column names (insert/select/update) and values (insert/update)
private String                         _dataSourceName;
private String                         _fromHint;
private ArrayList<String>              _groupByList;
private boolean                        _ignoreDuplicates;
private ArrayList<GLJoinDef>           _joinDefList;
private IGLColumn                      _keyColumn;
private int                            _maxNumberOfRows;
private String                         _orderByClause;
private final EGLSQLType               _sqlType;
private IGLTable                       _table;
private String                         _tableName;
private final ArrayList<GLWhereClause> _whereClauseList;
//==================================================================================================
private static class GLJoinDef {
private final CharSequence _condition;
private final String       _joinTable;
private final EGLJoinType  _joinType;
private GLJoinDef(final EGLJoinType joinType, final String joinTable, final CharSequence condition) {
  _joinType = joinType;
  _joinTable = joinTable;
  _condition = condition;
}
}
//==================================================================================================
private static class GLWhereClause {
private final int       _closeParens;
private String          _columnName;
private final EGLDBConj _conjunction;
private CharSequence    _expression;
private final int       _openParens;
private EGLDBOp         _operator;
private Object          _value;
public GLWhereClause(final EGLDBConj conjunction, final int openParens,
                     final CharSequence expression, final int closeParens) {
  _conjunction = conjunction;
  _openParens = openParens;
  _expression = expression;
  _closeParens = closeParens;
}
public GLWhereClause(final EGLDBConj conjunction, final int openParens, final String columnName,
                     final EGLDBOp operator, final Object value, final int closeParens) {
  _conjunction = conjunction;
  _openParens = openParens;
  _columnName = columnName;
  _operator = operator;
  _value = value;
  _closeParens = closeParens;
}
@Override
public String toString() {
  String result = _conjunction + " " + //
                  (_openParens > 0 ? OpenParens.substring(0, _openParens) : "");
  if (_expression == null) {
    result += _columnName + " " + _operator.getSQL() + " " + _value;
  }
  else {
    result += _expression;
  }
  result += _closeParens > 0 ? CloseParens.substring(0, _closeParens) : "";
  return result;
}
}
//==================================================================================================
/**
 * Returns a GLSQL object that can be used for constructing a "delete" statement against the default
 * data source.
 * @param table The table that will be used in the "delete" statement.
 * @return A GLSQL object that can be used for constructing a "delete" statement.
 */
public static GLSQL delete(final IGLTable table) {
  return GLSQL.delete(table, null);
}
//--------------------------------------------------------------------------------------------------
/**
 * Returns a GLSQL object that can be used for constructing a "delete" statement against the default
 * data source.
 * @param tableName The table that will be used in the "delete" statement.
 * @return A GLSQL object that can be used for constructing a "delete" statement.
 */
public static GLSQL delete(final String tableName) {
  return GLSQL.delete(tableName, null);
}
//--------------------------------------------------------------------------------------------------
/**
 * Returns a GLSQL object that can be used for constructing a "delete" statement.
 * @param table The table that will be used in the "delete" statement.
 * @param dataSourceName The data source name for the data source against which the delete will be
 * executed.
 * @return A GLSQL object that can be used for constructing a "delete" statement.
 */
public static GLSQL delete(final IGLTable table, final String dataSourceName) {
  return new GLSQL(EGLSQLType.Delete, table, dataSourceName);
}
//--------------------------------------------------------------------------------------------------
/**
 * Returns a GLSQL object that can be used for constructing a "delete" statement.
 * @param tableName The table that will be used in the "delete" statement.
 * @param dataSourceName The data source name for the data source against which the delete will be
 * executed.
 * @return A GLSQL object that can be used for constructing a "delete" statement.
 */
public static GLSQL delete(final String tableName, final String dataSourceName) {
  return new GLSQL(EGLSQLType.Delete, tableName, dataSourceName);
}
//--------------------------------------------------------------------------------------------------
/**
 * Returns a GLSQL object that can be used to insert data into the default data source.
 * @param table The table that is the target of the insert.
 * @param ignoreDuplicates If <code>true</code> then attempts to insert rows that already exist
 * (based upon the unique indexes for the table) will be ignored.
 * @return The GLSQL object.
 */
public static GLSQL insert(final IGLTable table, final boolean ignoreDuplicates) {
  return GLSQL.insert(table, null, ignoreDuplicates);
}
//--------------------------------------------------------------------------------------------------
/**
 * Returns a GLSQL object that can be used to insert data into the default data source.
 * @param tableName The table that is the target of the insert.
 * @param ignoreDuplicates If <code>true</code> then attempts to insert rows that already exist
 * (based upon the unique indexes for the table) will be ignored.
 * @return The GLSQL object.
 */
public static GLSQL insert(final String tableName, final boolean ignoreDuplicates) {
  return GLSQL.insert(tableName, null, ignoreDuplicates);
}
//--------------------------------------------------------------------------------------------------
/**
 * Returns a GLSQL object that can be used to insert data.
 * @param table The table that is the target of the insert.
 * @param dataSourceName The data source that is the target for the inserted data.
 * @param ignoreDuplicates If <code>true</code> then attempts to insert rows that already exist
 * (based upon the unique indexes for the table) will be ignored.
 * @return The GLSQL object.
 */
public static GLSQL insert(final IGLTable table, final String dataSourceName,
                           final boolean ignoreDuplicates) {
  final GLSQL result = new GLSQL(EGLSQLType.Insert, table, dataSourceName);
  result._ignoreDuplicates = ignoreDuplicates;
  return result;
}
//--------------------------------------------------------------------------------------------------
/**
 * Returns a GLSQL object that can be used to insert data.
 * @param tableName The table that is the target of the insert.
 * @param dataSourceName The data source that is the target for the inserted data.
 * @param ignoreDuplicates If <code>true</code> then attempts to insert rows that already exist
 * (based upon the unique indexes for the table) will be ignored.
 * @return The GLSQL object.
 */
public static GLSQL insert(final String tableName, final String dataSourceName,
                           final boolean ignoreDuplicates) {
  final GLSQL result = new GLSQL(EGLSQLType.Insert, tableName, dataSourceName);
  result._ignoreDuplicates = ignoreDuplicates;
  return result;
}
//--------------------------------------------------------------------------------------------------
/**
 * Returns a GLSQL object that can be used for constructing a "select" statement against the default
 * data source.
 * @return A GLSQL object that can be used for constructing a "select" statement.
 */
public static GLSQL select() {
  return GLSQL.select((String)null);
}
//--------------------------------------------------------------------------------------------------
/**
 * Returns a GLSQL object that can be used for constructing a "select" statement.
 * @param firstColumn A column that will be selected.
 * @param columns Additional columns that will be selected.
 * @return A GLSQL object that can be used for constructing a "select" statement.
 */
public static GLSQL select(final IGLColumn firstColumn, final IGLColumn... columns) {
  final GLSQL result = GLSQL.select(firstColumn.toString());
  if (columns != null) {
    for (final IGLColumn column : columns) {
      result.selectColumns(column.toString());
    }
  }
  return result;
}
//--------------------------------------------------------------------------------------------------
/**
 * Returns a GLSQL object that can be used for constructing a "select" statement.
 * @param columnSQL The SQL for the columns to be selected.
 * @return A GLSQL object that can be used for constructing a "select" statement.
 */
public static GLSQL select(final String... columnSQL) {
  final GLSQL result = new GLSQL(EGLSQLType.Select, (IGLTable)null, null);
  result.selectColumns(columnSQL);
  return result;
}
//--------------------------------------------------------------------------------------------------
/**
 * Returns a GLSQL object that can be used for constructing a "select" statement.
 * @param columnCollection The columns to be selected.
 * @return A GLSQL object that can be used for constructing a "select" statement.
 */
public static GLSQL select(final Collection<String> columnCollection) {
  final GLSQL result = new GLSQL(EGLSQLType.Select, (IGLTable)null, null);
  result.selectColumns(columnCollection);
  return result;
}
//--------------------------------------------------------------------------------------------------
/**
 * Returns a GLSQL object that can be used to execute an "update" statement against the default data
 * source.
 * @param table The table that is to be used in the "update" statement.
 * @return The GLSQL object.
 */
public static GLSQL update(final IGLTable table) {
  return GLSQL.update(table, null);
}
//--------------------------------------------------------------------------------------------------
/**
 * Returns a GLSQL object that can be used to execute an "update" statement against the default data
 * source.
 * @param tableName The table that is to be used in the "update" statement.
 * @return The GLSQL object.
 */
public static GLSQL update(final String tableName) {
  return GLSQL.update(tableName, null);
}
//--------------------------------------------------------------------------------------------------
/**
 * Returns a GLSQL object that can be used to execute an "update" statement.
 * @param table The table that is to be used in the "update" statement.
 * @param dataSourceName The data source name for the data source that will be used.
 * @return The GLSQL object.
 */
public static GLSQL update(final IGLTable table, final String dataSourceName) {
  return new GLSQL(EGLSQLType.Update, table, dataSourceName);
}
//--------------------------------------------------------------------------------------------------
/**
 * Returns a GLSQL object that can be used to execute an "update" statement.
 * @param tableName The table that is to be used in the "update" statement.
 * @param dataSourceName The data source name for the data source that will be used.
 * @return The GLSQL object.
 */
public static GLSQL update(final String tableName, final String dataSourceName) {
  return new GLSQL(EGLSQLType.Update, tableName, dataSourceName);
}
//--------------------------------------------------------------------------------------------------
private GLSQL(final EGLSQLType sqlType, final IGLTable table, final String dataSourceName) {
  this(sqlType, table == null ? null : table.toString(), dataSourceName);
  setTable(table, dataSourceName);
}
//--------------------------------------------------------------------------------------------------
private GLSQL(final EGLSQLType sqlType, final String tableName, final String dataSourceName) {
  _sqlType = sqlType;
  setTableName(tableName, dataSourceName);
  _columnMap = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
  _whereClauseList = new ArrayList<GLWhereClause>();
}
//--------------------------------------------------------------------------------------------------
/**
 * Adds hints to the SQL "from" clause (e.g., "index=XIE6Call").
 * @param fromHints The hints to be added to the SQL.
 * @return The GLSQL object.
 */
public GLSQL addFromHints(final String... fromHints) throws GLDBException {
  ensureSQLTypeIn(EGLSQLType.Select);
  for (final String fromHint : fromHints) {
    _fromHint = (GLUtil.isBlank(_fromHint) ? "" : _fromHint + ",") + fromHint;
  }
  return this;
}
//--------------------------------------------------------------------------------------------------
private void createGroupByList() {
  if (_groupByList == null) {
    _groupByList = new ArrayList<String>();
  }
}
//--------------------------------------------------------------------------------------------------
private void ensureSQLTypeIn(final EGLSQLType... sqlTypes) throws GLDBException {
  boolean sqlTypeFound = false;
  for (final EGLSQLType sqlType : sqlTypes) {
    if (_sqlType == sqlType) {
      sqlTypeFound = true;
      break;
    }
  }
  if (!sqlTypeFound) {
    throw new GLDBException(EGLDBException.InvalidSQLRequest);
  }
}
//--------------------------------------------------------------------------------------------------
/**
 * Executes a "select" statement.
 * @param listStore The ListStore that will receive the results of the "select".
 * @param callback The object that contains the success and failure callback methods.
 */
public void execute(final GLListStore listStore, final IGLSQLSelectCallback callback) {
  GLUtil.getRemoteService().select(toXMLSB().toString(), new AsyncCallback<String>() {
    @Override
    public void onFailure(final Throwable t) {
      callback.onFailure(t);
    }
    @Override
    public void onSuccess(final String selectResult) {
      try {
        listStore.clear();
        final String[] selectRows = selectResult.split("\n");
        GLRecordDef recordDef = null;
        boolean firstRow = true;
        for (final String row : selectRows) {
          if (firstRow) {
            recordDef = new GLRecordDef(_table, row.split(","), _keyColumn);
            firstRow = false;
          }
          else {
            @SuppressWarnings({"unchecked", "rawtypes"})
            final GLRecord record = new GLRecord(recordDef, (ArrayList)GLCSV.extract(row));
            listStore.add(record);
          }
        }
        callback.onSuccess();
      }
      catch (final GLCSVException csve) {
        onFailure(csve);
      }
    }
  });
}
//--------------------------------------------------------------------------------------------------
/**
 * Adds the table for the "from" clause to a "select" statement.
 * @param table The table that is to be used in the "select" statement.
 * @return The GLSQL object.
 */
public GLSQL from(final IGLTable table) throws GLDBException {
  return from(table, null);
}
//--------------------------------------------------------------------------------------------------
/**
 * Adds the table for the "from" clause to a "select" statement.
 * @param table The table that is to be used in the "select" statement.
 * @param dataSourceName The data source name for this query. If this is null then the default data
 * source will be used.
 * @return The GLSQL object.
 */
public GLSQL from(final IGLTable table, final String dataSourceName) throws GLDBException {
  ensureSQLTypeIn(EGLSQLType.Select);
  setTable(table, dataSourceName);
  setKeyColumn(table.getPrimaryKeyColumn());
  return this;
}
//--------------------------------------------------------------------------------------------------
/**
 * Returns the main table that will be used by the SQL statement.
 * @return The main table for the SQL statement.
 */
public IGLTable getTable() {
  return _table;
}
//--------------------------------------------------------------------------------------------------
/**
 * Adds a table/column to the "group by" clause for a "select" statement.
 * @param table The table for the "group by".
 * @param column The column the the "group by".
 * @return The GLSQL object.
 */
public GLSQL groupBy(final IGLTable table, final IGLColumn column) throws GLDBException {
  ensureSQLTypeIn(EGLSQLType.Select);
  groupBy(table.toString() + "." + column.toString());
  return this;
}
//--------------------------------------------------------------------------------------------------
/**
 * Adds a literal value to the "group by" clause for a "select" statement.
 * @param groupByClause A literal value to be added to the "group by" clause.
 * @return The GLSQL object.
 */
public GLSQL groupBy(final CharSequence groupByClause) throws GLDBException {
  ensureSQLTypeIn(EGLSQLType.Select);
  if (GLUtil.isBlank(groupByClause)) {
    return this;
  }
  createGroupByList();
  _groupByList.add(groupByClause.toString());
  return this;
}
//--------------------------------------------------------------------------------------------------
/**
 * Adds a "join" clause to the "select" statement.
 * @param joinType The type of join (i.e., "inner", "outer", etc.).
 * @param joinTable The table to be used in the join.
 * @param condition The condition for the join.
 * @return The GLSQL object.
 */
public GLSQL join(final EGLJoinType joinType, final String joinTable, final CharSequence condition)
  throws GLDBException {
  ensureSQLTypeIn(EGLSQLType.Select);
  final GLJoinDef joinDef = new GLJoinDef(joinType, joinTable, condition);
  if (_joinDefList == null) {
    _joinDefList = new ArrayList<GLJoinDef>();
  }
  _joinDefList.add(joinDef);
  return this;
}
//--------------------------------------------------------------------------------------------------
/**
 * Adds a "join" clause to the "select" statement.
 * @param joinType The type of join (i.e., "inner", "outer", etc.).
 * @param joinTable The table to be used in the join.
 * @param condition The condition for the join.
 * @return The GLSQL object.
 */
public GLSQL join(final EGLJoinType joinType, final IGLTable joinTable, final CharSequence condition)
  throws GLDBException {
  return join(joinType, joinTable.toString(), condition);
}
//--------------------------------------------------------------------------------------------------
/**
 * Adds an entry to the "order by" clause using a table and column enum entry.
 * @param table The table for the "order by" clause.
 * @param column The column for the "order by" clause.
 * @param ascending An indication of whether the "order by" entry will be ascending or descending.
 * @return The GLSQL object.
 */
public GLSQL orderBy(final IGLTable table, final IGLColumn column, final boolean ascending)
  throws GLDBException {
  orderBy(table.toString() + "." + column.toString() + (ascending ? "" : " desc"));
  return this;
}
//--------------------------------------------------------------------------------------------------
/**
 * Adds an entry to the "order by" clause using a literal "order by" string.
 * @param orderByClause The literal "order by" clause, for example, "TransactionDate desc".
 * @return The GLSQL object.
 */
public GLSQL orderBy(final String orderByClause) throws GLDBException {
  ensureSQLTypeIn(EGLSQLType.Select);
  if (_orderByClause == null) {
    _orderByClause = "";
  }
  _orderByClause += (_orderByClause.length() > 0 ? "," : "") + orderByClause;
  return this;
}
//--------------------------------------------------------------------------------------------------
/**
 * Adds columns to the "select" clause in a "select" statement.
 * @param firstColumn The first column to be added to the "select" statement.
 * @param columns Additional columns to be added to the "select" statement.
 * @return The GLSQL object.
 */
public GLSQL selectColumns(final IGLColumn firstColumn, final IGLColumn... columns) {
  selectColumns(firstColumn.toString());
  if (columns != null) {
    for (final IGLColumn column : columns) {
      selectColumns(column.toString());
    }
  }
  return this;
}
//--------------------------------------------------------------------------------------------------
/**
 * Adds string values to the columns in a "select" statement.
 * @param columnSQL The string values to be added to the "select" clause.
 * @return The GLSQL object.
 */
public GLSQL selectColumns(final String... columnSQL) {
  if (columnSQL == null || columnSQL.length == 0) {
    _columnMap.put("*", null);
  }
  else {
    for (final String column : columnSQL) {
      if (GLUtil.isBlank(column)) {
        _columnMap.put("*", null);
      }
      else {
        _columnMap.put(column, null);
      }
    }
  }
  return this;
}
//--------------------------------------------------------------------------------------------------
/**
 * Adds string values to the columns in a "select" statement.
 * @param columnCollection The string values to be added to the "select" clause.
 * @return The GLSQL object.
 */
public GLSQL selectColumns(final Collection<String> columnCollection) {
  if (columnCollection == null || columnCollection.size() == 0) {
    _columnMap.put("*", null);
  }
  else {
    for (final String column : columnCollection) {
      if (GLUtil.isBlank(column)) {
        _columnMap.put("*", null);
      }
      else {
        _columnMap.put(column, null);
      }
    }
  }
  return this;
}
//--------------------------------------------------------------------------------------------------
private void setKeyColumn(final IGLColumn keyColumn) {
  _keyColumn = keyColumn;
}
//--------------------------------------------------------------------------------------------------
/**
 * Ensures that the query returns no more than the specified number of rows.
 * @param maxNumberOfRows The maximum number of rows to return from the query.
 * @return The <code>GLSQL</code> object.
 */
public GLSQL setMaxNumberOfRows(final int maxNumberOfRows) throws GLDBException {
  ensureSQLTypeIn(EGLSQLType.Select);
  _maxNumberOfRows = maxNumberOfRows;
  return this;
}
//--------------------------------------------------------------------------------------------------
/**
 * Sets the "order by" clause in a "select" statement.
 * @param orderByClause The new "order by" clause.
 * @return The GLSQL object.
 */
public GLSQL setOrderBy(final String orderByClause) throws GLDBException {
  ensureSQLTypeIn(EGLSQLType.Select);
  if (orderByClause == null || orderByClause.length() == 0) {
    _orderByClause = null;
  }
  else {
    _orderByClause = orderByClause;
  }
  return this;
}
//--------------------------------------------------------------------------------------------------
private void setTable(final IGLTable table, final String dataSourceName) {
  if (table != null) {
    _table = table;
    setTableName(_table.toString(), dataSourceName);
  }
}
//--------------------------------------------------------------------------------------------------
private void setTableName(final String tableName, final String dataSourceName) {
  if (tableName != null) {
    _tableName = tableName;
    _dataSourceName = dataSourceName;
  }
}
//--------------------------------------------------------------------------------------------------
/**
 * Sets a value to be used in the "values" clause of an update statement.
 * @param column The column associated with the value.
 * @param value The value to be inserted. The data type of the object will be used to determine the
 * format of the value that will be used in the SQL. Note that the actual data type of the column in
 * the database is not used to determine the format used (for example, if a numeric value is
 * supplied for a character column the value in the insert statement will not be quoted).
 * @return The <code>GLSQL</code> object.
 */
public GLSQL setValue(final IGLColumn column, final Object value) throws GLDBException {
  return setValue(column, value, true);
}
//--------------------------------------------------------------------------------------------------
/**
 * Sets a value to be used in the "values" clause of an update statement. If the statement is an
 * "insert" and the value is null or a blank string then no value will be added to the SQL.
 * @param value The value to be inserted. The data type of the object will be used to determine the
 * format of the value that will be used in the SQL. Note that the actual data type of the column in
 * the database is not used to determine the format used (for example, if a numeric value is
 * supplied for a character column the value in the insert statement will not be quoted).
 * @param useQuotedStrings Indicates whether string values should be quoted in the SQL.
 * @return The <code>GLSQL</code> object.
 */
public GLSQL setValue(final IGLColumn column, final Object value, final boolean useQuotedStrings)
  throws GLDBException {
  ensureSQLTypeIn(EGLSQLType.Insert, EGLSQLType.Update);
  _columnMap.put(column.toString(), value.toString());
  return this;
}
//--------------------------------------------------------------------------------------------------
/**
 * Sets an expression to be used in the "values" clause of an update statement.
 * @param column The column associated with the value.
 * @param expression The expression to be used in the assignment statement of an "update" statement.
 * The expression is supplied in the form of a string which will be sent without modification in the
 * SQL statement.
 * @return The <code>GLSQL</code> object.
 */
public GLSQL setValueExp(final IGLColumn column, final String expression) throws GLDBException {
  return setValue(column, expression, false);
}
//--------------------------------------------------------------------------------------------------
@Override
public String toString() {
  return "Type:" + _sqlType + " Columns:" + _columnMap + " Table:" + _tableName + " Where:" +
         _whereClauseList;
}
//--------------------------------------------------------------------------------------------------
public StringBuilder toXMLSB() {
  final StringBuilder result = new StringBuilder(200);
  result.append("<").append(_sqlType.name()).append(" Columns='");
  boolean firstColumn = true;
  for (final String columnName : _columnMap.keySet()) {
    result.append(firstColumn ? "" : ",").append(columnName);
    firstColumn = false;
  }
  result.append("' Table='").append(_tableName).append("'");
  if (!GLUtil.isBlank(_dataSourceName)) {
    result.append(" DataSource='").append(_dataSourceName).append("'");
  }
  if (_groupByList != null && _groupByList.size() > 0) {
    result.append(" GroupBy='");
    firstColumn = true;
    for (final String columnName : _groupByList) {
      result.append(firstColumn ? "" : ",").append(columnName);
      firstColumn = false;
    }
    result.append("'");
  }
  if (!GLUtil.isBlank(_orderByClause)) {
    result.append(" OrderBy='").append(_orderByClause).append("'");
  }
  if (_ignoreDuplicates) {
    result.append(" IgnoreDuplicates='Y'");
  }
  if (_maxNumberOfRows > 0) {
    result.append(" MaxRows='").append(_maxNumberOfRows).append("'");
  }
  result.append(">");
  if (_joinDefList != null && _joinDefList.size() > 0) {
    for (final GLJoinDef joinDef : _joinDefList) {
      result.append("<Join Type='").append(joinDef._joinType.name()).append("'");
      result.append(" Table='").append(joinDef._joinTable).append("'");
      result.append(" Condition='").append(joinDef._condition).append("'");
    }
  }
  for (final GLWhereClause whereClause : _whereClauseList) {
    result.append("<Where").append(whereClause._conjunction.name());
    if (whereClause._openParens > 0) {
      result.append(" Open='").append(whereClause._openParens).append("'");
    }
    if (whereClause._expression == null) {
      result.append(" Column='").append(whereClause._columnName).append("'");
      result.append(" Op='").append(whereClause._operator.getSQL()).append("'");
      if (whereClause._value != null) {
        result.append(" Value='").append(whereClause._value.toString()).append("'");
      }
    }
    else {
      result.append(" Expression='").append(whereClause._expression).append("'");
    }
    if (whereClause._closeParens > 0) {
      result.append(" Close='").append(whereClause._closeParens).append("'");
    }
    result.append("/>");
  }
  result.append("</").append(_sqlType.name()).append(">");
  return result;
}
//--------------------------------------------------------------------------------------------------
/**
 * Adds a component to the "where" clause that represents a test for a specific column.
 * @param conjunction The conjunction (i.e., "and" or "or").
 * @param openParens The number of open parentheses to be added.
 * @param columnName The column that will precede the operator.
 * @param operator The conditional operator.
 * @param value The value against which the column will be compared. If the conditional operator is
 * "is null" (or a variant of "is null") then this parameter can be "null". If the conditional
 * operator is "in" (or a variable of "in") then this parameter should contain a list of values in a
 * string without the open and close parentheses (e.g., "1,2,3").
 * @param closeParens The number of close parentheses to be added.
 * @return The GLSQL object.
 */
public GLSQL where(final EGLDBConj conjunction, final int openParens, final String columnName,
                   final EGLDBOp operator, final Object value, final int closeParens) {
  _whereClauseList.add(new GLWhereClause(conjunction, openParens, columnName, operator, value,
                                         closeParens));
  return this;
}
//--------------------------------------------------------------------------------------------------
/**
 * Adds a component to the "where" clause that represents a test for a specific column.
 * @param conjunction The conjunction (i.e., "and" or "or").
 * @param openParens The number of open parentheses to be added.
 * @param column The column that will precede the operator.
 * @param operator The conditional operator.
 * @param value The value against which the column will be compared. If the conditional operator is
 * "is null" (or a variant of "is null") then this parameter can be "null". If the conditional
 * operator is "in" (or a variable of "in") then this parameter should contain a list of values in a
 * string without the open and close parentheses (e.g., "1,2,3").
 * @param closeParens The number of close parentheses to be added.
 * @return The GLSQL object.
 */
public GLSQL where(final EGLDBConj conjunction, final int openParens, final IGLColumn column,
                   final EGLDBOp operator, final Object value, final int closeParens) {
  return where(conjunction, openParens, column.toString(), operator, value, closeParens);
}
//--------------------------------------------------------------------------------------------------
/**
 * Adds a component to the "where" clause.
 * @param conjunction The conjunction (i.e., "and" or "or").
 * @param openParens The number of open parentheses to be added.
 * @param expression A literal string that will be added verbatim to the "where" clause.
 * @param closeParens The number of close parentheses to be added.
 * @return The GLSQL object.
 */
public GLSQL where(final EGLDBConj conjunction, final int openParens,
                   final CharSequence expression, final int closeParens) throws GLDBException {
  ensureSQLTypeIn(EGLSQLType.Delete, EGLSQLType.Select, EGLSQLType.Update);
  _whereClauseList.add(new GLWhereClause(conjunction, openParens, expression, closeParens));
  return this;
}
//--------------------------------------------------------------------------------------------------
/**
 * Adds a component to the "where" clause using "and" as the conjunction.
 * @param openParens The number of open parentheses to be added.
 * @param expression A literal string that will be added verbatim to the "where" clause.
 * @param closeParens The number of close parentheses to be added.
 * @return The GLSQL object.
 */
public GLSQL whereAnd(final int openParens, final CharSequence expression, final int closeParens)
  throws GLDBException {
  return where(EGLDBConj.And, openParens, expression, closeParens);
}
//--------------------------------------------------------------------------------------------------
/**
 * Adds a component to the "where" clause that represents a test for a specific column.
 * @param column The column that will precede the operator.
 * @param operator The conditional operator.
 * @param value The value against which the column will be compared. If the conditional operator is
 * "is null" (or a variant of "is null") then this parameter can be "null". If the conditional
 * operator is "in" (or a variable of "in") then this parameter should contain a list of values in a
 * string without the open and close parentheses (e.g., "1,2,3").
 * @return The GLSQL object.
 */
public GLSQL whereAnd(final IGLColumn column, final EGLDBOp operator, final Object value) {
  return where(EGLDBConj.And, 0, column, operator, value, 0);
}
//--------------------------------------------------------------------------------------------------
/**
 * Adds a component to the "where" clause that represents a test for a specific column.
 * @param openParens The number of open parentheses to be added.
 * @param column The column that will precede the operator.
 * @param operator The conditional operator.
 * @param value The value against which the column will be compared. If the conditional operator is
 * "is null" (or a variant of "is null") then this parameter can be "null". If the conditional
 * operator is "in" (or a variable of "in") then this parameter should contain a list of values in a
 * string without the open and close parentheses (e.g., "1,2,3").
 * @param closeParens The number of close parentheses to be added.
 * @return The GLSQL object.
 */
public GLSQL whereAnd(final int openParens, final IGLColumn column, final EGLDBOp operator,
                      final Object value, final int closeParens) {
  return where(EGLDBConj.And, openParens, column, operator, value, closeParens);
}
//--------------------------------------------------------------------------------------------------
/**
 * Adds a component to the "where" clause using "or" as the conjunction.
 * @param openParens The number of open parentheses to be added.
 * @param expression A literal string that will be added verbatim to the "where" clause.
 * @param closeParens The number of close parentheses to be added.
 * @return The GLSQL object.
 */
public GLSQL whereOr(final int openParens, final CharSequence expression, final int closeParens)
  throws GLDBException {
  return where(EGLDBConj.Or, openParens, expression, closeParens);
}
//--------------------------------------------------------------------------------------------------
/**
 * Adds a component to the "where" clause that represents a test for a specific column.
 * @param column The column that will precede the operator.
 * @param operator The conditional operator.
 * @param value The value against which the column will be compared. If the conditional operator is
 * "is null" (or a variant of "is null") then this parameter can be "null". If the conditional
 * operator is "in" (or a variable of "in") then this parameter should contain a list of values in a
 * string without the open and close parentheses (e.g., "1,2,3").
 * @return The GLSQL object.
 */
public GLSQL whereOr(final IGLColumn column, final EGLDBOp operator, final Object value) {
  return where(EGLDBConj.Or, 0, column, operator, value, 0);
}
//--------------------------------------------------------------------------------------------------
/**
 * Adds a component to the "where" clause that represents a test for a specific column.
 * @param openParens The number of open parentheses to be added.
 * @param column The column that will precede the operator.
 * @param operator The conditional operator.
 * @param value The value against which the column will be compared. If the conditional operator is
 * "is null" (or a variant of "is null") then this parameter can be "null". If the conditional
 * operator is "in" (or a variable of "in") then this parameter should contain a list of values in a
 * string without the open and close parentheses (e.g., "1,2,3").
 * @param closeParens The number of close parentheses to be added.
 * @return The GLSQL object.
 */
public GLSQL whereOr(final int openParens, final IGLColumn column, final EGLDBOp operator,
                     final Object value, final int closeParens) {
  return where(EGLDBConj.Or, openParens, column, operator, value, closeParens);
}
//--------------------------------------------------------------------------------------------------
}