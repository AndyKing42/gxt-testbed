package org.greatlogic.gxttestbed.client.glgwt;

import org.greatlogic.gxttestbed.client.glgwt.IGLEnums.EGLColumnDataType;
import org.greatlogic.gxttestbed.shared.IDBEnums.EGXTExamplesTable;
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
public interface IGLColumn {
//--------------------------------------------------------------------------------------------------
public String[] getChoices();
public EGLColumnDataType getDataType();
public int getDefaultGridColumnWidth();
public int getNumberOfDecimalPlaces();
public IGLColumn getParentDisplayColumn();
public EGXTExamplesTable getParentTable();
public String getTitle();
//--------------------------------------------------------------------------------------------------
}