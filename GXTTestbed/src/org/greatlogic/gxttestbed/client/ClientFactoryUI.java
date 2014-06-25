package org.greatlogic.gxttestbed.client;

import org.greatlogic.glgwt.client.core.GLLookupTableCache;

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
public void showPleaseWait() {

}
//--------------------------------------------------------------------------------------------------
}