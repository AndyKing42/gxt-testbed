package org.greatlogic.glgwt.client.widget;

import java.util.TreeMap;
import org.greatlogic.glgwt.client.core.GLRecord;
import org.greatlogic.glgwt.client.core.GLUtil;
import org.greatlogic.glgwt.shared.IGLColumn;
import com.sencha.gxt.widget.core.client.form.IsField;
import com.sencha.gxt.widget.core.client.grid.editing.GridEditing;

public class GLValidationRecord {
//--------------------------------------------------------------------------------------------------
private final TreeMap<String, GLGridColumnDef> _gridColumnDefMap;
private final GridEditing<GLRecord>            _gridEditing;
//--------------------------------------------------------------------------------------------------
GLValidationRecord(final TreeMap<String, GLGridColumnDef> gridColumnDefMap,
                   final GridEditing<GLRecord> gridEditing) {
  _gridColumnDefMap = gridColumnDefMap;
  _gridEditing = gridEditing;
}
//--------------------------------------------------------------------------------------------------
public int asInt(final IGLColumn column) {
  return GLUtil.stringToInt(asString(column));
}
//--------------------------------------------------------------------------------------------------
private Object asObject(final IGLColumn column) {
  final GLGridColumnDef columnDef = _gridColumnDefMap.get(column.toString());
  if (columnDef == null) {
    return null;
  }
  final IsField<Object> editor = _gridEditing.getEditor(columnDef.getColumnConfig());
  if (editor == null) {
    return null;
  }
  return editor.getValue();
}
//--------------------------------------------------------------------------------------------------
public String asString(final IGLColumn column) {
  final Object value = asObject(column);
  return value == null ? "" : value.toString();
}
//--------------------------------------------------------------------------------------------------
}