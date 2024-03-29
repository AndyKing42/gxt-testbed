package org.greatlogic.gxttestbed.server;

import java.util.List;
import org.greatlogic.glgwt.server.GLNextId;
import org.greatlogic.gxttestbed.shared.IDBEnums.EGXTTestbedTable;
import org.greatlogic.gxttestbed.shared.IDBEnums.Pet;
import org.greatlogic.gxttestbed.shared.IDBEnums.PetType;
import org.greatlogic.gxttestbed.shared.IGXTTestbedEnums.ETestDataOption;
import com.google.common.collect.Lists;
import com.greatlogic.glbase.gldb.GLDBException;
import com.greatlogic.glbase.gldb.GLDBUtil;
import com.greatlogic.glbase.gldb.GLSQL;
import com.greatlogic.glbase.gllib.GLLog;
import com.greatlogic.glbase.gllib.GLUtil;

class DBTestData {
//--------------------------------------------------------------------------------------------------
public static void processRequest(final String testDataOptionString) throws GLDBException {
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
private static void reload() throws GLDBException {
  reloadPetTypes();
  reloadPets();
}
//--------------------------------------------------------------------------------------------------
private static final String[] PetNamesAndSex = new String[] {"Angel,F", "Ashley,F", "Bandit,M",
    "Beau,M", "Bella,F", "Bo,M", "Boomer,M", "Bubba,M", "Buddy,M", "Buster,M", "Callie,F",
    "Calvin,M", "Cassie,F", "Champ,M", "Chelsea,F", "Chester,M", "Cleopatra,F", "Cody,M",
    "Cookie,F", "Cooper,M", "Dexter,M", "Diesel,M", "Dixie,F", "Duke,M", "Duncan,M", "Dusty,M",
    "Emily,F", "Emma,F", "Felix,M", "Female,F", "Fred,M", "Grace,F", "Guinness,M", "Haley,F",
    "Harry,M", "Hunter,M", "Isabella,F", "Jack,M", "Jasmine,F", "Joey,M", "Junior,M", "Kitty,F",
    "Kobe,M", "Leo,M", "Loki,U", "Lucy,F", "Maddie,F", "Mandy,F", "Marley,M", "Max,M", "Maximus,M",
    "Maxwell,M", "Maya,F", "Merlin,M", "Mia,F", "Mickey,M", "Mikey,M", "Millie,F", "Milo,U",
    "Misty,F", "Mocha,U", "Moose,M", "Morgan,U", "Murphy,M", "Nala,U", "Nikki,F", "Olivia,F",
    "Oreo,U", "Pebbles,F", "Penny,F", "Rex,M", "Roxie,F", "Rufus,M", "Sabrina,F", "Sadie,F",
    "Sam,U", "Samantha,F", "Sarah,F", "Sassy,U", "Scooter,M", "Sebastian,M", "Sheba,F", "Simba,M",
    "Snowball,U", "Socks,U", "Sophia,F", "Sophie,F", "Spencer,M", "Sunny,F", "Sylvester,M",
    "Taz,M", "Thomas,M", "Tinkerbell,F", "Toby,M", "Tommy,M", "Tucker,M", "Winston,M", "Ziggy,M",
    "Zoe,F", "Zoey,F"                        };
private static void reloadPets() throws GLDBException {
  truncate(EGXTTestbedTable.Pet);
  final List<Integer> petTypeIdList = reloadPetsGetPetTypeIdList();
  for (final String petNameAndSex : PetNamesAndSex) {
    final GLSQL petSQL = GLSQL.insert(EGXTTestbedTable.Pet.name(), false);
    final String[] nameAndSex = petNameAndSex.split(",");
    final String intakeDate = GLUtil.dateAddDays("20130501", GLUtil.getRandomInt(365));
    final int hour = 6 + GLUtil.getRandomInt(12);
    final int minute = GLUtil.getRandomInt(4) * 15;
    final String intakeTime = (hour < 10 ? "0" : "") + hour + (minute < 10 ? "0" : "") + //
                              minute + "00";
    final String fosterDate = GLUtil.getRandomInt(3) == 0 ? null : GLUtil.dateAddDays(intakeDate, //
                                                                                      60);
    petSQL.setValue(Pet.AdoptionFee.name(), GLUtil.getRandomInt(3000, 10000) / 100.0);
    petSQL.setValue(Pet.FosterDate.name(), fosterDate);
    petSQL.setValue(Pet.IntakeDate.name(), intakeDate + intakeTime);
    petSQL.setValue(Pet.NumberOfFosters.name(), GLUtil.getRandomInt(5));
    petSQL.setValue(Pet.PetId.name(), GLNextId.getNextIdValue(EGXTTestbedTable.Pet.name(), 1));
    petSQL.setValue(Pet.PetName.name(), nameAndSex[0]);
    petSQL.setValue(Pet.PetTypeId.name(),
                    petTypeIdList.get(GLUtil.getRandomInt(petTypeIdList.size())));
    petSQL.setValue(Pet.Sex.name(), nameAndSex[1]);
    petSQL.setValue(Pet.TrainedFlag.name(), GLUtil.getRandomInt(2) == 0 ? "Y" : "N");
    petSQL.execute(false);
  }
}
//--------------------------------------------------------------------------------------------------
private static List<Integer> reloadPetsGetPetTypeIdList() throws GLDBException {
  final List<Integer> result = Lists.newArrayList();
  final GLSQL petTypeSQL = GLSQL.select();
  petTypeSQL.from(EGXTTestbedTable.PetType.name());
  petTypeSQL.open();
  try {
    while (petTypeSQL.next(false)) {
      result.add(petTypeSQL.asInt(PetType.PetTypeId.name()));
    }
  }
  finally {
    petTypeSQL.close();
  }
  return result;
}
//--------------------------------------------------------------------------------------------------
private static final String[] PetTypes = new String[] {"Cat,Cat", "Dog,Dog", "Hedgedog,Hedgehog",
    "Invisible Pink Unicorn,IPU"       };
private static void reloadPetTypes() throws GLDBException {
  truncate(EGXTTestbedTable.PetType);
  int nextPetTypeId = 1;
  for (final String petType : PetTypes) {
    final String[] petTypeFields = petType.split(",");
    final GLSQL petTypeSQL = GLSQL.insert(EGXTTestbedTable.PetType.name(), false);
    petTypeSQL.setValue(PetType.PetTypeDesc.name(), petTypeFields[0]);
    petTypeSQL.setValue(PetType.PetTypeShortDesc.name(), petTypeFields[1]);
    petTypeSQL.setValue(Pet.PetTypeId.name(), nextPetTypeId++);
    petTypeSQL.execute();
  }
  GLNextId.getNextIdValue(EGXTTestbedTable.PetType.name(), PetTypes.length + 1);
}
//--------------------------------------------------------------------------------------------------
private static void truncate(final EGXTTestbedTable table) throws GLDBException {
  GLDBUtil.execSQL("truncate " + table);
}
//--------------------------------------------------------------------------------------------------
}