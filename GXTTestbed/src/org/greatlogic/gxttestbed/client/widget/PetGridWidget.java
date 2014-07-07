package org.greatlogic.gxttestbed.client.widget;
/*
 * Copyright 2006-2014 Andy King (GreatLogic.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
import org.greatlogic.glgwt.client.core.IGLGridRowEditingValidator;
import org.greatlogic.glgwt.client.widget.GLGridWidget;
import org.greatlogic.glgwt.client.widget.GLValidationRecord;
import org.greatlogic.gxttestbed.shared.IDBEnums.Pet;

public class PetGridWidget extends GLGridWidget {
//--------------------------------------------------------------------------------------------------
public PetGridWidget(final boolean inlineEditing, final boolean useCheckBoxSelectionModel,
                     final Pet... petColumns) {
  super(null, "There are no pets", inlineEditing, useCheckBoxSelectionModel, petColumns);
  setGridRowEditingValidator(createGridRowEditingValidator());
}
//--------------------------------------------------------------------------------------------------
private IGLGridRowEditingValidator createGridRowEditingValidator() {
  return new IGLGridRowEditingValidator() {
    @Override
    public boolean validate(final GLValidationRecord validationRecord) {
      final int numberOfFosters = validationRecord.asInt(Pet.NumberOfFosters);
      if (numberOfFosters > 6) {
        validationRecord.setInvalid(Pet.NumberOfFosters, "Number of fosters must be less than 7");
        return false;
      }
      return true;
    }
  };
}
//--------------------------------------------------------------------------------------------------
}