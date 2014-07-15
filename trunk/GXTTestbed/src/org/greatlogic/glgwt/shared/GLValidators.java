package org.greatlogic.glgwt.shared;

import java.util.TreeMap;

public abstract class GLValidators {
//--------------------------------------------------------------------------------------------------
private final TreeMap<IGLTable, IGLRecordValidator> _recordValidatorMap;
//--------------------------------------------------------------------------------------------------
protected GLValidators() {
  _recordValidatorMap = new TreeMap<>();
}
//--------------------------------------------------------------------------------------------------
public void addRecordValidator(final IGLTable table, final IGLRecordValidator recordValidator) {
  _recordValidatorMap.put(table, recordValidator);
}
//--------------------------------------------------------------------------------------------------
public IGLRecordValidator getRecordValidator(final IGLTable table) {
  return _recordValidatorMap.get(table);
}
//--------------------------------------------------------------------------------------------------
}