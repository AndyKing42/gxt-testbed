package org.greatlogic.gxttestbed.client;

import org.greatlogic.glgwt.client.core.GLLookupTableCache;
import org.greatlogic.gxttestbed.client.widget.MainLayoutWidget;

public class ClientFactoryUI extends ClientFactory {
//--------------------------------------------------------------------------------------------------
public ClientFactoryUI() {
  super();
  _lookupTableCache = new GLLookupTableCache();
}
//--------------------------------------------------------------------------------------------------
@Override
public void hidePleaseWait() {

}
//--------------------------------------------------------------------------------------------------
@Override
public void login() {

}
//--------------------------------------------------------------------------------------------------
@Override
public void setMainLayoutWidget(final MainLayoutWidget mainLayoutWidget) {
  _mainLayoutWidget = mainLayoutWidget;
}
//--------------------------------------------------------------------------------------------------
@Override
public void showPleaseWait() {

}
//--------------------------------------------------------------------------------------------------
}