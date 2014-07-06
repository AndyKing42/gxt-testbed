package org.greatlogic.glgwt.client.widget;

import org.greatlogic.glgwt.client.core.GLRecord;
import org.greatlogic.glgwt.shared.IGLColumn;
import org.greatlogic.glgwt.shared.IGLEnums.EGLColumnDataType;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;

public class GLColumnConfig<DataType> extends ColumnConfig<GLRecord, DataType> {
//--------------------------------------------------------------------------------------------------
private final IGLColumn _column;
private int             _columnIndex;
private DateTimeFormat  _dateTimeFormat;
//--------------------------------------------------------------------------------------------------
public GLColumnConfig(final IGLColumn column,
                      final ValueProvider<? super GLRecord, DataType> valueProvider,
                      final String header, final int width) {
  super(valueProvider, width, header);
  _column = column;
  setWidth(width < 0 ? column.getDefaultGridColumnWidth() : width);
  final EGLColumnDataType dataType = _column.getDataType();
  if (dataType == EGLColumnDataType.Boolean) {
    setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
  }
  else if (dataType.getNumeric() && _column.getParentTable() == null) {
    setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
  }
  else {
    setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
  }
}
//--------------------------------------------------------------------------------------------------
public IGLColumn getColumn() {
  return _column;
}
//--------------------------------------------------------------------------------------------------
public int getColumnIndex() {
  return _columnIndex;
}
//--------------------------------------------------------------------------------------------------
public DateTimeFormat getDateTimeFormat() {
  return _dateTimeFormat;
}
//--------------------------------------------------------------------------------------------------
public void setColumnIndex(final int columnIndex) {
  _columnIndex = columnIndex;
}
//--------------------------------------------------------------------------------------------------
public void setDateTimeFormat(final DateTimeFormat dateTimeFormat) {
  _dateTimeFormat = dateTimeFormat;
}
//--------------------------------------------------------------------------------------------------
@Override
public String toString() {
  return _column.toString() + " (" + getHeader() + ")";
}
//--------------------------------------------------------------------------------------------------
}