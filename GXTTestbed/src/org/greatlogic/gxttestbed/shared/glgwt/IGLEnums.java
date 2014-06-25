package org.greatlogic.gxttestbed.shared.glgwt;

import java.util.TreeMap;
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
public enum EGLLogLevel implements Comparable<EGLLogLevel> {
//These are sorted from least to greatest in importance
Debug(10),
InfoDetail(20),
InfoSummary(30),
Minor(40),
Major(50),
Critical(60),
Unknown(-1);
private static TreeMap<Integer, EGLLogLevel> _logLevelByPriorityMap;
private final int                            _priority;
private EGLLogLevel(final int priority) {
  _priority = priority;
}
public int getPriority() {
  return _priority;
}
public static EGLLogLevel lookupUsingPriority(final int priority) {
  EGLLogLevel result;
  if (_logLevelByPriorityMap == null) {
    _logLevelByPriorityMap = new TreeMap<>();
    for (final EGLLogLevel logLevel : EGLLogLevel.values()) {
      _logLevelByPriorityMap.put(logLevel._priority, logLevel);
    }
  }
  result = _logLevelByPriorityMap.get(priority);
  return result == null ? EGLLogLevel.Critical : result;
}
public EGLLogLevel next() {
  return ordinal() < EGLLogLevel.values().length - 1 ? EGLLogLevel.values()[ordinal() + 1] : null;
}
}
//--------------------------------------------------------------------------------------------------
public enum EGLSQLType {
Delete,
Insert,
Select,
Update
}
//--------------------------------------------------------------------------------------------------
}