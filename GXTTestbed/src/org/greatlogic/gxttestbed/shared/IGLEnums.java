package org.greatlogic.gxttestbed.shared;
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
public enum EGLSQLType {
Delete,
Insert,
Select,
Update
}
//--------------------------------------------------------------------------------------------------
}