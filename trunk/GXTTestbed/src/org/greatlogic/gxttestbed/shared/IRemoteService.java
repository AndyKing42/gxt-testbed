package org.greatlogic.gxttestbed.shared;

import org.greatlogic.gxttestbed.shared.glgwt.IGLRemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("GXTTestbedRemoteServiceServlet")
public interface IRemoteService extends IGLRemoteService {
//--------------------------------------------------------------------------------------------------
void loadTestData(final String testDataOptionString);
Integer login(final String loginName, final String password);
void recreateTables();
//--------------------------------------------------------------------------------------------------
}