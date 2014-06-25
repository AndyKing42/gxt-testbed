package org.greatlogic.glgwt.client.core;

import org.greatlogic.glgwt.shared.IGLTable;

public interface IGLCacheReloadCallback {
//--------------------------------------------------------------------------------------------------
public void onCompletion(final IGLTable table, final boolean reloadSucceeded);
//--------------------------------------------------------------------------------------------------
}