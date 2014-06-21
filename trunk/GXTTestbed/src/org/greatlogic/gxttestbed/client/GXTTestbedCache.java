package org.greatlogic.gxttestbed.client;

import java.util.TreeMap;
import org.greatlogic.gxttestbed.client.glgwt.GLDBException;
import org.greatlogic.gxttestbed.client.glgwt.GLInvalidFieldOrColumnException;
import org.greatlogic.gxttestbed.client.glgwt.GLListStore;
import org.greatlogic.gxttestbed.client.glgwt.GLRecord;
import org.greatlogic.gxttestbed.client.glgwt.GLSQL;
import org.greatlogic.gxttestbed.client.glgwt.GLUtil;
import org.greatlogic.gxttestbed.client.glgwt.IGLColumn;
import org.greatlogic.gxttestbed.client.glgwt.IGLSQLSelectCallback;
import org.greatlogic.gxttestbed.shared.IDBEnums.EGXTTestbedTable;
import org.greatlogic.gxttestbed.shared.IDBEnums.Pet;
import org.greatlogic.gxttestbed.shared.IDBEnums.PetType;

public class GXTTestbedCache {
//--------------------------------------------------------------------------------------------------
private static GLListStore               _petTypeListStore;
private static TreeMap<String, GLRecord> _petTypeRecordByPetTypeShortDescMap;
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
public static GLListStore getPetTypeListStore() {
  return _petTypeListStore;
}
//--------------------------------------------------------------------------------------------------
public static GLRecord getPetTypeRecordUsingPetTypeShortDesc(final String value) {
  if (_petTypeRecordByPetTypeShortDescMap == null) {
    _petTypeRecordByPetTypeShortDescMap = buildLookupMap(_petTypeListStore, //
                                                         PetType.PetTypeShortDesc);
  }
  return _petTypeRecordByPetTypeShortDescMap.get(value);
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
      public void onSuccess(final GLListStore listStore) {
        GLUtil.info(5, "Pets loaded successfully");
      }
    });
  }
  catch (final GLDBException dbe) {

  }
}
//--------------------------------------------------------------------------------------------------
private static void loadPetTypes() {
  _petTypeListStore = new GLListStore();
  try {
    final GLSQL petTypeSQL = GLSQL.select();
    petTypeSQL.from(EGXTTestbedTable.PetType);
    petTypeSQL.orderBy(EGXTTestbedTable.PetType, PetType.PetTypeShortDesc, true);
    petTypeSQL.execute(_petTypeListStore, new IGLSQLSelectCallback() {
      @Override
      public void onFailure(final Throwable t) {
        GLUtil.info(30, "Pet type loading failed: " + t.getMessage());
      }
      @Override
      public void onSuccess(final GLListStore listStore) {
        GLUtil.info(5, "Pet types loaded successfully");
      }
    });
  }
  catch (final GLDBException dbe) {

  }
}
//--------------------------------------------------------------------------------------------------
}