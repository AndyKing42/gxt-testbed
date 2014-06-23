package org.greatlogic.gxttestbed.shared.glgwt;

import com.google.gwt.user.client.rpc.RemoteService;

public interface IGLRemoteService extends RemoteService {
//--------------------------------------------------------------------------------------------------
void delete(final String deletes);
void log(final int logLevel, final String location, final String message);
String select(final String selectResult);
void update(final String updates);
//--------------------------------------------------------------------------------------------------
}