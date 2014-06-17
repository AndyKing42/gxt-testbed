package org.greatlogic.gxttestbed.client.glgwt;

import java.util.TreeMap;
/**
 * Provides a mapping of field names to field indexes.
 */
public class GLRecordDef {
//--------------------------------------------------------------------------------------------------
private final TreeMap<String, Integer> _fieldIndexByFieldNameMap; // field name -> field index
private final String                   _keyFieldName;
private final IGLTable                 _table;
//--------------------------------------------------------------------------------------------------
public GLRecordDef(final IGLTable table, final Object[] fieldNames, final Object keyFieldName) {
  _table = table;
  _fieldIndexByFieldNameMap = new TreeMap<String, Integer>(String.CASE_INSENSITIVE_ORDER);
  for (int fieldNameIndex = 0; fieldNameIndex < fieldNames.length; ++fieldNameIndex) {
    _fieldIndexByFieldNameMap.put(fieldNames[fieldNameIndex].toString(), fieldNameIndex);
  }
  _keyFieldName = keyFieldName.toString();
}
//--------------------------------------------------------------------------------------------------
public int getFieldIndex(final IGLColumn column) throws GLInvalidFieldOrColumnException {
  return getFieldIndex(column.toString());
}
//--------------------------------------------------------------------------------------------------
public int getFieldIndex(final String fieldName) throws GLInvalidFieldOrColumnException {
  final Integer result = _fieldIndexByFieldNameMap.get(fieldName);
  if (result == null) {
    throw new GLInvalidFieldOrColumnException(fieldName);
  }
  return result;
}
//--------------------------------------------------------------------------------------------------
public TreeMap<String, Integer> getFieldIndexByFieldNameMap() {
  return _fieldIndexByFieldNameMap;
}
//--------------------------------------------------------------------------------------------------
public String getKeyFieldName() {
  return _keyFieldName;
}
//--------------------------------------------------------------------------------------------------
public int getNumberOfFields() {
  return _fieldIndexByFieldNameMap.size();
}
//--------------------------------------------------------------------------------------------------
public IGLTable getTable() {
  return _table;
}
//--------------------------------------------------------------------------------------------------
@Override
public String toString() {
  return "Key:" + _keyFieldName + " Columns:" + _fieldIndexByFieldNameMap.keySet();
}
//--------------------------------------------------------------------------------------------------
}