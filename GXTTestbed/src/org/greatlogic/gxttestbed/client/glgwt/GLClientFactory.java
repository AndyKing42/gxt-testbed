package org.greatlogic.gxttestbed.client.glgwt;

import org.greatlogic.gxttestbed.shared.IRemoteService;
import org.greatlogic.gxttestbed.shared.IRemoteServiceAsync;
import com.google.gwt.core.client.GWT;

public abstract class GLClientFactory {
//--------------------------------------------------------------------------------------------------
protected GLLookupTableCache  _lookupTableCache;
private final GLEventBus      _eventBus;
protected IRemoteServiceAsync _remoteService;
//--------------------------------------------------------------------------------------------------
protected GLClientFactory() {
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