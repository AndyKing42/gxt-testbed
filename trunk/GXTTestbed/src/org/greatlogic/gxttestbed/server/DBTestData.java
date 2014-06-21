package org.greatlogic.gxttestbed.server;

import org.greatlogic.gxttestbed.client.glgwt.GLInvalidFieldOrColumnException;
import org.greatlogic.gxttestbed.client.glgwt.GLListStore;
import org.greatlogic.gxttestbed.client.glgwt.GLRecord;
import org.greatlogic.gxttestbed.shared.IDBEnums.EGXTTestbedTable;
import org.greatlogic.gxttestbed.shared.IGXTTestbedEnums.ETestDataOption;
import org.greatlogic.gxttestbed.shared.TestData;
import com.greatlogic.glbase.gldb.GLDBException;
import com.greatlogic.glbase.gldb.GLDBUtil;
import com.greatlogic.glbase.gldb.GLSQL;
import com.greatlogic.glbase.gllib.GLLog;

class DBTestData {
//--------------------------------------------------------------------------------------------------
public static void processRequest(final String testDataOptionString) throws GLDBException,
  GLInvalidFieldOrColumnException {
  final ETestDataOption testDataOption = ETestDataOption.lookup(testDataOptionString);
  switch (testDataOption) {
    case Reload:
      reload();
      break;
    case Unknown:
      GLLog.major("Unknown test data option:" + testDataOptionString);
      break;
  }
}
//--------------------------------------------------------------------------------------------------
private static void reload() throws GLDBException, GLInvalidFieldOrColumnException {
  //  truncate(EGXTTestbedTable.Pet);
  truncate(EGXTTestbedTable.PetType);
  final GLListStore listStore = new GLListStore();
  TestData.loadPetTypeTestData(listStore);
  for (int recordIndex = 0; recordIndex < listStore.size(); ++recordIndex) {
    final GLRecord record = listStore.get(recordIndex);
    final GLSQL petTypeSQL = GLSQL.insert(EGXTTestbedTable.PetType.name(), false);
    for (final String fieldName : record.getRecordDef().getFieldIndexByFieldNameMap().keySet()) {
      petTypeSQL.setValue(fieldName, record.asObject(fieldName));
    }
    petTypeSQL.execute(false);
  }
  TestData.loadPetTestData(listStore);
}
//--------------------------------------------------------------------------------------------------
private static void truncate(final EGXTTestbedTable table) throws GLDBException {
  GLDBUtil.execSQL("truncate " + table);
}
//--------------------------------------------------------------------------------------------------
}