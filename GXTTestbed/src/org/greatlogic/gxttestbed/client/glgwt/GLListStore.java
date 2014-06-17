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
/**
 * Creates a new GLListStore.
 */
public GLListStore() {
  super(new ModelKeyProvider<GLRecord>() {
    @Override
    public String getKey(final GLRecord record) {
      String result;
      try {
        result = record.getKeyValueAsString();
      }
      catch (final GLInvalidFieldOrColumnException ifoce) {
        result = "***error***";
      }
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
      final StringBuilder sb = new StringBuilder();
      final List<GLRecord> updatedRecordList = event.getItems();
      for (final GLRecord record : updatedRecordList) {
        sb.append("Table:").append(record.getRecordDef().getTable().toString()).append("/");
        try {
          sb.append(record.getKeyValueAsString());
        }
        catch (final GLInvalidFieldOrColumnException ifoce) {
          // the key field doesn't exist
          sb.append("???");
        }
        sb.append(":");
        boolean firstField = true;
        for (final String fieldName : record.getChangedFieldNameList()) {
          sb.append(firstField ? "" : ";").append(fieldName).append("=");
          try {
            sb.append(record.asString(fieldName));
          }
          catch (final GLInvalidFieldOrColumnException ifoce) {
            GLUtil.info(5, "Unknown column name:" + fieldName);
          }
          sb.append("");
          firstField = false;
        }
        sb.append("\n");
        record.getChangedFieldNameList().clear();
      }
      if (sb.length() > 0) {
        GLUtil.getRemoteService().update(sb.toString(), new AsyncCallback<Void>() {
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
  };
  addStoreUpdateHandler(storeUpdateHandler);
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
    try {
      sb.append(record.getKeyValueAsString());
    }
    catch (final GLInvalidFieldOrColumnException ifoce) {
      // the key field doesn't exist
      sb.append("???");
    }
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
@Override
public String toString() {
  return super.toString();
}
//--------------------------------------------------------------------------------------------------
}