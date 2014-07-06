package org.greatlogic.gxttestbed.shared;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import org.greatlogic.glgwt.client.core.GLRecord;
import org.greatlogic.glgwt.client.core.GLUtil;
import org.greatlogic.glgwt.shared.IGLColumn;
import org.greatlogic.glgwt.shared.IGLEnums.EGLColumnDataType;
import org.greatlogic.glgwt.shared.IGLTable;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorError;
import com.sencha.gxt.widget.core.client.form.Validator;

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
NextId(EGLColumnDataType.Int, null, 0, false, "Id", 50),
NextIdName(EGLColumnDataType.String, null, 50, false, "Name", 100),
NextIdTableName(EGLColumnDataType.String, null, 50, true, "Table", 100),
NextIdValue(EGLColumnDataType.Int, 100, 0, false, "Next Value", 10);
private final EGLColumnDataType _dataType;
private final int               _defaultGridColumnWidth;
private final Object            _defaultValue;
private final boolean           _nullable;
private final int               _numberOfDecimalPlaces;
private final String            _title;
private NextId(final EGLColumnDataType dataType, final Object defaultValue,
               final int numberOfDecimalPlaces, final boolean nullable, final String title,
               final int defaultGridColumnWidth) {
  _dataType = dataType;
  _defaultValue = defaultValue;
  _numberOfDecimalPlaces = numberOfDecimalPlaces;
  _nullable = nullable;
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
public boolean getNullable() {
  return _nullable;
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
@Override
public Validator<?> getValidator() {
  return null;
}
}
//--------------------------------------------------------------------------------------------------
public enum Pet implements IGLColumn {
AdoptionFee(EGLColumnDataType.Currency, 0, 2, false, "Adoption Fee", 100),
FosterDate(EGLColumnDataType.Date, null, 0, true, "Foster Date", 100) {
@Override
public Validator<?> getValidator() {
  return new Validator<Date>() {
    @SuppressWarnings("deprecation")
    @Override
    public List<EditorError> validate(final Editor<Date> editor, final Date date) {
      if (date == null) {
        return null;
      }
      if (date.getMonth() == 1) {
        return GLUtil.createValidatorResult(editor, "Nothing in February");
      }
      if (date.getDate() == 15) {
        return GLUtil.createValidatorResult(editor, "No ides!");
      }
      return null;
    }
  };
}
},
IntakeDate(EGLColumnDataType.DateTime, null, 0, true, "Intake Date/Time", 125),
NumberOfFosters(EGLColumnDataType.Int, 0, 0, false, "Number Of Fosters", 60),
PetId(EGLColumnDataType.Int, null, 0, false, "Id", 50),
PetName(EGLColumnDataType.String, null, 0, false, "Pet Name", 80),
PetTypeId(EGLColumnDataType.Int, null, 0, false, "Pet Type", 80) {
@Override
public IGLTable getParentTable() {
  return EGXTTestbedTable.PetType;
}
},
Sex(EGLColumnDataType.String, "U", 0, false, "Sex", 50) {
private ArrayList<String> _choiceList;
@Override
public ArrayList<String> getChoiceList() {
  if (_choiceList == null) {
    _choiceList = GLUtil.loadListFromStrings("F,M,U", true);
  }
  return _choiceList;
}
},
TrainedFlag(EGLColumnDataType.Boolean, "N", 0, false, "Trained?", 80);
private final EGLColumnDataType _dataType;
private final int               _defaultGridColumnWidth;
private final Object            _defaultValue;
private final boolean           _nullable;
private final int               _numberOfDecimalPlaces;
private final String            _title;
private Pet(final EGLColumnDataType dataType, final Object defaultValue,
            final int numberOfDecimalPlaces, final boolean nullable, final String title,
            final int defaultGridColumnWidth) {
  _dataType = dataType;
  _defaultValue = defaultValue;
  _numberOfDecimalPlaces = numberOfDecimalPlaces;
  _nullable = nullable;
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
public boolean getNullable() {
  return _nullable;
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
@Override
public Validator<?> getValidator() {
  return null;
}
}
//--------------------------------------------------------------------------------------------------
public enum PetType implements IGLColumn {
PetTypeDesc(EGLColumnDataType.String, null, 0, false, "Pet Type Desc", 100),
PetTypeId(EGLColumnDataType.Int, null, 0, false, "Id", 50),
PetTypeShortDesc(EGLColumnDataType.String, null, 0, false, "Pet Type Short Desc", 10);
private final EGLColumnDataType _dataType;
private final int               _defaultGridColumnWidth;
private final Object            _defaultValue;
private final boolean           _nullable;
private final int               _numberOfDecimalPlaces;
private final String            _title;
private PetType(final EGLColumnDataType dataType, final Object defaultValue,
                final int numberOfDecimalPlaces, final boolean nullable, final String title,
                final int defaultGridColumnWidth) {
  _dataType = dataType;
  _defaultValue = defaultValue;
  _numberOfDecimalPlaces = numberOfDecimalPlaces;
  _nullable = nullable;
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
public boolean getNullable() {
  return _nullable;
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
@Override
public Validator<?> getValidator() {
  return null;
}
}
//--------------------------------------------------------------------------------------------------
}