package org.greatlogic.glgwt.server;

import org.greatlogic.glgwt.shared.IGLRemoteService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.greatlogic.glbase.gllib.GLLog;
import com.greatlogic.glbase.gllib.IGLLibEnums.EGLLogLevel;

@SuppressWarnings("serial")
public class GLRemoteServiceServlet extends RemoteServiceServlet implements IGLRemoteService {
//--------------------------------------------------------------------------------------------------
/**
 * Inserts, updates, or deletes rows. The entries in the "dbChanges" parameter are broken into
 * linefeed-separated lines. Each line represents an insert (one per line), update (one per line),
 * or deletes (any number per line). Each line begins with a single character that indicates whether
 * this is an insert ("I"), update ("U"), or delete ("D") line. This character is followed by a
 * hyphen, and then the table name. The table name is followed by a forward slash, and then the key
 * column name, and then an equals sign. On insert and update lines the equals sign is followed by
 * the key value for the row that is to be inserted or updated, followed by a colon and then a
 * comma-delimited list of column name, equals sign, and value; on delete lines the equals sign is
 * followed by a comma-delimited list of key values for the rows that are to be deleted. An example
 * containing each type of line:
 * 
 * <pre>
 * I-table_name1/key_column_name1=key-value:column=value;column=value;column=value<linefeed>
 * U-table_name1/key_column_name1=key-value:column=value;column=value;column=value<linefeed>
 * D-table_name1/key_column_name1=key-value,key-value,key-value<linefeed>
 * </pre>
 */
@Override
public void applyDBChanges(final String dbChanges) {
  GLDBStatement.applyDBChanges(dbChanges);
}
//--------------------------------------------------------------------------------------------------
@Override
public int getNextId(final String tableName, final int numberOfValues) {
  return GLNextId.getNextIdValue(tableName, numberOfValues);
}
//--------------------------------------------------------------------------------------------------
@Override
public void log(final int priority, final String location, final String message) {
  GLLog.log(EGLLogLevel.lookupUsingPriority(priority), location + "=>" + message);
}
//--------------------------------------------------------------------------------------------------
@Override
public String select(final String xmlRequest) {
  return GLDBStatement.select(xmlRequest);
}
//--------------------------------------------------------------------------------------------------
}