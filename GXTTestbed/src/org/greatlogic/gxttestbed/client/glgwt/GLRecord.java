package org.greatlogic.gxttestbed.client.glgwt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map.Entry;
/**
 * A thin wrapper around a list of field values. The objective is to provide easy access to the
 * values in the list, converting the string values to any of a number of basic data types.
 */
public class GLRecord {
//--------------------------------------------------------------------------------------------------
private ArrayList<String>       _changedFieldNameList;
private final GLRecordDef       _recordDef;
private final ArrayList<Object> _valueList;
//--------------------------------------------------------------------------------------------------
/**
 * Creates a new (empty) GLRecord.
 * @param recordDef The record definition associated with this record. This is used when values are
 * retrieved using the field name or column.
 */
public GLRecord(final GLRecordDef recordDef) {
  this(recordDef, null);
}
//--------------------------------------------------------------------------------------------------
/**
 * Creates a new GLRecord.
 * @param recordDef The record definition associated with this record. This is used when values are
 * retrieved using the field name or column.
 * @param list The list of values for this record. Note that the List object is referenced from the
 * GLRecord object, and must not therefore be modified after being passed to the GLRecord
 * constructor.
 */
public GLRecord(final GLRecordDef recordDef, final ArrayList<Object> list) {
  _recordDef = recordDef;
  _valueList = list == null ? new ArrayList<Object>(_recordDef.getNumberOfFields()) : list;
}
//--------------------------------------------------------------------------------------------------
public boolean asBoolean(final IGLColumn column) throws GLInvalidFieldOrColumnException {
  return asBoolean(column, false);
}
//--------------------------------------------------------------------------------------------------
public boolean asBoolean(final IGLColumn column, final boolean defaultValue)
  throws GLInvalidFieldOrColumnException {
  return asBoolean(column.toString(), defaultValue);
}
//--------------------------------------------------------------------------------------------------
public boolean asBoolean(final String fieldName) throws GLInvalidFieldOrColumnException {
  return asBoolean(fieldName, false);
}
//--------------------------------------------------------------------------------------------------
public boolean asBoolean(final String fieldName, final boolean defaultValue)
  throws GLInvalidFieldOrColumnException {
  return GLUtil.stringToBoolean(asString(fieldName), defaultValue);
}
//--------------------------------------------------------------------------------------------------
public BigDecimal asDec(final IGLColumn column) throws GLInvalidFieldOrColumnException {
  return asDec(column, BigDecimal.ZERO);
}
//--------------------------------------------------------------------------------------------------
public BigDecimal asDec(final IGLColumn column, final BigDecimal defaultValue)
  throws GLInvalidFieldOrColumnException {
  return asDec(column.toString(), defaultValue);
}
//--------------------------------------------------------------------------------------------------
public BigDecimal asDec(final String fieldName) throws GLInvalidFieldOrColumnException {
  return asDec(fieldName, BigDecimal.ZERO);
}
//--------------------------------------------------------------------------------------------------
public BigDecimal asDec(final String fieldName, final BigDecimal defaultValue)
  throws GLInvalidFieldOrColumnException {
  return GLUtil.stringToDec(asString(fieldName), defaultValue);
}
//--------------------------------------------------------------------------------------------------
public double asDouble(final IGLColumn column) throws GLInvalidFieldOrColumnException {
  return asDouble(column, 0);
}
//--------------------------------------------------------------------------------------------------
public double asDouble(final IGLColumn column, final double defaultValue)
  throws GLInvalidFieldOrColumnException {
  return asDouble(column.toString(), defaultValue);
}
//--------------------------------------------------------------------------------------------------
public double asDouble(final String fieldName) throws GLInvalidFieldOrColumnException {
  return asDouble(fieldName, 0);
}
//--------------------------------------------------------------------------------------------------
public double asDouble(final String fieldName, final double defaultValue)
  throws GLInvalidFieldOrColumnException {
  return GLUtil.stringToDouble(asString(fieldName), defaultValue);
}
//--------------------------------------------------------------------------------------------------
public float asFloat(final IGLColumn column) throws GLInvalidFieldOrColumnException {
  return asFloat(column, 0);
}
//--------------------------------------------------------------------------------------------------
public float asFloat(final IGLColumn column, final float defaultValue)
  throws GLInvalidFieldOrColumnException {
  return asFloat(column.toString(), defaultValue);
}
//--------------------------------------------------------------------------------------------------
public float asFloat(final String fieldName) throws GLInvalidFieldOrColumnException {
  return asFloat(fieldName, 0);
}
//--------------------------------------------------------------------------------------------------
public float asFloat(final String fieldName, final float defaultValue)
  throws GLInvalidFieldOrColumnException {
  return (float)asDouble(fieldName, defaultValue);
}
//--------------------------------------------------------------------------------------------------
public int asInt(final IGLColumn column) throws GLInvalidFieldOrColumnException {
  return asInt(column, 0);
}
//--------------------------------------------------------------------------------------------------
public int asInt(final IGLColumn column, final int defaultValue)
  throws GLInvalidFieldOrColumnException {
  return asInt(column.toString(), defaultValue);
}
//--------------------------------------------------------------------------------------------------
public int asInt(final String fieldName) throws GLInvalidFieldOrColumnException {
  return asInt(fieldName, 0);
}
//--------------------------------------------------------------------------------------------------
public int asInt(final String fieldName, final int defaultValue)
  throws GLInvalidFieldOrColumnException {
  return (int)asLong(fieldName, defaultValue);
}
//--------------------------------------------------------------------------------------------------
public long asLong(final IGLColumn column) throws GLInvalidFieldOrColumnException {
  return asLong(column, 0);
}
//--------------------------------------------------------------------------------------------------
public long asLong(final IGLColumn column, final long defaultValue)
  throws GLInvalidFieldOrColumnException {
  return asLong(column.toString(), defaultValue);
}
//--------------------------------------------------------------------------------------------------
public long asLong(final String fieldName) throws GLInvalidFieldOrColumnException {
  return asLong(fieldName, 0);
}
//--------------------------------------------------------------------------------------------------
public long asLong(final String fieldName, final long defaultValue)
  throws GLInvalidFieldOrColumnException {
  return GLUtil.stringToLong(asString(fieldName), defaultValue);
}
//--------------------------------------------------------------------------------------------------
public Object asObject(final IGLColumn column) throws GLInvalidFieldOrColumnException {
  return asObject(column.toString());
}
//--------------------------------------------------------------------------------------------------
public Object asObject(final String fieldName) throws GLInvalidFieldOrColumnException {
  return _valueList.get(_recordDef.getFieldIndex(fieldName));
}
//--------------------------------------------------------------------------------------------------
public String asString(final IGLColumn column) throws GLInvalidFieldOrColumnException {
  return asString(column, "");
}
//--------------------------------------------------------------------------------------------------
public String asString(final IGLColumn column, final String defaultValue)
  throws GLInvalidFieldOrColumnException {
  return asString(column.toString(), defaultValue);
}
//--------------------------------------------------------------------------------------------------
public String asString(final String fieldName) throws GLInvalidFieldOrColumnException {
  return asString(fieldName, "");
}
//--------------------------------------------------------------------------------------------------
/**
 * Returns a string representation of the value retrieved using <code>fieldName</code>. If the value
 * is a <code>Date</code> type value then the result will be returned as a date/time in the format
 * YYYYMMDDHHMMSS.
 * @param fieldName The field name of the value that is to be returned.
 * @param defaultValue The default value that will be returned if there is no entry in the map for
 * the specified field name.
 * @return A string representation of the requested value.
 */
public String asString(final String fieldName, final String defaultValue)
  throws GLInvalidFieldOrColumnException {
  return GLUtil.formatObjectSpecial(_valueList.get(_recordDef.getFieldIndex(fieldName)),
                                    defaultValue);
}
//--------------------------------------------------------------------------------------------------
public ArrayList<String> getChangedFieldNameList() {
  if (_changedFieldNameList == null) {
    _changedFieldNameList = new ArrayList<String>();
  }
  return _changedFieldNameList;
}
//--------------------------------------------------------------------------------------------------
public String getKeyValueAsString() throws GLInvalidFieldOrColumnException {
  return asString(_recordDef.getKeyFieldName());
}
//--------------------------------------------------------------------------------------------------
public Object put(final String fieldName, final Object value)
  throws GLInvalidFieldOrColumnException {
  final int fieldIndex = _recordDef.getFieldIndex(fieldName);
  return _valueList.set(fieldIndex, value);
}
//--------------------------------------------------------------------------------------------------
public GLRecordDef getRecordDef() {
  return _recordDef;
}
//--------------------------------------------------------------------------------------------------
public Object put(final IGLColumn column, final Object value)
  throws GLInvalidFieldOrColumnException {
  return put(column.toString(), value);
}
//--------------------------------------------------------------------------------------------------
@Override
public String toString() {
  final StringBuilder sb = new StringBuilder(_valueList.size() * 20);
  try {
    boolean firstTime = true;
    for (final Entry<String, Integer> fieldEntry : _recordDef.getFieldIndexByFieldNameMap()
                                                             .entrySet()) {
      final String fieldName = fieldEntry.getKey();
      sb.append(firstTime ? "" : ";").append(fieldName).append(":").append(asString(fieldName));
      firstTime = false;
    }
  }
  catch (final GLInvalidFieldOrColumnException ifoce) {
    // this should never happen
  }
  return sb.toString();
}
//--------------------------------------------------------------------------------------------------
}