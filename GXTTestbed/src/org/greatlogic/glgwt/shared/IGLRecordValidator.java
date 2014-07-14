package org.greatlogic.glgwt.shared;

import java.util.ArrayList;
import org.greatlogic.glgwt.client.core.GLRecord;

public interface IGLRecordValidator {
//--------------------------------------------------------------------------------------------------
public ArrayList<GLValidationError> validate(final GLRecord record);
//--------------------------------------------------------------------------------------------------
}