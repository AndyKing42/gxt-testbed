package org.greatlogic.gxttestbed.client.glgwt;

import org.greatlogic.gxttestbed.shared.IGLColumn;
import org.greatlogic.gxttestbed.shared.IGLEnums.EGLColumnDataType;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;

public class GLGridColumnDef {
//--------------------------------------------------------------------------------------------------
private final IGLColumn                   _column;
private ColumnConfig<GLRecord, ?>         _columnConfig;
private int                               _columnIndex;
private final String                      _header;
private final HorizontalAlignmentConstant _horizontalAlignment;
private final IGLLookupListStoreKey       _lookupListStoreKey;
private final int                         _width;
//--------------------------------------------------------------------------------------------------
public GLGridColumnDef(final IGLColumn column) {
  this(column, null, -1, null);
}
//--------------------------------------------------------------------------------------------------
public GLGridColumnDef(final IGLColumn column, final IGLLookupListStoreKey lookupListStoreKey) {
  this(column, null, -1, lookupListStoreKey);
}
//--------------------------------------------------------------------------------------------------
public GLGridColumnDef(final IGLColumn column, final String header, final int width) {
  this(column, header, width, null);
}
//--------------------------------------------------------------------------------------------------
public GLGridColumnDef(final IGLColumn column, final String header, final int width,
                       final IGLLookupListStoreKey lookupListStoreKey) {
  _column = column;
  _header = header == null ? _column.getTitle() : header;
  _width = width < 0 ? column.getDefaultGridColumnWidth() : width;
  _lookupListStoreKey = lookupListStoreKey;
  final EGLColumnDataType dataType = _column.getDataType();
  if (dataType == EGLColumnDataType.Boolean) {
    _horizontalAlignment = HasHorizontalAlignment.ALIGN_CENTER;
  }
  else if (dataType.getNumeric() && _column.getParentTable() == null) {
    _horizontalAlignment = HasHorizontalAlignment.ALIGN_RIGHT;
  }
  else {
    _horizontalAlignment = HasHorizontalAlignment.ALIGN_LEFT;
  }
}
//--------------------------------------------------------------------------------------------------
public IGLColumn getColumn() {
  return _column;
}
//--------------------------------------------------------------------------------------------------
public ColumnConfig<GLRecord, ?> getColumnConfig() {
  return _columnConfig;
}
//--------------------------------------------------------------------------------------------------
public int getColumnIndex() {
  return _columnIndex;
}
//--------------------------------------------------------------------------------------------------
public String getHeader() {
  return _header;
}
//--------------------------------------------------------------------------------------------------
public HorizontalAlignmentConstant getHorizontalAlignment() {
  return _horizontalAlignment;
}
//--------------------------------------------------------------------------------------------------
public IGLLookupListStoreKey getLookupListStoreKey() {
  return _lookupListStoreKey;
}
//--------------------------------------------------------------------------------------------------
public int getWidth() {
  return _width;
}
//--------------------------------------------------------------------------------------------------
public void setColumnConfig(final ColumnConfig<GLRecord, ?> columnConfig, final int columnIndex) {
  _columnConfig = columnConfig;
  _columnIndex = columnIndex;
}
//--------------------------------------------------------------------------------------------------
@Override
public String toString() {
  return _column.toString() + " (" + _header + ")";
}
//--------------------------------------------------------------------------------------------------
}