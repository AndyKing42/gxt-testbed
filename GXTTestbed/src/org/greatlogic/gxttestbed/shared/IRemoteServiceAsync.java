package org.greatlogic.gxttestbed.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IRemoteServiceAsync {
//--------------------------------------------------------------------------------------------------
void delete(final String deletes, final AsyncCallback<Void> callback);
void loadTestData(final String testDataOptionString, final AsyncCallback<Void> callback);
void log(final int logLevel, final String location, final String message,
         final AsyncCallback<Void> callback);
void login(final String loginName, final String password, final AsyncCallback<Integer> callback);
void select(final String selectResult, final AsyncCallback<String> asyncCallback);
void update(final String updates, final AsyncCallback<Void> callback);
//--------------------------------------------------------------------------------------------------
}