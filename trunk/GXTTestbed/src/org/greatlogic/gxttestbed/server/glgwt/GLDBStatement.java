package org.greatlogic.gxttestbed.server.glgwt;

import org.apache.commons.lang3.StringUtils;
import org.greatlogic.gxttestbed.shared.IDBEnums.EGXTTestbedTable;
import org.greatlogic.gxttestbed.shared.glgwt.IGLTable;
import com.greatlogic.glbase.gldb.GLDBException;
import com.greatlogic.glbase.gldb.GLSQL;
import com.greatlogic.glbase.gllib.GLLog;
import com.greatlogic.glbase.glxml.GLXML;
import com.greatlogic.glbase.glxml.GLXMLException;

class GLDBStatement {
//--------------------------------------------------------------------------------------------------
public static void delete(final String deletes) {
  GLLog.infoDetail("Delete:" + deletes);
  try {
    final String[] tableLines = deletes.split("\n");
    for (final String tableLine : tableLines) {
      final int colonIndex = tableLine.indexOf(':');
      if (colonIndex > 0) {
        final int slashIndex = tableLine.indexOf('/', colonIndex + 2);
        if (slashIndex > 0 && slashIndex < tableLine.length() - 1) {
          final IGLTable table = EGXTTestbedTable.valueOf(tableLine.substring(colonIndex + 1, //
                                                                              slashIndex));
          final GLSQL deleteSQL = GLSQL.delete(table.toString());
          deleteSQL.whereAnd(0, table.getPrimaryKeyColumn().toString() + " in (" + //
                                tableLine.substring(slashIndex + 1) + ")", 0);
          GLLog.debug(deleteSQL.getSQL());
          //        deleteSQL.execute();
        }
      }
    }
  }
  catch (final GLDBException dbe) {
    GLLog.major("Error executing 'delete'", dbe);
  }
}
//--------------------------------------------------------------------------------------------------
public static void insert(final String inserts) {
  GLLog.infoDetail("Inserts:");
  GLLog.infoDetail(inserts);
  insertOrUpdateRows(true, inserts);
}
//--------------------------------------------------------------------------------------------------
private static void insertOrUpdateRows(final boolean insert, final String insertsOrUpdates) {
  try {
    final String[] rows = insertsOrUpdates.split("\n");
    for (final String row : rows) {
      int colonIndex = row.indexOf(':');
      if (colonIndex > 0) {
        final int slashIndex = row.indexOf('/', colonIndex);
        if (slashIndex > colonIndex + 1) {
          final IGLTable table = EGXTTestbedTable.valueOf(row.substring(colonIndex + 1, //
                                                                        slashIndex));
          colonIndex = row.indexOf(':', slashIndex);
          if (colonIndex > slashIndex + 1 && colonIndex < row.length() - 1) {
            final String keyValue = row.substring(slashIndex + 1, colonIndex);
            final String[] columnNamesAndValues = row.substring(colonIndex + 1).split(";");
            final GLSQL sql;
            if (insert) {
              sql = GLSQL.insert(table.toString(), false);
              sql.setValue(table.getPrimaryKeyColumn().toString(), keyValue);
            }
            else {
              sql = GLSQL.update(table.toString());
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
              sql.whereAnd(0, table.getPrimaryKeyColumn().toString() + "=" + keyValue, 0);
            }
            sql.execute();
          }
        }
      }
    }
  }
  catch (final GLDBException dbe) {
    GLLog.major("Error executing " + (insert ? "'insert'" : "'update'"), dbe);
  }
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