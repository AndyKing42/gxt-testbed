package org.greatlogic.gxttestbed.server;

import org.greatlogic.gxttestbed.shared.IRemoteService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.greatlogic.glbase.gllib.GLLog;
import com.greatlogic.glbase.gllib.IGLLibEnums.EGLLogLevel;

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
  DBStatement.delete(deletes);
}
//--------------------------------------------------------------------------------------------------
@Override
public void loadTestData(final String testDataOptionString) {
  try {
    DBTestData.processRequest(testDataOptionString);
  }
  catch (final Exception e) {
    GLLog.major("Error loading test data", e);
  }
}
//--------------------------------------------------------------------------------------------------
@Override
public void log(final int logLevelId, final String location, final String message) {
  GLLog.log(EGLLogLevel.lookupUsingPriority(logLevelId * 10), location + "=>" + message);
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
public void recreateTables() {
  DBCreateTables.recreateTables();
}
//--------------------------------------------------------------------------------------------------
@Override
public String select(final String xmlRequest) {
  return DBStatement.select(xmlRequest);
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
  DBStatement.update(updates);
}
//--------------------------------------------------------------------------------------------------
}