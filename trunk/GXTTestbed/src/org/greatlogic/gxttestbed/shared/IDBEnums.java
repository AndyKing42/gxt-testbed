package org.greatlogic.gxttestbed.shared;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;
import org.greatlogic.glgwt.client.core.GLRecord;
import org.greatlogic.glgwt.client.core.GLUtil;
import org.greatlogic.glgwt.shared.IGLColumn;
import org.greatlogic.glgwt.shared.IGLEnums.EGLColumnDataType;
import org.greatlogic.glgwt.shared.IGLTable;

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
NextId(IDBEnums.NextId.class, IDBEnums.NextId.NextId, IDBEnums.NextId.NextIdName),
Pet(IDBEnums.Pet.class, IDBEnums.Pet.PetId, IDBEnums.Pet.PetName),
PetType(IDBEnums.PetType.class, IDBEnums.PetType.PetTypeId, IDBEnums.PetType.PetTypeShortDesc);
private final IGLColumn                _comboboxDisplayColumn;
private TreeMap<String, IGLColumn>     _columnByColumnNameMap;
private final Class<? extends Enum<?>> _columnClass;
private final IGLColumn                _primaryKeyColumn;
private EGXTTestbedTable(final Class<? extends Enum<?>> columnClass,
                         final IGLColumn primaryKeyColumn, final IGLColumn comboboxDisplayColumn) {
  _columnClass = columnClass;
  _primaryKeyColumn = primaryKeyColumn;
  _comboboxDisplayColumn = comboboxDisplayColumn;
}
private void createColumnByColumnNameMap() {
  if (_columnByColumnNameMap == null) {
    _columnByColumnNameMap = new TreeMap<>();
    for (final Enum<?> column : _columnClass.getEnumConstants()) {
      _columnByColumnNameMap.put(column.name(), (IGLColumn)column);
    }
  }
}
@Override
public IGLColumn findColumnUsingColumnName(final String columnName) {
  createColumnByColumnNameMap();
  return _columnByColumnNameMap.get(columnName);
}
@Override
public Collection<IGLColumn> getColumns() {
  createColumnByColumnNameMap();
  return _columnByColumnNameMap.values();
}
@Override
public IGLColumn getComboboxDisplayColumn() {
  return _comboboxDisplayColumn;
}
@Override
public IGLColumn getPrimaryKeyColumn() {
  return _primaryKeyColumn;
}
@Override
public void initializeNewRecord(final GLRecord record) {
  createColumnByColumnNameMap();
  for (final IGLColumn column : _columnByColumnNameMap.values()) {
    final Object defaultValue = column.getDefaultValue();
    if (defaultValue != null) {
      record.set(column, defaultValue);
    }
  }
}
}
//--------------------------------------------------------------------------------------------------
public enum NextId implements IGLColumn {
NextId(EGLColumnDataType.Int, null, 0, "Id", 50),
NextIdName(EGLColumnDataType.String, null, 50, "Name", 100),
NextIdTableName(EGLColumnDataType.String, null, 50, "Table", 100),
NextIdValue(EGLColumnDataType.Int, 100, 0, "Next Value", 10);
private final EGLColumnDataType _dataType;
private final int               _defaultGridColumnWidth;
private final Object            _defaultValue;
private final int               _numberOfDecimalPlaces;
private final String            _title;
private NextId(final EGLColumnDataType dataType, final Object defaultValue,
               final int numberOfDecimalPlaces, final String title, final int defaultGridColumnWidth) {
  _dataType = dataType;
  _defaultValue = defaultValue;
  _numberOfDecimalPlaces = numberOfDecimalPlaces;
  _title = title;
  _defaultGridColumnWidth = defaultGridColumnWidth;
}
@Override
public ArrayList<String> getChoiceList() {
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
public Object getDefaultValue() {
  return _defaultValue;
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
AdoptionFee(EGLColumnDataType.Currency, 0, 2, "Adoption Fee", 100),
FosterDate(EGLColumnDataType.Date, null, 0, "Foster Date", 100),
IntakeDate(EGLColumnDataType.DateTime, null, 0, "Intake Date/Time", 125),
NumberOfFosters(EGLColumnDataType.Int, 0, 0, "Number Of Fosters", 20),
PetId(EGLColumnDataType.Int, null, 0, "Id", 50),
PetName(EGLColumnDataType.String, null, 0, "Pet Name", 80),
PetTypeId(EGLColumnDataType.Int, null, 0, "Pet Type", 80) {
  @Override
  public IGLTable getParentTable() {
    return EGXTTestbedTable.PetType;
  }
},
Sex(EGLColumnDataType.String, "U", 0, "Sex", 50) {
  private ArrayList<String> _choiceList;
  @Override
  public ArrayList<String> getChoiceList() {
    if (_choiceList == null) {
      _choiceList = GLUtil.loadListFromStrings("F,M,U", true);
    }
    return _choiceList;
  }
},
TrainedFlag(EGLColumnDataType.Boolean, "N", 0, "Trained?", 80);
private final EGLColumnDataType _dataType;
private final int               _defaultGridColumnWidth;
private final Object            _defaultValue;
private final int               _numberOfDecimalPlaces;
private final String            _title;
private Pet(final EGLColumnDataType dataType, final Object defaultValue,
            final int numberOfDecimalPlaces, final String title, final int defaultGridColumnWidth) {
  _dataType = dataType;
  _defaultValue = defaultValue;
  _numberOfDecimalPlaces = numberOfDecimalPlaces;
  _title = title;
  _defaultGridColumnWidth = defaultGridColumnWidth;
}
@Override
public ArrayList<String> getChoiceList() {
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
public Object getDefaultValue() {
  return _defaultValue;
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
PetTypeDesc(EGLColumnDataType.String, null, 0, "Pet Type Desc", 100),
PetTypeId(EGLColumnDataType.Int, null, 0, "Id", 50),
PetTypeShortDesc(EGLColumnDataType.String, null, 0, "Pet Type Short Desc", 10);
private final EGLColumnDataType _dataType;
private final int               _defaultGridColumnWidth;
private final Object            _defaultValue;
private final int               _numberOfDecimalPlaces;
private final String            _title;
private PetType(final EGLColumnDataType dataType, final Object defaultValue,
                final int numberOfDecimalPlaces, final String title,
                final int defaultGridColumnWidth) {
  _dataType = dataType;
  _defaultValue = defaultValue;
  _numberOfDecimalPlaces = numberOfDecimalPlaces;
  _title = title;
  _defaultGridColumnWidth = defaultGridColumnWidth;
}
@Override
public ArrayList<String> getChoiceList() {
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
public Object getDefaultValue() {
  return _defaultValue;
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