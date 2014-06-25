package org.greatlogic.gxttestbed.client;

import org.greatlogic.glgwt.client.core.GLDBException;
import org.greatlogic.glgwt.client.core.GLListStore;
import org.greatlogic.glgwt.client.core.GLLog;
import org.greatlogic.glgwt.client.core.GLSQL;
import org.greatlogic.glgwt.client.core.IGLSQLSelectCallback;
import org.greatlogic.gxttestbed.shared.IDBEnums.EGXTTestbedTable;
import org.greatlogic.gxttestbed.shared.IDBEnums.Pet;

public class GXTTestbedCache {
//--------------------------------------------------------------------------------------------------
public static void loadPets(final GLListStore petListStore) {
  try {
    final GLSQL petSQL = GLSQL.select();
    petSQL.from(EGXTTestbedTable.Pet);
    petSQL.orderBy(EGXTTestbedTable.Pet, Pet.PetName, true);
    petSQL.execute(petListStore, new IGLSQLSelectCallback() {
      @Override
      public void onFailure(final Throwable t) {
        GLLog.popup(30, "Pet loading failed: " + t.getMessage());
      }
      @Override
      public void onSuccess() {
        GLLog.popup(5, "Pets loaded successfully");
      }
    });
  }
  catch (final GLDBException dbe) {
    //    GLUtil.getRemoteService().log(logLevel, location, message, callback);
  }
}
//--------------------------------------------------------------------------------------------------
}