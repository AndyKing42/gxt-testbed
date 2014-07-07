package org.greatlogic.glgwt.client.event;

import org.greatlogic.glgwt.client.event.GLLookupLoadedEvent.IGLLookupLoadedEventHandler;
import org.greatlogic.glgwt.shared.IGLTable;
import com.google.gwt.event.shared.EventHandler;
import com.google.web.bindery.event.shared.Event;

public class GLLookupLoadedEvent extends Event<IGLLookupLoadedEventHandler> {
//--------------------------------------------------------------------------------------------------
public static final Type<IGLLookupLoadedEventHandler> LookLoadedEventType;
private final IGLTable                                _table;
//==================================================================================================
public interface IGLLookupLoadedEventHandler extends EventHandler {
public void onLookupLoadedEvent(final GLLookupLoadedEvent lookupLoadedEvent);
}
//==================================================================================================
static {
  LookLoadedEventType = new Type<IGLLookupLoadedEventHandler>();
}
//--------------------------------------------------------------------------------------------------
public GLLookupLoadedEvent(final IGLTable table) {
  _table = table;
}
//--------------------------------------------------------------------------------------------------
@Override
protected void dispatch(final IGLLookupLoadedEventHandler handler) {
  handler.onLookupLoadedEvent(this);
}
//--------------------------------------------------------------------------------------------------
@Override
public Type<IGLLookupLoadedEventHandler> getAssociatedType() {
  return LookLoadedEventType;
}
//--------------------------------------------------------------------------------------------------
public IGLTable getTable() {
  return _table;
}
//--------------------------------------------------------------------------------------------------
@Override
public String toString() {
  return "LookupLoadedEvent - table:" + _table;
}
//--------------------------------------------------------------------------------------------------
}