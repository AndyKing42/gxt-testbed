package org.greatlogic.gxttestbed.client;

import org.greatlogic.gxttestbed.client.glgwt.GLDBException;
import org.greatlogic.gxttestbed.client.glgwt.GLListStore;
import org.greatlogic.gxttestbed.client.glgwt.GLSQL;
import org.greatlogic.gxttestbed.client.glgwt.GLUtil;
import org.greatlogic.gxttestbed.client.glgwt.IGLSQLSelectCallback;
import org.greatlogic.gxttestbed.shared.IDBEnums.EGXTTestbedTable;
import org.greatlogic.gxttestbed.shared.IDBEnums.Pet;

public class GXTTestbedCache {
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
}