package org.greatlogic.glgwt.client.core;

import java.util.ArrayList;
import org.greatlogic.glgwt.shared.IGLColumn;
import org.greatlogic.glgwt.shared.IGLTable;
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
  addStoreUpdateHandler(new StoreUpdateHandler<GLRecord>() {
    @Override
    public void onUpdate(final StoreUpdateEvent<GLRecord> event) {
      //      ClientFactory.Instance.getEventBus().fireEvent(changes_committed_event);
    }
  });
}
//--------------------------------------------------------------------------------------------------
@Override
public void commitChanges() {
  final StringBuilder insertsSB = new StringBuilder();
  final StringBuilder updatesSB = new StringBuilder();
  final ArrayList<GLRecord> insertedRecordList = new ArrayList<>();
  final IGLTable table = _recordDef.getTable();
  final String primaryKeyColumnName = table.getPrimaryKeyColumn().toString();
  for (final Record record : getModifiedRecords()) {
    final GLRecord glRecord = record.getModel();
    final StringBuilder sb = glRecord.getInserted() ? insertsSB : updatesSB;
    sb.append("Table:").append(table.toString()).append("/");
    sb.append(primaryKeyColumnName).append("=").append(glRecord.getKeyValueAsString()).append(":");
    if (glRecord.getInserted()) {
      commitChangesAddInsertedRecord(sb, glRecord);
      insertedRecordList.add(glRecord);
    }
    else {
      commitChangesAddUpdatedRecord(sb, glRecord, record);
    }
    sb.append("\n");
  }
  if (insertsSB.length() > 0) {
    sendInsertsToServer(insertsSB, insertedRecordList, updatesSB.length() == 0);
  }
  if (updatesSB.length() > 0) {
    sendUpdatesToServer(updatesSB);
  }
}
//--------------------------------------------------------------------------------------------------
private void commitChangesAddInsertedRecord(final StringBuilder sb, final GLRecord glRecord) {
  boolean firstColumn = true;
  for (final IGLColumn column : glRecord.getRecordDef().getTable().getColumnList()) {
    final Object value = glRecord.asObject(column);
    if (value != null) {
      final String stringValue = glRecord.asString(column);
      if (!stringValue.isEmpty()) {
        sb.append(firstColumn ? "" : ";").append(column.toString()).append("=").append(stringValue);
        firstColumn = false;
      }
    }
  }
}
//--------------------------------------------------------------------------------------------------
private void commitChangesAddUpdatedRecord(final StringBuilder sb, final GLRecord glRecord,
                                           final Record record) {
  boolean firstColumn = true;
  for (final Change<GLRecord, ?> change : record.getChanges()) {
    final String columnName = change.getChangeTag().toString();
    sb.append(firstColumn ? "" : ";");
    sb.append(columnName).append("=").append(glRecord.asString(columnName));
    firstColumn = false;
  }
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
  final IGLTable table = _recordDef.getTable();
  sb.append("Table:").append(table.toString()).append("/").append(table.getPrimaryKeyColumn());
  sb.append(":");
  boolean firstRecord = true;
  for (final GLRecord record : recordList) {
    sb.append(firstRecord ? "" : ",").append(record.getKeyValueAsString());
    firstRecord = false;
  }
  if (sb.length() > 0) {
    GLUtil.getRemoteService().delete(sb.toString(), new AsyncCallback<Void>() {
      @Override
      public void onFailure(final Throwable t) {
        GLLog.popup(10, "Server update failed:" + t.getMessage());
      }
      @Override
      public void onSuccess(final Void result) {
        GLLog.popup(10, "Changes have been saved on the server");
      }
    });
  }
}
//--------------------------------------------------------------------------------------------------
private void sendInsertsToServer(final StringBuilder insertsSB,
                                 final ArrayList<GLRecord> insertedRecordList,
                                 final boolean commitChanges) {
  GLUtil.getRemoteService().insert(insertsSB.toString(), new AsyncCallback<Void>() {
    @Override
    public void onFailure(final Throwable t) {
      GLLog.popup(10, "Server inserts failed:" + t.getMessage());
    }
    @Override
    public void onSuccess(final Void result) {
      GLLog.popup(10, "Inserts have been saved on the server");
      for (final GLRecord record : insertedRecordList) {
        record.setInserted(false);
      }
      if (commitChanges) {
        GLListStore.super.commitChanges();
      }
    }
  });
}
//--------------------------------------------------------------------------------------------------
private void sendUpdatesToServer(final StringBuilder updatesSB) {
  GLUtil.getRemoteService().update(updatesSB.toString(), new AsyncCallback<Void>() {
    @Override
    public void onFailure(final Throwable t) {
      GLLog.popup(10, "Server updates failed:" + t.getMessage());
    }
    @Override
    public void onSuccess(final Void result) {
      GLLog.popup(10, "Changes have been saved on the server");
      //      ClientFactory.Instance.getEventBus().fireEvent(event);
      GLListStore.super.commitChanges();
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