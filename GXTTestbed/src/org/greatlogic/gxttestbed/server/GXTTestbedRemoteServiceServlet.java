package org.greatlogic.gxttestbed.server;

import org.greatlogic.gxttestbed.shared.IRemoteService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class GXTTestbedRemoteServiceServlet extends RemoteServiceServlet implements IRemoteService {
//--------------------------------------------------------------------------------------------------
/**
 * Deletes any number of rows from any number of tables. The format for the "deletes" parameter is:
 * 
 * <pre>
 * Table:table_name1/key1,key2,key3<linefeed>
 * Table:table_name2/key1,key2,key3<linefeed>
 * Table:table_name3/key1,key2,key3<linefeed>
 * </pre>
 * 
 * Each line in the "deletes" string represents any number of rows to be deleted from a single
 * table.
 */
@Override
public void delete(final String deletes) {
  //  GLLog.infoDetail("Delete:" + deletes);
  //  try {
  //    final String[] tableLines = deletes.split("\n");
  //    for (final String tableLine : tableLines) {
  //      final int colonIndex = tableLine.indexOf(':');
  //      if (colonIndex > 0) {
  //        final int slashIndex = tableLine.indexOf('/', colonIndex + 2);
  //        if (slashIndex > 0 && slashIndex < tableLine.length() - 1) {
  //          final IGLTable table = EGXTTestbedTable.valueOf(tableLine.substring(colonIndex + 1, //
  //                                                                               slashIndex));
  //          final GLSQL deleteSQL = GLSQL.delete(table.toString());
  //          deleteSQL.whereAnd(0, table.getPrimaryKeyColumn().toString() + " in (" + //
  //                                tableLine.substring(slashIndex + 1) + ")", 0);
  //          GLLog.debug(deleteSQL.getSQL());
  //          //        deleteSQL.execute();
  //        }
  //      }
  //    }
  //  }
  //  catch (final GLDBException dbe) {
  //    GLLog.major("Error executing 'delete'", dbe);
  //  }
}
//--------------------------------------------------------------------------------------------------
@Override
public void log(final int logLevelId, final String location, final String message) {
  //  GLLog.log(EGLLogLevel.lookupUsingPriority(logLevelId * 10), location + "=>" + message);
}
//--------------------------------------------------------------------------------------------------
/**
 * Attempts to log in using the supplied login name and password.
 * @param loginName The login name that will be used for the login attempt.
 * @param password The password that will be used for the login attempt (this is the plain text
 * password, not the encrypted hash value).
 * @return The id of the Person row, or zero if the login request fails.
 */
@Override
public Integer login(final String loginName, final String password) {
  // find the user using the loginName and password
  // if the user isn't found {
  //    GLLog.infoSummary("Login failed for login name:" + loginName);
  //    return 0;
  // }
  //  GLLog.infoSummary("Login succeeded for login name:" + user.getLoginName());
  //  getThreadLocalRequest().getSession().setAttribute(ESessionAttribute.LoginUser.name(), user);
  //  return user.getUserId();
  return 0;
}
//--------------------------------------------------------------------------------------------------
@Override
public String select(final String xmlRequest) {
  //  GLLog.debug(xmlRequest);
  //  final StringBuilder result = new StringBuilder();
  //  try {
  //    final GLXML xml = new GLXML(xmlRequest);
  //    final GLSQL sql = GLSQL.selectUsingXML(xml);
  //    sql.open();
  //    try {
  //      result.append(StringUtils.join(sql.getColumnNameIterable(), ',')).append('\n');
  //      while (sql.next(false)) {
  //        sql.getRowAsCSV(result);
  //        result.append('\n');
  //      }
  //    }
  //    finally {
  //      sql.close();
  //    }
  //  }
  //  catch (final GLDBException dbe) {
  //    GLLog.major("Error executing 'select'", dbe);
  //    result.setLength(0);
  //    result.append("error");
  //  }
  //  catch (final GLXMLException xmle) {
  //    GLLog.major("Error processing XML for 'select'", xmle);
  //    result.setLength(0);
  //    result.append("error");
  //  }
  //  return result.toString();
  return "";
}
//--------------------------------------------------------------------------------------------------
/**
 * Updates rows in any number of tables. The format for the "updates" parameter is:
 * 
 * <pre>
 * Table:table_name1/key1:column1=value1;column2=value2;column3=value3<linefeed>
 * Table:table_name2/key2:column1=value1;column2=value2;column3=value3<linefeed>
 * Table:table_name3/key3:column1=value1;column2=value2;column3=value3<linefeed>
 * </pre>
 * 
 * where the "key1", "key2", etc., values are the primary key values for each of the rows to be
 * updated. Each line in the "updates" string represents changes to a single row.
 */
@Override
public void update(final String updates) {
  //  GLLog.infoDetail("Updates:");
  //  GLLog.infoDetail(updates);
  //  try {
  //    final String[] rows = updates.split("\n");
  //    for (final String row : rows) {
  //      int colonIndex = row.indexOf(':');
  //      if (colonIndex > 0) {
  //        final int slashIndex = row.indexOf('/', colonIndex);
  //        if (slashIndex > colonIndex + 1) {
  //          final IGLTable table = EGXTTestbedTable.valueOf(row.substring(colonIndex + 1, //
  //                                                                        slashIndex));
  //          colonIndex = row.indexOf(':', slashIndex);
  //          if (colonIndex > slashIndex + 1 && colonIndex < row.length() - 1) {
  //            final String keyValue = row.substring(slashIndex + 1, colonIndex);
  //            final String[] columnNamesAndValues = row.substring(colonIndex + 1).split(";");
  //            final GLSQL updateSQL = GLSQL.update(table.toString());
  //            for (final String columnNameAndValue : columnNamesAndValues) {
  //              final int equalsIndex = columnNameAndValue.indexOf('=');
  //              if (equalsIndex > 0) {
  //                final String columnName = columnNameAndValue.substring(0, equalsIndex);
  //                final String value = columnNameAndValue.substring(equalsIndex + 1);
  //                updateSQL.setValue(columnName, value.isEmpty() ? null : value);
  //              }
  //            }
  //            updateSQL.whereAnd(0, table.getPrimaryKeyColumn().toString() + "=" + keyValue, 0);
  //            updateSQL.execute();
  //          }
  //        }
  //      }
  //    }
  //  }
  //  catch (final GLDBException dbe) {
  //    GLLog.major("Error executing 'update'", dbe);
  //  }
}
//--------------------------------------------------------------------------------------------------
}