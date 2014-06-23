package org.greatlogic.gxttestbed.client.glgwt;

import org.greatlogic.gxttestbed.shared.glgwt.IGLTable;

public interface IGLCacheReloadCallback {
//--------------------------------------------------------------------------------------------------
public void onCompletion(final IGLTable table, final boolean reloadSucceeded);
//--------------------------------------------------------------------------------------------------
}