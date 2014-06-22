package org.greatlogic.gxttestbed.client;

import java.util.HashMap;
import java.util.TreeMap;
import org.greatlogic.gxttestbed.client.glgwt.GLDBException;
import org.greatlogic.gxttestbed.client.glgwt.GLInvalidFieldOrColumnException;
import org.greatlogic.gxttestbed.client.glgwt.GLListStore;
import org.greatlogic.gxttestbed.client.glgwt.GLRecord;
import org.greatlogic.gxttestbed.client.glgwt.GLSQL;
import org.greatlogic.gxttestbed.client.glgwt.GLUtil;
import org.greatlogic.gxttestbed.client.glgwt.IGLSQLSelectCallback;
import org.greatlogic.gxttestbed.shared.IDBEnums.EGXTTestbedTable;
import org.greatlogic.gxttestbed.shared.IDBEnums.Pet;
import org.greatlogic.gxttestbed.shared.IDBEnums.PetType;
import org.greatlogic.gxttestbed.shared.IGLColumn;
import org.greatlogic.gxttestbed.shared.IGLTable;

public class GXTTestbedCache {
//--------------------------------------------------------------------------------------------------
private static HashMap<IGLTable, TreeMap<String, GLRecord>>  _displayValueToRecordMapByTableMap;
private static HashMap<IGLTable, TreeMap<Integer, GLRecord>> _keyToRecordMapByTableMap;
private static HashMap<IGLTable, GLListStore>                _listStoreByTableMap;
//--------------------------------------------------------------------------------------------------
static {
  _displayValueToRecordMapByTableMap = new HashMap<>();
  _keyToRecordMapByTableMap = new HashMap<>();
  _listStoreByTableMap = new HashMap<>();
}
//--------------------------------------------------------------------------------------------------
private static TreeMap<String, GLRecord> buildLookupMap(final GLListStore listStore,
                                                        final IGLColumn lookupColumn) {
  final TreeMap<String, GLRecord> result = new TreeMap<String, GLRecord>();
  for (int recordIndex = 0; recordIndex < listStore.size(); ++recordIndex) {
    final GLRecord record = listStore.get(recordIndex);
    try {
      result.put(record.asString(lookupColumn), record);
    }
    catch (final GLInvalidFieldOrColumnException e) {

    }
  }
  return result;
}
//--------------------------------------------------------------------------------------------------
public static void load() {
  loadPetTypes();
}
//--------------------------------------------------------------------------------------------------
// todo: this doesn't really belong here ... the pets aren't held in the cache
public static void loadPets(final GLListStore petListStore) {
  try {
    final GLSQL petSQL = GLSQL.select();
    petSQL.from(EGXTTestbedTable.Pet);
    petSQL.orderBy(EGXTTestbedTable.Pet, Pet.PetName, true);
    petSQL.execute(petListStore, new IGLSQLSelectCallback() {
      @Override
      public void onFailure(final Throwable t) {
        GLUtil.info(30, "Pet loading failed: " + t.getMessage());
      }
      @Override
      public void onSuccess() {
        GLUtil.info(5, "Pets loaded successfully");
      }
    });
  }
  catch (final GLDBException dbe) {

  }
}
//--------------------------------------------------------------------------------------------------
private static GLListStore addLookupType(final IGLTable lookupTable) {
  final GLListStore result = new GLListStore();
  _displayValueToRecordMapByTableMap.put(lookupTable, new TreeMap<String, GLRecord>());
  _keyToRecordMapByTableMap.put(lookupTable, new TreeMap<Integer, GLRecord>());
  _listStoreByTableMap.put(lookupTable, result);
  return result;
}
//--------------------------------------------------------------------------------------------------
private static void loadPetTypes() {
  final GLListStore listStore = addLookupType(EGXTTestbedTable.PetType);
  try {
    final GLSQL petTypeSQL = GLSQL.select();
    petTypeSQL.from(EGXTTestbedTable.PetType);
    petTypeSQL.orderBy(EGXTTestbedTable.PetType, PetType.PetTypeShortDesc, true);
    petTypeSQL.execute(listStore, new IGLSQLSelectCallback() {
      @Override
      public void onFailure(final Throwable t) {
        GLUtil.info(30, "Pet type loading failed: " + t.getMessage());
      }
      @Override
      public void onSuccess() {
        final TreeMap<String, GLRecord> displayValueToRecordMap;
        displayValueToRecordMap = _displayValueToRecordMapByTableMap.get(EGXTTestbedTable.PetType);
        final TreeMap<Integer, GLRecord> keyToRecordMap;
        keyToRecordMap = _keyToRecordMapByTableMap.get(EGXTTestbedTable.PetType);
        displayValueToRecordMap.clear();
        keyToRecordMap.clear();
        for (int recordIndex = 0; recordIndex < listStore.size(); ++recordIndex) {
          final GLRecord record = listStore.get(recordIndex);
          try {
            displayValueToRecordMap.put(record.asString(PetType.PetTypeShortDesc), record);
            keyToRecordMap.put(record.asInt(PetType.PetTypeId), record);
          }
          catch (final GLInvalidFieldOrColumnException ifoce) {
            //
          }
        }
        GLUtil.info(5, "Pet types loaded successfully");
      }
    });
  }
  catch (final GLDBException dbe) {

  }
}
//--------------------------------------------------------------------------------------------------
}