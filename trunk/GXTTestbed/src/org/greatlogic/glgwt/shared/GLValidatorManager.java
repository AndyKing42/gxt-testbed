package org.greatlogic.glgwt.shared;

import java.util.TreeMap;

public class GLValidatorManager {
//--------------------------------------------------------------------------------------------------
public static final GLValidatorManager              Instance;
private final TreeMap<IGLTable, IGLRecordValidator> _recordValidatorMap;
//--------------------------------------------------------------------------------------------------
static {
  Instance = new GLValidatorManager();
}
//--------------------------------------------------------------------------------------------------
private GLValidatorManager() {
  _recordValidatorMap = new TreeMap<>();
}
//--------------------------------------------------------------------------------------------------
public void addRecordValidator(final IGLTable table, final IGLRecordValidator recordValidator) {
  _recordValidatorMap.put(table, recordValidator);
}
//--------------------------------------------------------------------------------------------------
}