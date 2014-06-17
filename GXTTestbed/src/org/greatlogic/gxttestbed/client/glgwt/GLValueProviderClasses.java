package org.greatlogic.gxttestbed.client.glgwt;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.TreeMap;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.sencha.gxt.core.client.ValueProvider;

abstract class GLValueProviderClasses {
//==================================================================================================
public static class GLBigDecimalValueProvider implements ValueProvider<GLRecord, BigDecimal> {
private final IGLColumn _column;
private final int       _numberOfDecimalPlaces;
public GLBigDecimalValueProvider(final IGLColumn column, final int numberOfDecimalPlaces) {
  _column = column;
  _numberOfDecimalPlaces = numberOfDecimalPlaces;
}
@Override
public BigDecimal getValue(final GLRecord record) {
  try {
    return record.asDec(_column);
  }
  catch (final GLInvalidFieldOrColumnException ifoce) {
    return BigDecimal.ZERO;
  }
}
@Override
public void setValue(final GLRecord record, final BigDecimal value) {
  try {
    record.put(_column, value.setScale(_numberOfDecimalPlaces, RoundingMode.HALF_UP));
  }
  catch (final GLInvalidFieldOrColumnException ifoce) {
    //
  }
}
@Override
public String getPath() {
  return _column.toString();
}
}
//==================================================================================================
public static class GLBooleanValueProvider implements ValueProvider<GLRecord, Boolean> {
private final IGLColumn _column;
public GLBooleanValueProvider(final IGLColumn column) {
  _column = column;
}
@Override
public Boolean getValue(final GLRecord record) {
  try {
    return record.asBoolean(_column);
  }
  catch (final GLInvalidFieldOrColumnException ifoce) {
    return false;
  }
}
@Override
public void setValue(final GLRecord record, final Boolean value) {
  try {
    record.put(_column, value);
  }
  catch (final GLInvalidFieldOrColumnException ifoce) {
    //
  }
}
@Override
public String getPath() {
  return _column.toString();
}
}
//==================================================================================================
public static class GLDateValueProvider implements ValueProvider<GLRecord, Date> {
private final IGLColumn _column;
public GLDateValueProvider(final IGLColumn column) {
  _column = column;
}
@SuppressWarnings("deprecation")
@Override
public Date getValue(final GLRecord record) {
  try {
    final String date = record.asString(_column);
    if (date.length() < 8) {
      return null;
    }
    final int year = GLUtil.stringToInt(date.substring(0, 4));
    final int month = GLUtil.stringToInt(date.substring(4, 6));
    final int day = GLUtil.stringToInt(date.substring(6, 8));
    return new Date(year - 1900, month - 1, day);
  }
  catch (final GLInvalidFieldOrColumnException ifoce) {
    return null;
  }
}
@Override
public void setValue(final GLRecord record, final Date value) {
  try {
    record.put(_column, DateTimeFormat.getFormat("yyyyMMdd").format(value));
  }
  catch (final GLInvalidFieldOrColumnException ifoce) {
    //
  }
}
@Override
public String getPath() {
  return _column.toString();
}
}
//==================================================================================================
public static class GLForeignKeyValueProvider implements ValueProvider<GLRecord, String> {
private final TreeMap<Integer, String> _parentDisplayValueMap;
private final IGLColumn                _column;
public GLForeignKeyValueProvider(final IGLColumn column) {
  _column = column;
  _parentDisplayValueMap = new TreeMap<Integer, String>();
  _parentDisplayValueMap.put(1, "Dog");
  _parentDisplayValueMap.put(2, "Cat");
  _parentDisplayValueMap.put(3, "Unicorn");
  _parentDisplayValueMap.put(4, "FSM");
}
@Override
public String getValue(final GLRecord record) {
  String result;
  try {
    result = _parentDisplayValueMap.get(record.asInt(_column));
    return result;
  }
  catch (final GLInvalidFieldOrColumnException ifoce) {
    return "";
  }
}
@Override
public void setValue(final GLRecord record, final String value) {
  try {
    record.put(_column, value);
  }
  catch (final GLInvalidFieldOrColumnException ifoce) {
    //
  }
}
@Override
public String getPath() {
  return _column.toString();
}
}
//==================================================================================================
public static class GLIntegerValueProvider implements ValueProvider<GLRecord, Integer> {
private final IGLColumn _column;
public GLIntegerValueProvider(final IGLColumn column) {
  _column = column;
}
@Override
public Integer getValue(final GLRecord record) {
  try {
    return record.asInt(_column);
  }
  catch (final GLInvalidFieldOrColumnException ifoce) {
    return 0;
  }
}
@Override
public void setValue(final GLRecord record, final Integer value) {
  try {
    record.put(_column, value);
  }
  catch (final GLInvalidFieldOrColumnException ifoce) {
    //
  }
}
@Override
public String getPath() {
  return _column.toString();
}
}
//==================================================================================================
public static class GLStringValueProvider implements ValueProvider<GLRecord, String> {
private final IGLColumn _column;
public GLStringValueProvider(final IGLColumn column) {
  _column = column;
}
@Override
public String getValue(final GLRecord record) {
  try {
    return record.asString(_column);
  }
  catch (final GLInvalidFieldOrColumnException ifoce) {
    return "";
  }
}
@Override
public void setValue(final GLRecord record, final String value) {
  try {
    record.put(_column, value);
  }
  catch (final GLInvalidFieldOrColumnException ifoce) {
    //
  }
}
@Override
public String getPath() {
  return _column.toString();
}
}
//==================================================================================================
}