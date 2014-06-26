package org.greatlogic.glgwt.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IGLRemoteServiceAsync {
//--------------------------------------------------------------------------------------------------
void applyDBChanges(final String dbChanges, final AsyncCallback<Void> callback);
void getNextId(final String tableName, final int numberOfValues,
               final AsyncCallback<Integer> callback);
void log(final int priority, final String location, final String message,
         final AsyncCallback<Void> callback);
void select(final String xmlRequest, final AsyncCallback<String> asyncCallback);
//--------------------------------------------------------------------------------------------------
}