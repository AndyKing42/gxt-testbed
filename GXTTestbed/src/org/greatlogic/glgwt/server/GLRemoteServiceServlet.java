package org.greatlogic.glgwt.server;

import org.greatlogic.glgwt.shared.IGLRemoteService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.greatlogic.glbase.gllib.GLLog;
import com.greatlogic.glbase.gllib.IGLLibEnums.EGLLogLevel;

@SuppressWarnings("serial")
public class GLRemoteServiceServlet extends RemoteServiceServlet implements IGLRemoteService {
//--------------------------------------------------------------------------------------------------
/**
 * Deletes any number of rows from any number of tables. The format for the "deletes" parameter is:
 * 
 * <pre>
 * Table:table_name1/key_column_name1:key1,key2,key3<linefeed>
 * Table:table_name2/key_column_name2:key1,key2,key3<linefeed>
 * Table:table_name3/key_column_name3:key1,key2,key3<linefeed>
 * </pre>
 * 
 * Each line in the "deletes" string represents any number of rows to be deleted from a single
 * table.
 */
@Override
public void delete(final String deletes) {
  GLDBStatement.delete(deletes);
}
//--------------------------------------------------------------------------------------------------
@Override
public int getNextId(final String tableName, final int numberOfValues) {
  return GLNextId.getNextIdValue(tableName, numberOfValues);
}
//--------------------------------------------------------------------------------------------------
/**
 * Inserts rows into a table. The format for the "inserts" parameter is:
 * 
 * <pre>
 * Table:table_name1/key_column_name1=key1:column1=value1;column2=value2;column3=value3<linefeed>
 * Table:table_name2/key_column_name2=key2:column1=value1;column2=value2;column3=value3<linefeed>
 * Table:table_name3/key_column_name3=key3:column1=value1;column2=value2;column3=value3<linefeed>
 * </pre>
 * 
 * where the "key1", "key2", etc., values are the primary key values for each of the rows to be
 * inserted. Each line in the "inserts" string represents a single row.
 */
@Override
public void insert(final String inserts) {
  GLDBStatement.insert(inserts);
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
/**
 * Updates rows in any number of tables. The format for the "updates" parameter is:
 * 
 * <pre>
 * Table:table_name1/key_column_name1=key1:column1=value1;column2=value2;column3=value3<linefeed>
 * Table:table_name2/key_column_name2=key2:column1=value1;column2=value2;column3=value3<linefeed>
 * Table:table_name3/key_column_name3=key3:column1=value1;column2=value2;column3=value3<linefeed>
 * </pre>
 * 
 * where the "key1", "key2", etc., values are the primary key values for each of the rows to be
 * updated. Each line in the "updates" string represents changes to a single row.
 */
@Override
public void update(final String updates) {
  GLDBStatement.update(updates);
}
//--------------------------------------------------------------------------------------------------
}