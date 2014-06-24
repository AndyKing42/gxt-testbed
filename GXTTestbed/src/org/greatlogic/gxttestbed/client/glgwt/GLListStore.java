package org.greatlogic.gxttestbed.client.glgwt;

import java.util.ArrayList;
import java.util.List;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.event.StoreUpdateEvent;
import com.sencha.gxt.data.shared.event.StoreUpdateEvent.StoreUpdateHandler;
/**
 * A ListStore that contains GLRecord entries.
 */
public class GLListStore extends ListStore<GLRecord> {
//--------------------------------------------------------------------------------------------------
private GLRecordDef _recordDef;
//--------------------------------------------------------------------------------------------------
/**
 * Creates a new GLListStore.
 */
public GLListStore() {
  super(new ModelKeyProvider<GLRecord>() {
    @Override
    public String getKey(final GLRecord record) {
      String result;
      result = record.getKeyValueAsString();
      return result;
    }
  });
  createStoreUpdateHandler();
}
//--------------------------------------------------------------------------------------------------
@Override
public void commitChanges() {
  for (final Record record : getModifiedRecords()) {
    final GLRecord glRecord = record.getModel();
    glRecord.getChangedFieldNameList().clear();
    for (final Change<GLRecord, ?> change : record.getChanges()) {
      glRecord.getChangedFieldNameList().add(change.getChangeTag().toString());
    }
  }
  super.commitChanges();
}
//--------------------------------------------------------------------------------------------------
private void createStoreUpdateHandler() {
  final StoreUpdateHandler<GLRecord> storeUpdateHandler = new StoreUpdateHandler<GLRecord>() {
    @Override
    public void onUpdate(final StoreUpdateEvent<GLRecord> event) {
      final StringBuilder insertsSB = new StringBuilder();
      final StringBuilder updatesSB = new StringBuilder();
      final List<GLRecord> recordList = event.getItems();
      for (final GLRecord record : recordList) {
        final StringBuilder sb = record.getInserted() ? insertsSB : updatesSB;
        sb.append("Table:").append(record.getRecordDef().getTable().toString()).append("/");
        sb.append(record.getKeyValueAsString());
        sb.append(":");
        boolean firstField = true;
        for (final String fieldName : record.getChangedFieldNameList()) {
          sb.append(firstField ? "" : ";").append(fieldName).append("=");
          sb.append(record.asString(fieldName));
          sb.append("");
          firstField = false;
        }
        sb.append("\n");
        record.getChangedFieldNameList().clear();
      }
      if (insertsSB.length() > 0) {
        sendInsertsToServer(insertsSB);
      }
      if (updatesSB.length() > 0) {
        sendUpdatesToServer(updatesSB);
      }
    }
  };
  addStoreUpdateHandler(storeUpdateHandler);
}
//--------------------------------------------------------------------------------------------------
public GLRecordDef getRecordDef() {
  return _recordDef;
}
//--------------------------------------------------------------------------------------------------
public void remove(final ArrayList<GLRecord> recordList) {
  for (final GLRecord record : recordList) {
    remove(record);
  }
  final StringBuilder sb = new StringBuilder();
  boolean firstRecord = true;
  for (final GLRecord record : recordList) {
    if (firstRecord) {
      sb.append("Table:").append(record.getRecordDef().getTable().toString()).append("/");
      firstRecord = false;
    }
    else {
      sb.append(",");
    }
    sb.append(record.getKeyValueAsString());
  }
  if (sb.length() > 0) {
    GLUtil.getRemoteService().delete(sb.toString(), new AsyncCallback<Void>() {
      @Override
      public void onFailure(final Throwable t) {
        GLUtil.info(10, "Server update failed:" + t.getMessage());
      }
      @Override
      public void onSuccess(final Void result) {
        GLUtil.info(10, "Changes have been saved on the server");
      }
    });
  }
}
//--------------------------------------------------------------------------------------------------
private void sendInsertsToServer(final StringBuilder insertsSB) {
  GLUtil.getRemoteService().insert(insertsSB.toString(), new AsyncCallback<Void>() {
    @Override
    public void onFailure(final Throwable t) {
      GLUtil.info(10, "Server inserts failed:" + t.getMessage());
    }
    @Override
    public void onSuccess(final Void result) {
      GLUtil.info(10, "Changes have been saved on the server");
    }
  });
}
//--------------------------------------------------------------------------------------------------
private void sendUpdatesToServer(final StringBuilder updatesSB) {
  GLUtil.getRemoteService().update(updatesSB.toString(), new AsyncCallback<Void>() {
    @Override
    public void onFailure(final Throwable t) {
      GLUtil.info(10, "Server updates failed:" + t.getMessage());
    }
    @Override
    public void onSuccess(final Void result) {
      GLUtil.info(10, "Changes have been saved on the server");
    }
  });
}
//--------------------------------------------------------------------------------------------------
public void setRecordDef(final GLRecordDef recordDef) {
  _recordDef = recordDef;
}
//--------------------------------------------------------------------------------------------------
@Override
public String toString() {
  return super.toString();
}
//--------------------------------------------------------------------------------------------------
}