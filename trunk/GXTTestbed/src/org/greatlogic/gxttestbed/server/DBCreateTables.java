package org.greatlogic.gxttestbed.server;

import org.greatlogic.gxttestbed.shared.IDBEnums.EGXTTestbedTable;
import com.greatlogic.glbase.gldb.GLDBUtil;
import com.greatlogic.glbase.gldb.GLSchemaLoader;
import com.greatlogic.glbase.gldb.GLSchemaTable;
import com.greatlogic.glbase.gllib.GLLog;

class DBCreateTables {
//--------------------------------------------------------------------------------------------------
public static void recreateTables() {
  try {
    GLDBUtil.dropTable(null, EGXTTestbedTable.Pet.name());
    GLDBUtil.dropTable(null, EGXTTestbedTable.PetType.name());
    final String sql;
    sql = "" //
          + "create table Pet\n" //
          + "(\n" //
          + "PetId           INTEGER NOT NULL,\n" //
          + "AdoptionFee     DECIMAL (19,4) NOT NULL,\n" //
          + "FosterDate      DATETIME,\n" //
          + "IntakeDate      DATETIME NOT NULL,\n" //
          + "NumberOfFosters INTEGER NOT NULL,\n" //
          + "PetName         VARCHAR (50) NOT NULL,\n" //
          + "PetTypeId       INTEGER NOT NULL,\n" //
          + "Sex             VARCHAR (1) NOT NULL,\n" //
          + "TrainedFlag     VARCHAR (1) NOT NULL,\n" //
          + "constraint PetPK primary key (PetId)\n" //
          + ")\n" //
          + "go\n" //
          + "create TABLE PetType\n" //
          + "(\n" //
          + "PetTypeId        INTEGER NOT NULL,\n" //
          + "PetTypeDesc      VARCHAR (50) NOT NULL,\n" //
          + "PetTypeShortDesc VARCHAR (10) NOT NULL,\n" //
          + "constraint FosterLocTypePK primary key (PetTypeId)\n" //
          + ")\n" //
          + "go\n" //
          + "create unique index PetType_index1 on PetType\n" //
          + "(\n" //
          + "PetTypeShortDesc\n" //
          + ")\n" //
          + "go\n" //
          + "create unique index PetType_index2 on PetType\n" //
          + "(\n" //
          + "PetTypeDesc\n" //
          + ")\n" //
          + "go\n";
    final GLSchemaLoader schemaLoader = GLSchemaLoader.createUsingString(sql, "go", "--");
    for (final GLSchemaTable table : schemaLoader.getTables()) {
      table.executeSQL(null, true, true);
    }
  }
  catch (final Exception e) {
    GLLog.major("Table creation failed", e);
  }
}
//--------------------------------------------------------------------------------------------------
}