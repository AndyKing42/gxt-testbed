package org.greatlogic.gxttestbed.client;

import org.greatlogic.glgwt.client.core.GLEventBus;
import org.greatlogic.glgwt.client.core.GLLookupTableCache;
import org.greatlogic.gxttestbed.shared.IRemoteService;
import org.greatlogic.gxttestbed.shared.IRemoteServiceAsync;
import com.google.gwt.core.client.GWT;

public abstract class ClientFactory {
//--------------------------------------------------------------------------------------------------
protected GLLookupTableCache  _lookupTableCache;
private final GLEventBus      _eventBus;
protected IRemoteServiceAsync _remoteService;
//--------------------------------------------------------------------------------------------------
protected ClientFactory() {
  _eventBus = new GLEventBus();
  _remoteService = GWT.create(IRemoteService.class);
}
//--------------------------------------------------------------------------------------------------
public GLEventBus getEventBus() {
  return _eventBus;
}
//--------------------------------------------------------------------------------------------------
public GLLookupTableCache getLookupTableCache() {
  return _lookupTableCache;
}
//--------------------------------------------------------------------------------------------------
public IRemoteServiceAsync getRemoteService() {
  return _remoteService;
}
//--------------------------------------------------------------------------------------------------
public abstract void hidePleaseWait();
//--------------------------------------------------------------------------------------------------
public abstract void login();
//--------------------------------------------------------------------------------------------------
public abstract void showPleaseWait();
//--------------------------------------------------------------------------------------------------
}