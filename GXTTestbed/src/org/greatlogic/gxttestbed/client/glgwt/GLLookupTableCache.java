package org.greatlogic.gxttestbed.client.glgwt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import org.greatlogic.gxttestbed.shared.glgwt.IGLTable;

public class GLLookupTableCache {
//--------------------------------------------------------------------------------------------------
private static ArrayList<IGLTable>                           _cachedTableList;
private static HashMap<IGLTable, TreeMap<String, GLRecord>>  _displayValueToRecordMapByTableMap;
private static HashMap<IGLTable, TreeMap<Integer, GLRecord>> _keyToRecordMapByTableMap;
private static HashMap<IGLTable, GLListStore>                _listStoreByTableMap;
//--------------------------------------------------------------------------------------------------
static {
  _cachedTableList = new ArrayList<>();
  _displayValueToRecordMapByTableMap = new HashMap<>();
  _keyToRecordMapByTableMap = new HashMap<>();
  _listStoreByTableMap = new HashMap<>();
}
//--------------------------------------------------------------------------------------------------
private static GLListStore addLookupType(final IGLTable lookupTable) {
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
public static GLListStore getListStore(final IGLTable table) {
  return addLookupType(table);
}
//--------------------------------------------------------------------------------------------------
public static String lookupDisplayValue(final IGLTable lookupTable, final int key) {
  final TreeMap<Integer, GLRecord> keyToRecordMap = _keyToRecordMapByTableMap.get(lookupTable);
  if (keyToRecordMap == null) {
    return "?";
  }
  final GLRecord record = keyToRecordMap.get(key);
  try {
    return record == null ? "?" : record.asString(lookupTable.getComboboxDisplayColumn());
  }
  catch (final GLInvalidFieldOrColumnException e) {
    return "?";
  }
}
//--------------------------------------------------------------------------------------------------
public static int lookupKeyValue(final IGLTable lookupTable, final String displayValue) {
  final GLRecord record = lookupRecord(lookupTable, displayValue);
  if (record == null) {
    return 0;
  }
  try {
    return record.asInt(lookupTable.getPrimaryKeyColumn());
  }
  catch (final GLInvalidFieldOrColumnException e) {
    return 0;
  }
}
//--------------------------------------------------------------------------------------------------
public static GLRecord lookupRecord(final IGLTable lookupTable, final String displayValue) {
  final TreeMap<String, GLRecord> displayValueToRecordMap;
  displayValueToRecordMap = _displayValueToRecordMapByTableMap.get(lookupTable);
  if (displayValueToRecordMap == null) {
    return null;
  }
  return displayValueToRecordMap.get(displayValue);
}
//--------------------------------------------------------------------------------------------------
public static void reload(final IGLCacheReloadCallback cacheReloadCallback) {
  for (final IGLTable table : _cachedTableList) {
    reload(table, false, cacheReloadCallback);
  }
}
//--------------------------------------------------------------------------------------------------
public static void reload(final IGLTable table, final boolean addToReloadList,
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
        GLUtil.info(30, table + " loading failed: " + t.getMessage());
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
          try {
            displayValueToRecordMap.put(record.asString(table.getComboboxDisplayColumn()), record);
            keyToRecordMap.put(record.asInt(table.getPrimaryKeyColumn()), record);
          }
          catch (final GLInvalidFieldOrColumnException ifoce) {
            //
          }
        }
        GLUtil.info(5, "Reload of " + table + " was successful");
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