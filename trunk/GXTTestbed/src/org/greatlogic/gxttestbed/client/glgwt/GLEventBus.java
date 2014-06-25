package org.greatlogic.gxttestbed.client.glgwt;

import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.Event.Type;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class GLEventBus {
//--------------------------------------------------------------------------------------------------
private final SimpleEventBus _eventBus;
//--------------------------------------------------------------------------------------------------
public GLEventBus() {
  _eventBus = new SimpleEventBus();
}
//--------------------------------------------------------------------------------------------------
public <H> HandlerRegistration addHandler(final Type<H> type, final H handler) {
  return _eventBus.addHandler(type, handler);
}
//--------------------------------------------------------------------------------------------------
public void fireEvent(final String location, final Event<?> event) {
  _eventBus.fireEvent(event);
  GLLog.infoDetail(location, "Event fired-" + event.toDebugString());
}
//--------------------------------------------------------------------------------------------------
public SimpleEventBus getEventBus() {
  return _eventBus;
}
//--------------------------------------------------------------------------------------------------
}