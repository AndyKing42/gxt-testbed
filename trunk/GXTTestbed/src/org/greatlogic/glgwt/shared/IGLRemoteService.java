package org.greatlogic.glgwt.shared;

import com.google.gwt.user.client.rpc.RemoteService;

public interface IGLRemoteService extends RemoteService {
//--------------------------------------------------------------------------------------------------
void applyDBChanges(final String dbChanges);
int getNextId(final String tableName, final int numberOfValues);
void log(final int priority, final String location, final String message);
String select(final String xmlRequest);
//--------------------------------------------------------------------------------------------------
}