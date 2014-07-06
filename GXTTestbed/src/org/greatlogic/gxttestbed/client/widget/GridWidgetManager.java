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
import org.greatlogic.glgwt.client.core.GLLog;
import org.greatlogic.glgwt.client.widget.GLGridWidget;
import org.greatlogic.gxttestbed.shared.IDBEnums.Pet;

public class GridWidgetManager {
//--------------------------------------------------------------------------------------------------
private static TreeMap<String, GridWidgetInfo> _gridWidgetInfoMap; /* grid name -> GLGridWidgetInfo */
//--------------------------------------------------------------------------------------------------
private static class GridWidgetInfo {
private final GLGridWidget _gridWidget;
private final boolean      _inlineEditing;
private final boolean      _useCheckBoxSelectionModel;
private GridWidgetInfo(final PetGridWidget gridWidget, final boolean inlineEditing,
                       final boolean useCheckBoxSelectionModel) {
  _gridWidget = gridWidget;
  _inlineEditing = inlineEditing;
  _useCheckBoxSelectionModel = useCheckBoxSelectionModel;
}
}
//--------------------------------------------------------------------------------------------------
static {
  _gridWidgetInfoMap = new TreeMap<>();
}
//--------------------------------------------------------------------------------------------------
public static PetGridWidget getPetGrid(final String gridName) {
  final GridWidgetInfo gridWidgetInfo = _gridWidgetInfoMap.get(gridName);
  if (gridWidgetInfo == null) {
    GLLog.popup(60, "Attempted to get grid with no settings");
    return null;
  }
  return getPetGrid(gridName, gridWidgetInfo._inlineEditing,
                    gridWidgetInfo._useCheckBoxSelectionModel);
}
//--------------------------------------------------------------------------------------------------
public static PetGridWidget getPetGrid(final String gridName, final boolean inlineEditing,
                                       final boolean useCheckBoxSelectionModel) {
  PetGridWidget result;
  GridWidgetInfo gridWidgetInfo = _gridWidgetInfoMap.get(gridName);
  if (gridWidgetInfo == null || gridWidgetInfo._inlineEditing != inlineEditing ||
      gridWidgetInfo._useCheckBoxSelectionModel != useCheckBoxSelectionModel) {
    result = new PetGridWidget(inlineEditing, useCheckBoxSelectionModel, //
                               Pet.PetName, Pet.PetTypeId, Pet.Sex, Pet.IntakeDate, //
                               Pet.TrainedFlag, Pet.AdoptionFee, Pet.FosterDate, //
                               Pet.NumberOfFosters);
    gridWidgetInfo = new GridWidgetInfo(result, inlineEditing, useCheckBoxSelectionModel);
    _gridWidgetInfoMap.put(gridName, gridWidgetInfo);
  }
  return (PetGridWidget)gridWidgetInfo._gridWidget;
}
//--------------------------------------------------------------------------------------------------
}