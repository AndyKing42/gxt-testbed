package org.greatlogic.gxttestbed.shared;

import org.greatlogic.gxttestbed.shared.glgwt.IGLRemoteServiceAsync;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IRemoteServiceAsync extends IGLRemoteServiceAsync {
//--------------------------------------------------------------------------------------------------
void loadTestData(final String testDataOptionString, final AsyncCallback<Void> callback);
void login(final String loginName, final String password, final AsyncCallback<Integer> callback);
void recreateTables(final AsyncCallback<Void> asyncCallback);
//--------------------------------------------------------------------------------------------------
}