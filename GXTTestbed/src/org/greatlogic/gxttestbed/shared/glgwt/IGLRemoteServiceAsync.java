package org.greatlogic.gxttestbed.shared.glgwt;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IGLRemoteServiceAsync {
//--------------------------------------------------------------------------------------------------
void delete(final String deletes, final AsyncCallback<Void> callback);
void log(final int logLevel, final String location, final String message,
         final AsyncCallback<Void> callback);
void select(final String selectResult, final AsyncCallback<String> asyncCallback);
void update(final String updates, final AsyncCallback<Void> callback);
//--------------------------------------------------------------------------------------------------
}