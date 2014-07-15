package org.greatlogic.gxttestbed.shared;

import java.math.BigDecimal;
import java.util.List;
import org.greatlogic.glgwt.client.widget.GLValidationRecord;
import org.greatlogic.glgwt.shared.GLRecordValidator;
import org.greatlogic.glgwt.shared.GLValidators;
import org.greatlogic.gxttestbed.shared.IDBEnums.EGXTTestbedTable;
import org.greatlogic.gxttestbed.shared.IDBEnums.Pet;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorError;
import com.sencha.gxt.widget.core.client.form.Validator;

public class Validators extends GLValidators {
//--------------------------------------------------------------------------------------------------
public Validators() {
  super();
  addPetValidators();
}
//--------------------------------------------------------------------------------------------------
private void addPetValidators() {
  addColumnValidator(Pet.NumberOfFosters, new Validator<Integer>() {
    @Override
    public List<EditorError> validate(final Editor<Integer> editor, final Integer value) {
      return null;
    }
  });
  addRecordValidator(EGXTTestbedTable.Pet, new GLRecordValidator() {
    @Override
    public void validateRecord(final GLValidationRecord validationRecord) {
      if (validationRecord.asInt(Pet.NumberOfFosters) < 0) {
        addError(Pet.NumberOfFosters, "Number of fosters cannot be less than zero");
      }
      if (validationRecord.asDec(Pet.AdoptionFee).compareTo(BigDecimal.ZERO) < 0) {
        addError(Pet.AdoptionFee, "Adoption fee cannot be less than zero");
      }
    }
  });
}
//--------------------------------------------------------------------------------------------------
}