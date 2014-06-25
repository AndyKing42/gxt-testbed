package org.greatlogic.glgwt.client.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import org.greatlogic.glgwt.shared.IGLTable;

public class GLLookupTableCache {
//--------------------------------------------------------------------------------------------------
private final ArrayList<IGLTable>                           _cachedTableList;
private final HashMap<IGLTable, TreeMap<String, GLRecord>>  _displayValueToRecordMapByTableMap;
private final HashMap<IGLTable, TreeMap<Integer, GLRecord>> _keyToRecordMapByTableMap;
private final HashMap<IGLTable, GLListStore>                _listStoreByTableMap;
//--------------------------------------------------------------------------------------------------
public GLLookupTableCache() {
  _cachedTableList = new ArrayList<>();
  _displayValueToRecordMapByTableMap = new HashMap<>();
  _keyToRecordMapByTableMap = new HashMap<>();
  _listStoreByTableMap = new HashMap<>();
}
//--------------------------------------------------------------------------------------------------
private GLListStore addLookupType(final IGLTable lookupTable) {
  GLListStore result = _listStoreByTableMap.get(lookupTable);
  if (result != null) {
    return result;
  }
  result = new GLListStore();
  _displayValueToRecordMapByTableMap.put(lookupTable, new TreeMap<String, GLRecord>());
  _keyToRecordMapByTableMap.put(lookupTable, new TreeMap<Integer, GLRecord>());
  _listStoreByTableMap.put(lookupTable, result);
  return result;
}
//--------------------------------------------------------------------------------------------------
public GLListStore getListStore(final IGLTable table) {
  return addLookupType(table);
}
//--------------------------------------------------------------------------------------------------
public String lookupDisplayValue(final IGLTable lookupTable, final int key) {
  final TreeMap<Integer, GLRecord> keyToRecordMap = _keyToRecordMapByTableMap.get(lookupTable);
  if (keyToRecordMap == null) {
    return "?";
  }
  final GLRecord record = keyToRecordMap.get(key);
  return record == null ? "?" : record.asString(lookupTable.getComboboxDisplayColumn());
}
//--------------------------------------------------------------------------------------------------
public int lookupKeyValue(final IGLTable lookupTable, final String displayValue) {
  final GLRecord record = lookupRecord(lookupTable, displayValue);
  if (record == null) {
    return 0;
  }
  return record.asInt(lookupTable.getPrimaryKeyColumn());
}
//--------------------------------------------------------------------------------------------------
public GLRecord lookupRecord(final IGLTable lookupTable, final String displayValue) {
  final TreeMap<String, GLRecord> displayValueToRecordMap;
  displayValueToRecordMap = _displayValueToRecordMapByTableMap.get(lookupTable);
  if (displayValueToRecordMap == null) {
    return null;
  }
  return displayValueToRecordMap.get(displayValue);
}
//--------------------------------------------------------------------------------------------------
public void reload(final IGLCacheReloadCallback cacheReloadCallback) {
  for (final IGLTable table : _cachedTableList) {
    reload(table, false, cacheReloadCallback);
  }
}
//--------------------------------------------------------------------------------------------------
public void reload(final IGLTable table, final boolean addToReloadList,
                   final IGLCacheReloadCallback cacheReloadCallback) {
  if (addToReloadList) {
    _cachedTableList.add(table);
  }
  final GLListStore listStore = getListStore(table);
  listStore.clear();
  try {
    final GLSQL sql = GLSQL.select();
    sql.from(table);
    sql.orderBy(table, table.getComboboxDisplayColumn(), true);
    sql.execute(listStore, new IGLSQLSelectCallback() {
      @Override
      public void onFailure(final Throwable t) {
        GLLog.popup(30, table + " loading failed: " + t.getMessage());
        if (cacheReloadCallback != null) {
          cacheReloadCallback.onCompletion(table, false);
        }
      }
      @Override
      public void onSuccess() {
        final TreeMap<String, GLRecord> displayValueToRecordMap;
        displayValueToRecordMap = _displayValueToRecordMapByTableMap.get(table);
        final TreeMap<Integer, GLRecord> keyToRecordMap = _keyToRecordMapByTableMap.get(table);
        displayValueToRecordMap.clear();
        keyToRecordMap.clear();
        for (int recordIndex = 0; recordIndex < listStore.size(); ++recordIndex) {
          final GLRecord record = listStore.get(recordIndex);
          displayValueToRecordMap.put(record.asString(table.getComboboxDisplayColumn()), record);
          keyToRecordMap.put(record.asInt(table.getPrimaryKeyColumn()), record);
        }
        GLLog.popup(5, "Reload of " + table + " was successful");
        if (cacheReloadCallback != null) {
          cacheReloadCallback.onCompletion(table, true);
        }
      }
    });
  }
  catch (final GLDBException dbe) {

  }
}
//--------------------------------------------------------------------------------------------------
}