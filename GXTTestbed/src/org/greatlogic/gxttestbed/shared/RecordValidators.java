package org.greatlogic.gxttestbed.shared;

import java.util.ArrayList;
import org.greatlogic.glgwt.client.core.GLRecord;
import org.greatlogic.glgwt.shared.GLValidationError;
import org.greatlogic.glgwt.shared.IGLRecordValidator;

public abstract class RecordValidators {
//--------------------------------------------------------------------------------------------------
private static boolean _validatorsCreated;
//--------------------------------------------------------------------------------------------------
public static void createValidators() {
  if (!_validatorsCreated) {
    _validatorsCreated = true;
    createPetRecordValidator();
  }
}
//--------------------------------------------------------------------------------------------------
private static IGLRecordValidator createPetRecordValidator() {
  return new IGLRecordValidator() {
    @Override
    public final ArrayList<GLValidationError> validate(final GLRecord record) {
      ArrayList<GLValidationError> result = null;
      final int numberOfFosters = record.asInt(IDBEnums.Pet.NumberOfFosters);
      if (numberOfFosters > 6) {
        result = result == null ? new ArrayList<GLValidationError>() : result;
        result.add(new GLValidationError(IDBEnums.Pet.NumberOfFosters,
                                         "Number of fosters must be less than 7"));
      }
      return result;
    }
  };
}
//--------------------------------------------------------------------------------------------------
}