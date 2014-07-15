package org.greatlogic.gxttestbed.shared;

import java.util.ArrayList;
import org.greatlogic.glgwt.client.widget.GLValidationRecord;
import org.greatlogic.glgwt.shared.GLValidationError;
import org.greatlogic.glgwt.shared.GLValidators;
import org.greatlogic.glgwt.shared.IGLRecordValidator;
import org.greatlogic.gxttestbed.shared.IDBEnums.EGXTTestbedTable;
import org.greatlogic.gxttestbed.shared.IDBEnums.Pet;

public class Validators extends GLValidators {
//--------------------------------------------------------------------------------------------------
public Validators() {
  super();
  addRecordValidator(EGXTTestbedTable.Pet, createPetRecordValidator());
}
//--------------------------------------------------------------------------------------------------
private IGLRecordValidator createPetRecordValidator() {
  return new IGLRecordValidator() {
    @Override
    public ArrayList<GLValidationError> validate(final GLValidationRecord validationRecord) {
      ArrayList<GLValidationError> result = null;
      final int numberOfFosters = validationRecord.asInt(Pet.NumberOfFosters);
      if (numberOfFosters > 6) {
        result = result == null ? new ArrayList<GLValidationError>() : result;
        result.add(new GLValidationError(Pet.NumberOfFosters,
                                         "Number of fosters must be less than 7"));
      }
      return result;
    }
  };
}
//--------------------------------------------------------------------------------------------------
}