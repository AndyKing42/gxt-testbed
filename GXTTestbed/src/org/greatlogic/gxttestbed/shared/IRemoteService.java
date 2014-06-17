package org.greatlogic.gxttestbed.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("GXTTestbedRemoteServiceServlet")
public interface IRemoteService extends RemoteService {
//--------------------------------------------------------------------------------------------------
void delete(final String deletes);
void log(final int logLevel, final String location, final String message);
Integer login(final String loginName, final String password);
String select(final String selectResult);
void update(final String updates);
//--------------------------------------------------------------------------------------------------
}