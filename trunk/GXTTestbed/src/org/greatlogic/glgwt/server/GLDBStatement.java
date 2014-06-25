package org.greatlogic.glgwt.server;

import org.apache.commons.lang3.StringUtils;
import com.greatlogic.glbase.gldb.GLDBException;
import com.greatlogic.glbase.gldb.GLSQL;
import com.greatlogic.glbase.gllib.GLLog;
import com.greatlogic.glbase.glxml.GLXML;
import com.greatlogic.glbase.glxml.GLXMLException;

class GLDBStatement {
//==================================================================================================
private static abstract class LineIterator {
private LineIterator(final String lines) {
  final String[] tableLines = lines.split("\n");
  for (final String tableLine : tableLines) {
    final int tableLineLength = tableLine.length();
    if (tableLine.startsWith("Table:") && tableLineLength > 10) {
      final int slashIndex = tableLine.indexOf('/', 7);
      if (slashIndex > 0 && slashIndex < tableLineLength - 3) {
        final String tableName = tableLine.substring(6, slashIndex);
        final int colonIndex = tableLine.indexOf(':', slashIndex + 2);
        if (colonIndex > 0 && colonIndex < tableLineLength - 1) {
          String keyColumnName = tableLine.substring(slashIndex + 1, colonIndex);
          final int equalsIndex = keyColumnName.indexOf('=', 1);
          int restOfLineIndex;
          if (equalsIndex > 0) {
            keyColumnName = keyColumnName.substring(0, equalsIndex);
            restOfLineIndex = slashIndex + equalsIndex + 2;
          }
          else {
            restOfLineIndex = colonIndex + 1;
          }
          final String restOfLine = tableLine.substring(restOfLineIndex);
          processLine(tableLine, tableName, keyColumnName, restOfLine);
        }
      }
    }
  }
}
abstract void processLine(final String line, final String tableName, final String keyColumnName,
                          final String restOfLine);
}
//==================================================================================================
/**
 * Deletes rows based upon linefeed-separated entries in a string. Entries are in the format:
 * 
 * <pre>
 * Table:table_name1/key_column_name1:key1,key2,key3<linefeed>
 * </pre>
 * @param deletes The rows to be deleted.
 */
public static void delete(final String deletes) {
  GLLog.infoDetail("Delete:" + deletes);
  new LineIterator(deletes) {
    @Override
    /**
     * The restOfLine parameter will be in the format:
     * <pre>
     * key_value1,key_value2,key_value3
     * </pre>
     */
    void processLine(final String line, final String tableName, final String keyColumnName,
                     final String restOfLine) {
      try {
        final GLSQL deleteSQL = GLSQL.delete(tableName);
        deleteSQL.whereAnd(0, keyColumnName + " in (" + restOfLine + ")", 0);
        GLLog.debug(deleteSQL.getSQL());
        deleteSQL.execute();
      }
      catch (final GLDBException dbe) {
        GLLog.major("Error executing 'delete' for:" + line, dbe);
      }
    }
  };
}
//--------------------------------------------------------------------------------------------------
public static void insert(final String inserts) {
  GLLog.infoDetail("Inserts:");
  GLLog.infoDetail(inserts);
  insertOrUpdateRows(true, inserts);
}
//--------------------------------------------------------------------------------------------------
/**
 * Inserts or updates rows based upon linefeed-separated entries in a string. Entries are in the
 * format:
 * 
 * <pre>
 * Table:table_name1/key_column_name1=key1:column1=value1;column2=value2;column3=value3<linefeed>
 * </pre>
 * @param insertsOrUpdates The rows to be inserted or updated.
 */
private static void insertOrUpdateRows(final boolean insert, final String insertsOrUpdates) {
  new LineIterator(insertsOrUpdates) {
    @Override
    /**
     * The restOfLine parameter will be in the format:
     * <pre>
     * key1:column1=value1;column2=value2
     * </pre>
     */
    void processLine(final String line, final String tableName, final String keyColumnName,
                     final String restOfLine) {
      try {
        final int colonIndex = restOfLine.indexOf(':', 1);
        if (colonIndex > 1 && colonIndex < restOfLine.length() - 3) {
          final String keyValue = restOfLine.substring(0, colonIndex);
          final String[] columnNamesAndValues = restOfLine.substring(colonIndex + 1).split(";");
          final GLSQL sql;
          if (insert) {
            sql = GLSQL.insert(tableName, false);
            sql.setValue(keyColumnName, keyValue);
          }
          else {
            sql = GLSQL.update(tableName);
          }
          for (final String columnNameAndValue : columnNamesAndValues) {
            final int equalsIndex = columnNameAndValue.indexOf('=');
            if (equalsIndex > 0) {
              final String columnName = columnNameAndValue.substring(0, equalsIndex);
              final String value = columnNameAndValue.substring(equalsIndex + 1);
              sql.setValue(columnName, value.isEmpty() ? null : value);
            }
          }
          if (!insert) {
            sql.whereAnd(0, keyColumnName + "=" + keyValue, 0);
          }
          sql.execute();
        }
      }
      catch (final GLDBException dbe) {
        GLLog.major("Error executing " + (insert ? "'insert'" : "'update'"), dbe);
      }
    }
  };
}
//--------------------------------------------------------------------------------------------------
public static String select(final String xmlRequest) {
  GLLog.debug(xmlRequest);
  final StringBuilder result = new StringBuilder();
  try {
    final GLXML xml = new GLXML(xmlRequest);
    final GLSQL sql = GLSQL.selectUsingXML(xml);
    sql.open();
    try {
      result.append(StringUtils.join(sql.getColumnNameIterable(), ',')).append('\n');
      while (sql.next(false)) {
        sql.getRowAsCSV(result);
        result.append('\n');
      }
    }
    finally {
      sql.close();
    }
  }
  catch (final GLDBException dbe) {
    GLLog.major("Error executing 'select'", dbe);
    result.setLength(0);
    result.append("error");
  }
  catch (final GLXMLException xmle) {
    GLLog.major("Error processing XML for 'select'", xmle);
    result.setLength(0);
    result.append("error");
  }
  return result.toString();
}
//--------------------------------------------------------------------------------------------------
public static void update(final String updates) {
  GLLog.infoDetail("Updates:");
  GLLog.infoDetail(updates);
  insertOrUpdateRows(false, updates);
}
//--------------------------------------------------------------------------------------------------
}