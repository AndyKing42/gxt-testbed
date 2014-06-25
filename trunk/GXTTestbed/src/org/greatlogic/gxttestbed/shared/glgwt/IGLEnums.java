package org.greatlogic.gxttestbed.shared.glgwt;

import java.util.Map;
import java.util.logging.Level;
import com.google.common.collect.Maps;
import com.greatlogic.glbase.gllib.GLUtil;
import com.greatlogic.glbase.gllib.IGLLibEnums.EGLLogAttribute;
import com.greatlogic.glbase.glxml.IGLXMLAttributeEnum;

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
public interface IGLEnums {
//--------------------------------------------------------------------------------------------------
public enum EGLColumnDataType {
Boolean(false),
Currency(true),
Date(false),
DateTime(false),
Decimal(true),
Int(true),
String(false);
private final boolean _numeric;
private EGLColumnDataType(final boolean numeric) {
  _numeric = numeric;
}
public boolean getNumeric() {
  return _numeric;
}
}
//--------------------------------------------------------------------------------------------------
public enum EGLDBConj {
And,
Or
}
//--------------------------------------------------------------------------------------------------
public enum EGLDBException {
InvalidSQLRequest,
ParameterIsNull
}
//--------------------------------------------------------------------------------------------------
public enum EGLDBOp {
Equals("="),
EqualsExp("="),
GreaterThan(">"),
GreaterThanExp(">"),
GreaterThanOrEqual(">="),
GreaterThanOrEqualExp(">="),
In("in"),
IsNotNull("is not null"),
IsNotNullOrBlank("is not null"),
IsNull("is null"),
IsNullOrBlank("is null"),
LessThan("<"),
LessThanExp("<"),
LessThanOrEqual("<="),
LessThanOrEqualExp("<="),
Like("like"),
NotEqual("<>"),
NotEqualExp("<>"),
NotLike("not like");
private final String _sql;
private EGLDBOp(final String sql) {
  _sql = sql;
}
public String getSQL() {
  return _sql;
}
}
//--------------------------------------------------------------------------------------------------
public enum EGLJoinType {
Inner,
Left,
Outer,
Right
}
//--------------------------------------------------------------------------------------------------
// NOTE: this is just a copy of the EGLLogLevel enum from the com.greatlogic.glbase.gllib version.
public enum EGLLogLevel implements Comparable<EGLLogLevel> {
//These are sorted from least to greatest in importance
Debug(10, EGLLogAttribute.Debug, "     ", Level.FINEST), // used during development
InfoDetail(20, EGLLogAttribute.InfoDetail, "*    ", Level.FINER), // used for real time debugging
InfoSummary(30, EGLLogAttribute.InfoSummary, "*    ", Level.FINE), // used for real time debugging
Minor(40, EGLLogAttribute.Minor, "**   ", Level.INFO), // not important enough to prevent operation -- just a warning
Major(50, EGLLogAttribute.Major, "***  ", Level.WARNING), // the problem probably requires attention
Critical(60, EGLLogAttribute.Critical, "**** ", Level.SEVERE), // the program probably should/will not continue running
Unknown(-1, EGLLogAttribute.Unknown, "**** ", Level.SEVERE);
private static Map<Integer, EGLLogLevel> _logLevelByIDMap;
private final IGLXMLAttributeEnum        _attributeEnum;
private final Level                      _loggerLevel;
private String                           _logString;
private final int                        _priority;
private EGLLogLevel(final int priority, final IGLXMLAttributeEnum attributeEnum,
                    final String defaultLogString, final Level loggerLevel) {
  _priority = priority;
  _attributeEnum = attributeEnum;
  setLogString(defaultLogString);
  _loggerLevel = loggerLevel;
} // GLLogLevel()
String getLogString() {
  return _logString;
} // getLogString()
IGLXMLAttributeEnum getAttributeEnum() {
  return _attributeEnum;
} // getXMLAttribute()
public Level getLoggerLevel() {
  return _loggerLevel;
}
public int getPriority() {
  return _priority;
}
static EGLLogLevel lookup(final String lookupString) {
  return (EGLLogLevel)GLUtil.enumLookup(EGLLogLevel.class, lookupString, Unknown);
} // lookup()
public static EGLLogLevel lookupUsingPriority(final int priority) {
  EGLLogLevel result;
  if (_logLevelByIDMap == null) {
    _logLevelByIDMap = Maps.newTreeMap();
    for (final EGLLogLevel logLevel : EGLLogLevel.values()) {
      _logLevelByIDMap.put(logLevel._priority, logLevel);
    }
  }
  result = _logLevelByIDMap.get(priority);
  return result == null ? EGLLogLevel.Critical : result;
}
public EGLLogLevel next() {
  return ordinal() < EGLLogLevel.values().length - 1 ? EGLLogLevel.values()[ordinal() + 1] : null;
}
void setLogString(final String logString) {
  _logString = logString;
} // setLogString()
} // enum EGLLogLevel
//--------------------------------------------------------------------------------------------------
public enum EGLSQLType {
Delete,
Insert,
Select,
Update
}
//--------------------------------------------------------------------------------------------------
}