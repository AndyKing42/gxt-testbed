package org.greatlogic.gxttestbed.client.widget;
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
import java.util.TreeMap;
import org.greatlogic.gxttestbed.client.glgwt.GLGridWidget;

public class GridWidgetManager {
//--------------------------------------------------------------------------------------------------
private static TreeMap<String, GLGridWidget> _gridWidgetMap; // grid name -> GLValueMapGridWidget

//--------------------------------------------------------------------------------------------------
static {
  _gridWidgetMap = new TreeMap<String, GLGridWidget>();
}
//--------------------------------------------------------------------------------------------------
public static PetGridWidget getPetGrid(final String gridName) {
  PetGridWidget result = (PetGridWidget)_gridWidgetMap.get(gridName);
  if (result == null) {
    result = new PetGridWidget();
    _gridWidgetMap.put(gridName, result);
  }
  return result;
}
//--------------------------------------------------------------------------------------------------
}