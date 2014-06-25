package org.greatlogic.glgwt.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IGLRemoteServiceAsync {
//--------------------------------------------------------------------------------------------------
void delete(final String deletes, final AsyncCallback<Void> callback);
void getNextId(final String tableName, final int numberOfValues,
               final AsyncCallback<Integer> callback);
void insert(final String inserts, final AsyncCallback<Void> callback);
void log(final int priority, final String location, final String message,
         final AsyncCallback<Void> callback);
void select(final String xmlRequest, final AsyncCallback<String> asyncCallback);
void update(final String updates, final AsyncCallback<Void> callback);
//--------------------------------------------------------------------------------------------------
}