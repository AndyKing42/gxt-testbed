package org.greatlogic.gxttestbed.server;

import org.greatlogic.gxttestbed.server.glgwt.GLRemoteServiceServlet;
import org.greatlogic.gxttestbed.shared.IRemoteService;
import com.greatlogic.glbase.gllib.GLLog;
import com.greatlogic.glbase.gllib.IGLLibEnums.EGLLogLevel;

@SuppressWarnings("serial")
public class GXTTestbedRemoteServiceServlet extends GLRemoteServiceServlet implements
                                                                          IRemoteService {
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
}