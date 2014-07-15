package org.greatlogic.glgwt.shared;

import java.util.ArrayList;
import org.greatlogic.glgwt.client.widget.GLValidationRecord;

public interface IGLRecordValidator {
//--------------------------------------------------------------------------------------------------
public ArrayList<GLValidationError> validate(final GLValidationRecord validationRecord);
//--------------------------------------------------------------------------------------------------
}