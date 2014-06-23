package org.greatlogic.gxttestbed.shared;

import org.greatlogic.gxttestbed.shared.glgwt.IGLColumn;
import org.greatlogic.gxttestbed.shared.glgwt.IGLEnums.EGLColumnDataType;
import org.greatlogic.gxttestbed.shared.glgwt.IGLTable;

public interface IDBEnums {
//--------------------------------------------------------------------------------------------------
public enum EGXTTestbedId {
PetId(1, "PetId", EGXTTestbedTable.Pet),
PetTypeId(2, "PetTypeId", EGXTTestbedTable.PetType);
private final String           _name;
private final int              _nextId;
private final EGXTTestbedTable _table;
private EGXTTestbedId(final int nextId, final String name, final EGXTTestbedTable table) {
  _nextId = nextId;
  _name = name;
  _table = table;
}
public String getName() {
  return _name;
}
public int getNextId() {
  return _nextId;
}
public EGXTTestbedTable getTable() {
  return _table;
}
}
//--------------------------------------------------------------------------------------------------
public enum EGXTTestbedTable implements IGLTable {
NextId(IDBEnums.NextId.NextId, IDBEnums.NextId.NextIdName),
Pet(IDBEnums.Pet.PetId, IDBEnums.Pet.PetName),
PetType(IDBEnums.PetType.PetTypeId, IDBEnums.PetType.PetTypeShortDesc);
private final IGLColumn _comboboxDisplayColumn;
private final IGLColumn _primaryKeyColumn;
private EGXTTestbedTable(final IGLColumn primaryKeyColumn, final IGLColumn comboboxDisplayColumn) {
  _primaryKeyColumn = primaryKeyColumn;
  _comboboxDisplayColumn = comboboxDisplayColumn;
}
@Override
public IGLColumn getComboboxDisplayColumn() {
  return _comboboxDisplayColumn;
}
@Override
public IGLColumn getPrimaryKeyColumn() {
  return _primaryKeyColumn;
}
}
//--------------------------------------------------------------------------------------------------
public enum NextId implements IGLColumn {
NextId(EGLColumnDataType.Int, 0, "Id", 50),
NextIdName(EGLColumnDataType.String, 50, "Name", 100),
NextIdTableName(EGLColumnDataType.String, 50, "Table", 100),
NextIdValue(EGLColumnDataType.Int, 0, "Next Value", 10);
private final EGLColumnDataType _dataType;
private final int               _defaultGridColumnWidth;
private final int               _numberOfDecimalPlaces;
private final String            _title;
private NextId(final EGLColumnDataType dataType, final int numberOfDecimalPlaces,
               final String title, final int defaultGridColumnWidth) {
  _dataType = dataType;
  _numberOfDecimalPlaces = numberOfDecimalPlaces;
  _title = title;
  _defaultGridColumnWidth = defaultGridColumnWidth;
}
@Override
public String[] getChoices() {
  return null;
}
@Override
public EGLColumnDataType getDataType() {
  return _dataType;
}
@Override
public int getDefaultGridColumnWidth() {
  return _defaultGridColumnWidth;
}
@Override
public int getNumberOfDecimalPlaces() {
  return _numberOfDecimalPlaces;
}
@Override
public IGLTable getParentTable() {
  return null;
}
@Override
public String getTitle() {
  return _title;
}
}
//--------------------------------------------------------------------------------------------------
public enum Pet implements IGLColumn {
AdoptionFee(EGLColumnDataType.Currency, 2, "Adoption Fee", 100),
FosterDate(EGLColumnDataType.Date, 0, "Foster Date", 100),
IntakeDate(EGLColumnDataType.DateTime, 0, "Intake Date/Time", 125),
NumberOfFosters(EGLColumnDataType.Int, 0, "Number Of Fosters", 20),
PetId(EGLColumnDataType.Int, 0, "Id", 50),
PetName(EGLColumnDataType.String, 0, "Pet Name", 80),
PetTypeId(EGLColumnDataType.Int, 0, "Pet Type", 80) {
@Override
public IGLTable getParentTable() {
  return EGXTTestbedTable.PetType;
}
},
Sex(EGLColumnDataType.String, 0, "Sex", 50) {
private String[] _choices;
@Override
public String[] getChoices() {
  if (_choices == null) {
    _choices = new String[] {"F", "M", "U"};
  }
  return _choices;
}
},
TrainedFlag(EGLColumnDataType.Boolean, 0, "Trained?", 80);
private final EGLColumnDataType _dataType;
private final int               _defaultGridColumnWidth;
private final int               _numberOfDecimalPlaces;
private final String            _title;
private Pet(final EGLColumnDataType dataType, final int numberOfDecimalPlaces, final String title,
            final int defaultGridColumnWidth) {
  _dataType = dataType;
  _numberOfDecimalPlaces = numberOfDecimalPlaces;
  _title = title;
  _defaultGridColumnWidth = defaultGridColumnWidth;
}
@Override
public String[] getChoices() {
  return null;
}
@Override
public EGLColumnDataType getDataType() {
  return _dataType;
}
@Override
public int getDefaultGridColumnWidth() {
  return _defaultGridColumnWidth;
}
@Override
public int getNumberOfDecimalPlaces() {
  return _numberOfDecimalPlaces;
}
@Override
public IGLTable getParentTable() {
  return null;
}
@Override
public String getTitle() {
  return _title;
}
}
//--------------------------------------------------------------------------------------------------
public enum PetType implements IGLColumn {
PetTypeDesc(EGLColumnDataType.String, 0, "Pet Type Desc", 100),
PetTypeId(EGLColumnDataType.Int, 0, "Id", 50),
PetTypeShortDesc(EGLColumnDataType.String, 0, "Pet Type Short Desc", 10);
private final EGLColumnDataType _dataType;
private final int               _defaultGridColumnWidth;
private final int               _numberOfDecimalPlaces;
private final String            _title;
private PetType(final EGLColumnDataType dataType, final int numberOfDecimalPlaces,
                final String title, final int defaultGridColumnWidth) {
  _dataType = dataType;
  _numberOfDecimalPlaces = numberOfDecimalPlaces;
  _title = title;
  _defaultGridColumnWidth = defaultGridColumnWidth;
}
@Override
public String[] getChoices() {
  return null;
}
@Override
public EGLColumnDataType getDataType() {
  return _dataType;
}
@Override
public int getDefaultGridColumnWidth() {
  return _defaultGridColumnWidth;
}
@Override
public int getNumberOfDecimalPlaces() {
  return _numberOfDecimalPlaces;
}
@Override
public IGLTable getParentTable() {
  return null;
}
@Override
public String getTitle() {
  return _title;
}
}
//--------------------------------------------------------------------------------------------------
}