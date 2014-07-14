package org.greatlogic.gxttestbed.client;

import org.greatlogic.glgwt.client.core.GLLookupCache;
import org.greatlogic.glgwt.client.event.GLEventBus;
import org.greatlogic.gxttestbed.client.widget.MainLayoutWidget;
import org.greatlogic.gxttestbed.shared.IRemoteService;
import org.greatlogic.gxttestbed.shared.IRemoteServiceAsync;
import org.greatlogic.gxttestbed.shared.LookupCacheLoader;
import com.google.gwt.core.client.GWT;
import com.sencha.gxt.widget.core.client.ContentPanel;

public abstract class ClientFactory {
//--------------------------------------------------------------------------------------------------
public static ClientFactory   Instance;

private final GLEventBus      _eventBus;
protected GLLookupCache       _lookupCache;
protected MainLayoutWidget    _mainLayoutWidget;
protected IRemoteServiceAsync _remoteService;
//--------------------------------------------------------------------------------------------------
protected ClientFactory() {
  Instance = this;
  _eventBus = new GLEventBus();
  _remoteService = GWT.create(IRemoteService.class);
  _lookupCache = new GLLookupCache();
  LookupCacheLoader.load(_lookupCache);
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
public GLLookupCache getLookupCache() {
  return _lookupCache;
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