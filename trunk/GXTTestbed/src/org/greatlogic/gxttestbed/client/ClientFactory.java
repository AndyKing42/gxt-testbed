package org.greatlogic.gxttestbed.client;

import org.greatlogic.glgwt.client.core.GLLookupTableCache;
import org.greatlogic.glgwt.client.event.GLEventBus;
import org.greatlogic.gxttestbed.client.widget.MainLayoutWidget;
import org.greatlogic.gxttestbed.shared.IRemoteService;
import org.greatlogic.gxttestbed.shared.IRemoteServiceAsync;
import com.google.gwt.core.client.GWT;
import com.sencha.gxt.widget.core.client.ContentPanel;

public abstract class ClientFactory {
//--------------------------------------------------------------------------------------------------
public static ClientFactory   Instance;

private final GLEventBus      _eventBus;
protected GLLookupTableCache  _lookupTableCache;
protected MainLayoutWidget    _mainLayoutWidget;
protected IRemoteServiceAsync _remoteService;
//--------------------------------------------------------------------------------------------------
protected ClientFactory() {
  Instance = this;
  _eventBus = new GLEventBus();
  _remoteService = GWT.create(IRemoteService.class);
}
//--------------------------------------------------------------------------------------------------
public ContentPanel getCenterPanel() {
  return _mainLayoutWidget == null ? null : _mainLayoutWidget.getCenterPanel();
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
public MainLayoutWidget getMainLayoutWidget() {
  return _mainLayoutWidget;
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
public abstract void setMainLayoutWidget(MainLayoutWidget mainLayoutWidget);
//--------------------------------------------------------------------------------------------------
}