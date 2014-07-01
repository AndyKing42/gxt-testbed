package org.greatlogic.glgwt.client.widget;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;
import org.greatlogic.glgwt.client.core.GLListStore;
import org.greatlogic.glgwt.client.core.GLRecord;
import org.greatlogic.glgwt.client.core.GLUtil;
import org.greatlogic.glgwt.client.core.IGLCreateNewRecordCallback;
import org.greatlogic.glgwt.client.event.GLLookupTableLoadedEvent;
import org.greatlogic.glgwt.client.event.GLLookupTableLoadedEvent.IGLLookupTableLoadedEventHandler;
import org.greatlogic.glgwt.client.widget.GLValueProviderClasses.GLBigDecimalValueProvider;
import org.greatlogic.glgwt.client.widget.GLValueProviderClasses.GLBooleanValueProvider;
import org.greatlogic.glgwt.client.widget.GLValueProviderClasses.GLDateValueProvider;
import org.greatlogic.glgwt.client.widget.GLValueProviderClasses.GLForeignKeyValueProvider;
import org.greatlogic.glgwt.client.widget.GLValueProviderClasses.GLIntegerValueProvider;
import org.greatlogic.glgwt.client.widget.GLValueProviderClasses.GLStringValueProvider;
import org.greatlogic.glgwt.shared.IGLColumn;
import org.greatlogic.glgwt.shared.IGLEnums.EGLColumnDataType;
import org.greatlogic.glgwt.shared.IGLTable;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safecss.shared.SafeStyles;
import com.google.gwt.safecss.shared.SafeStylesUtils;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.Event.Type;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.sencha.gxt.cell.core.client.ResizableCell;
import com.sencha.gxt.cell.core.client.form.CheckBoxCell;
import com.sencha.gxt.cell.core.client.form.DateCell;
import com.sencha.gxt.cell.core.client.form.NumberInputCell;
import com.sencha.gxt.cell.core.client.form.TextInputCell;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.util.TextMetrics;
import com.sencha.gxt.data.shared.Store;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.box.ProgressMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.event.ColumnWidthChangeEvent;
import com.sencha.gxt.widget.core.client.event.ColumnWidthChangeEvent.ColumnWidthChangeHandler;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.event.HeaderContextMenuEvent;
import com.sencha.gxt.widget.core.client.event.HeaderContextMenuEvent.HeaderContextMenuHandler;
import com.sencha.gxt.widget.core.client.event.RefreshEvent;
import com.sencha.gxt.widget.core.client.event.RowClickEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.BigDecimalField;
import com.sencha.gxt.widget.core.client.form.DateTimePropertyEditor;
import com.sencha.gxt.widget.core.client.form.IntegerField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor.BigDecimalPropertyEditor;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor.IntegerPropertyEditor;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.Grid.GridCell;
import com.sencha.gxt.widget.core.client.grid.GridSelectionModel;
import com.sencha.gxt.widget.core.client.grid.GridView;
import com.sencha.gxt.widget.core.client.grid.editing.GridInlineEditing;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

public abstract class GLGridWidget_3 implements IsWidget {
//--------------------------------------------------------------------------------------------------
private final HashSet<ColumnConfig<GLRecord, ?>> _checkBoxSet;
private ContentPanel                             _contentPanel;
private Grid<GLRecord>                           _grid;
protected ArrayList<GLGridColumnDef>             _gridColumnDefList;
private TreeMap<String, GLGridColumnDef>         _gridColumnDefMap;
private GridInlineEditing<GLRecord>              _gridInlineEditing;
protected GLListStore                            _listStore;
private HandlerRegistration                      _lookupTableLoadedHandlerRegistration;
private final String                             _noRowsMessage;
private GridSelectionModel<GLRecord>             _selectionModel;
//--------------------------------------------------------------------------------------------------
protected GLGridWidget_3(final String headingText, final String noRowsMessage) {
  super();
  _noRowsMessage = noRowsMessage == null ? "There are no results to display" : noRowsMessage;
  _listStore = new GLListStore();
  _checkBoxSet = new HashSet<ColumnConfig<GLRecord, ?>>();
  _gridColumnDefList = new ArrayList<GLGridColumnDef>();
  loadGridColumnDefList();
  createGridColumnDefMap();
  createContentPanel(headingText);
  waitForComboBoxData();
}
//--------------------------------------------------------------------------------------------------
private void addHeaderContextMenuHandler() {
  final HeaderContextMenuHandler headerContextMenuHandler = new HeaderContextMenuHandler() {
    @Override
    public void onHeaderContextMenu(final HeaderContextMenuEvent headerContextMenuEvent) {
      MenuItem menuItem = new MenuItem("Size To Fit");
      menuItem.addSelectionHandler(new SelectionHandler<Item>() {
        @Override
        public void onSelection(final SelectionEvent<Item> selectionEvent) {
          resizeColumnToFit(headerContextMenuEvent.getColumnIndex());
          _grid.getView().refresh(true);
        }
      });
      headerContextMenuEvent.getMenu().add(menuItem);
      menuItem = new MenuItem("Size All To Fit");
      menuItem.addSelectionHandler(new SelectionHandler<Item>() {
        @Override
        public void onSelection(final SelectionEvent<Item> selectionEvent) {
          final ProgressMessageBox messageBox = new ProgressMessageBox("Size All Columns", //
                                                                       "Resizing Columns...");
          messageBox.setProgressText("Calculating...");
          messageBox.show();
          resizeNextColumn(messageBox, _selectionModel instanceof CheckBoxSelectionModel ? 1 : 0,
                           _grid.getColumnModel().getColumnCount() - 1);
        }
      });
      headerContextMenuEvent.getMenu().add(menuItem);
    }
  };
  _grid.addHeaderContextMenuHandler(headerContextMenuHandler);
}
//--------------------------------------------------------------------------------------------------
/**
 * If there are lookup tables that are needed by any of the columns in the grid then the creation of
 * the grid must be deferred until all of those lookup tables have been loaded into the cache. The
 * lookup tables are added to the loadTableSet; when the LookupTableLoadedEvent is fired the table
 * that's been loaded is removed from the set; when all tables have been loaded the grid is created.
 * @param loadTableSet The set that contains the list of lookup tables that need to be loaded prior
 * to creating the grid.
 */
private void addLookupTableLoadedEventHandler(final HashSet<IGLTable> loadTableSet) {
  if (_lookupTableLoadedHandlerRegistration == null) {
    final IGLLookupTableLoadedEventHandler handler = new IGLLookupTableLoadedEventHandler() {
      @Override
      public void onLookupTableLoadedEvent(final GLLookupTableLoadedEvent lookupTableLoadedEvent) {
        loadTableSet.remove(lookupTableLoadedEvent.getTable());
        if (loadTableSet.size() == 0) {
          _lookupTableLoadedHandlerRegistration.removeHandler();
          _lookupTableLoadedHandlerRegistration = null;
          createGrid();
        }
      }
    };
    final Type<IGLLookupTableLoadedEventHandler> eventType;
    eventType = GLLookupTableLoadedEvent.LookTableLoadedEventType;
    _lookupTableLoadedHandlerRegistration = GLUtil.getEventBus().addHandler(eventType, handler);
  }
}
//--------------------------------------------------------------------------------------------------
private void applyColumnWidthToCell(final ColumnConfig<GLRecord, ?> columnConfig) {
  ((ResizableCell)(columnConfig.getCell())).setWidth(columnConfig.getWidth() - 2);
}
//--------------------------------------------------------------------------------------------------
@Override
public Widget asWidget() {
  return _contentPanel;
}
//--------------------------------------------------------------------------------------------------
private void centerCheckBox(final ColumnConfig<GLRecord, ?> columnConfig) {
  final int leftPadding = (columnConfig.getWidth() - 12) / 2;
  final String styles = "padding: 3px 0px 0px " + leftPadding + "px;";
  final SafeStyles textStyles = SafeStylesUtils.fromTrustedString(styles);
  columnConfig.setColumnTextStyle(textStyles);
}
//--------------------------------------------------------------------------------------------------
private void createCheckBoxSelectionModel() {
  final IdentityValueProvider<GLRecord> identityValueProvider;
  identityValueProvider = new IdentityValueProvider<GLRecord>();
  _selectionModel = new CheckBoxSelectionModel<GLRecord>(identityValueProvider) {
    @Override
    protected void onRefresh(final RefreshEvent event) {
      if (isSelectAllChecked()) {
        selectAll();
      }
      super.onRefresh(event);
    }
  };
}
//--------------------------------------------------------------------------------------------------
private ColumnConfig<GLRecord, BigDecimal> createColumnConfigBigDecimal(final GLGridColumnDef gridColumnDef,
                                                                        final IGLColumn column) {
  final ColumnConfig<GLRecord, BigDecimal> result;
  final ValueProvider<GLRecord, BigDecimal> valueProvider;
  valueProvider = new GLBigDecimalValueProvider(column, column.getNumberOfDecimalPlaces());
  result = new ColumnConfig<GLRecord, BigDecimal>(valueProvider, gridColumnDef.getWidth(), //
                                                  gridColumnDef.getHeader());
  result.setHorizontalAlignment(gridColumnDef.getHorizontalAlignment());
  NumberFormat numberFormat;
  if (column.getDataType() == EGLColumnDataType.Currency) {
    numberFormat = NumberFormat.getSimpleCurrencyFormat();
    numberFormat = NumberFormat.getFormat("#0.00");
  }
  else {
    numberFormat = NumberFormat.getDecimalFormat();
  }
  final BigDecimalPropertyEditor propertyEditor;
  propertyEditor = new NumberPropertyEditor.BigDecimalPropertyEditor(numberFormat);
  final NumberInputCell<BigDecimal> cell = new NumberInputCell<BigDecimal>(propertyEditor);
  final BigDecimalField field = new BigDecimalField(cell);
  field.setFormat(numberFormat);
  result.setCell(cell);
  return result;
}
//--------------------------------------------------------------------------------------------------
private ColumnConfig<GLRecord, Boolean> createColumnConfigBoolean(final GLGridColumnDef gridColumnDef,
                                                                  final IGLColumn column) {
  final ColumnConfig<GLRecord, Boolean> result;
  result = new ColumnConfig<GLRecord, Boolean>(new GLBooleanValueProvider(column), //
                                               gridColumnDef.getWidth(), gridColumnDef.getHeader());
  result.setHorizontalAlignment(gridColumnDef.getHorizontalAlignment()); // for the header
  result.setCell(new CheckBoxCell());
  result.setSortable(false);
  _checkBoxSet.add(result);
  return result;
}
//--------------------------------------------------------------------------------------------------
private ColumnConfig<GLRecord, Date> createColumnConfigDateTime(final GLGridColumnDef gridColumnDef,
                                                                final IGLColumn column,
                                                                final String dateTimeFormat) {
  final ColumnConfig<GLRecord, Date> result;
  result = new ColumnConfig<GLRecord, Date>(new GLDateValueProvider(column), //
                                            gridColumnDef.getWidth(), gridColumnDef.getHeader());
  result.setHorizontalAlignment(gridColumnDef.getHorizontalAlignment());
  final DateCell cell = new DateCell();
  cell.setPropertyEditor(new DateTimePropertyEditor(dateTimeFormat));
  result.setCell(cell);
  return result;
}
//--------------------------------------------------------------------------------------------------
private ColumnConfig<GLRecord, String> createColumnConfigForeignKey(final GLGridColumnDef gridColumnDef,
                                                                    final IGLTable lookupTable,
                                                                    final IGLColumn column) {
  final ColumnConfig<GLRecord, String> result;
  final ValueProvider<GLRecord, String> valueProvider = new GLForeignKeyValueProvider(lookupTable, //
                                                                                      column);
  result = new ColumnConfig<GLRecord, String>(valueProvider, gridColumnDef.getWidth(), //
                                              gridColumnDef.getHeader());
  result.setHorizontalAlignment(gridColumnDef.getHorizontalAlignment());
  result.setCell(new TextInputCell());
  return result;
}
//--------------------------------------------------------------------------------------------------
private ColumnConfig<GLRecord, Integer> createColumnConfigInteger(final GLGridColumnDef gridColumnDef,
                                                                  final IGLColumn column) {
  final ColumnConfig<GLRecord, Integer> result;
  result = new ColumnConfig<GLRecord, Integer>(new GLIntegerValueProvider(column), //
                                               gridColumnDef.getWidth(), gridColumnDef.getHeader());
  result.setHorizontalAlignment(gridColumnDef.getHorizontalAlignment());
  final IntegerPropertyEditor propertyEditor = new NumberPropertyEditor.IntegerPropertyEditor();
  final NumberInputCell<Integer> cell = new NumberInputCell<>(propertyEditor);
  new IntegerField(cell);
  result.setCell(cell);
  return result;
}
//--------------------------------------------------------------------------------------------------
private ColumnConfig<GLRecord, String> createColumnConfigString(final GLGridColumnDef gridColumnDef,
                                                                final IGLColumn column) {
  final ColumnConfig<GLRecord, String> result;
  result = new ColumnConfig<GLRecord, String>(new GLStringValueProvider(column), //
                                              gridColumnDef.getWidth(), gridColumnDef.getHeader());
  result.setHorizontalAlignment(gridColumnDef.getHorizontalAlignment());
  result.setCell(new TextInputCell());
  _gridInlineEditing.addEditor(result, new TextField());
  return result;
}
//--------------------------------------------------------------------------------------------------
private ColumnModel<GLRecord> createColumnModel() {
  ColumnModel<GLRecord> result;
  final ArrayList<ColumnConfig<GLRecord, ?>> columnConfigList;
  columnConfigList = new ArrayList<ColumnConfig<GLRecord, ?>>();
  if (_selectionModel instanceof CheckBoxSelectionModel) {
    columnConfigList.add(((CheckBoxSelectionModel<GLRecord>)_selectionModel).getColumn());
  }
  for (final GLGridColumnDef gridColumnDef : _gridColumnDefList) {
    ColumnConfig<GLRecord, ?> columnConfig = null;
    final IGLColumn column = gridColumnDef.getColumn();
    switch (column.getDataType()) {
      case Boolean:
        columnConfig = createColumnConfigBoolean(gridColumnDef, column);
        break;
      case Currency:
        columnConfig = createColumnConfigBigDecimal(gridColumnDef, column);
        break;
      case Date:
        columnConfig = createColumnConfigDateTime(gridColumnDef, column, "dd MMM yyyy");
        break;
      case DateTime:
        columnConfig = createColumnConfigDateTime(gridColumnDef, column, "dd MMM yyyy hh:mm a");
        break;
      case Decimal:
        columnConfig = createColumnConfigBigDecimal(gridColumnDef, column);
        break;
      case Int:
        if (column.getParentTable() == null) {
          columnConfig = createColumnConfigInteger(gridColumnDef, column);
        }
        else {
          columnConfig = createColumnConfigForeignKey(gridColumnDef, column.getParentTable(), //
                                                      column);
        }
        break;
      case String:
        columnConfig = createColumnConfigString(gridColumnDef, column);
        break;
    }
    if (columnConfig != null) {
      applyColumnWidthToCell(columnConfig);
      final SafeStyles _textStyles = SafeStylesUtils.fromTrustedString("padding:0px 0px 1px 1px;");
      columnConfig.setColumnTextStyle(_textStyles);
      gridColumnDef.setColumnConfig(columnConfig, columnConfigList.size());
      columnConfigList.add(columnConfig);
    }
  }
  result = new ColumnModel<GLRecord>(columnConfigList);
  result.addColumnWidthChangeHandler(createColumnModelColumnWidthChangeHandler());
  for (final ColumnConfig<GLRecord, ?> columnConfig : _checkBoxSet) {
    centerCheckBox(columnConfig);
  }
  return result;
}
//--------------------------------------------------------------------------------------------------
private ColumnWidthChangeHandler createColumnModelColumnWidthChangeHandler() {
  return new ColumnWidthChangeHandler() {
    @SuppressWarnings("unchecked")
    @Override
    public void onColumnWidthChange(final ColumnWidthChangeEvent event) {
      final ColumnConfig<GLRecord, ?> columnConfig;
      columnConfig = (ColumnConfig<GLRecord, ?>)event.getColumnConfig();
      if (_checkBoxSet.contains(columnConfig)) {
        centerCheckBox(columnConfig);
      }
      else {
        applyColumnWidthToCell(columnConfig);
      }
      _grid.getView().refresh(true);
    }
  };
}
//--------------------------------------------------------------------------------------------------
private void createContentPanel(final String headingText) {
  _contentPanel = new ContentPanel();
  if (GLUtil.isBlank(headingText)) {
    _contentPanel.setHeaderVisible(false);
  }
  else {
    _contentPanel.setHeaderVisible(true);
    _contentPanel.setHeadingText(headingText);
  }
  _contentPanel.setButtonAlign(BoxLayoutPack.START);
  createContentPanelButtons();
}
//--------------------------------------------------------------------------------------------------
private void createContentPanelButtons() {
  createContentPanelNewButton();
  _contentPanel.addButton(new TextButton("Undo Changes", new SelectHandler() {
    @Override
    public void onSelect(final SelectEvent event) {
      _listStore.rejectChanges();
    }
  }));
  _contentPanel.addButton(new TextButton("Save", new SelectHandler() {
    @Override
    public void onSelect(final SelectEvent event) {
      _listStore.commitChanges();
    }
  }));
  _contentPanel.addButton(createContentPanelDeleteButton());
  createContentPanelDeleteButton();
}
//--------------------------------------------------------------------------------------------------
private TextButton createContentPanelDeleteButton() {
  return new TextButton("Delete Selected", new SelectHandler() {
    @Override
    public void onSelect(final SelectEvent selectEvent) {
      final List<GLRecord> selectedRecordList = _selectionModel.getSelectedItems();
      if (selectedRecordList.size() == 0) {
        final AlertMessageBox messageBox;
        messageBox = new AlertMessageBox("Delete Rows", "You haven't selected any rows to delete");
        messageBox.show();
        return;
      }
      final String rowMessage;
      if (selectedRecordList.size() == 1) {
        rowMessage = "this row";
      }
      else {
        rowMessage = "these " + selectedRecordList.size() + " rows";
      }
      final ConfirmMessageBox messageBox;
      messageBox = new ConfirmMessageBox("Delete Rows", //
                                         "Are you sure you want to delete " + rowMessage + "?");
      messageBox.addDialogHideHandler(new DialogHideHandler() {
        @Override
        public void onDialogHide(final DialogHideEvent hideEvent) {
          if (hideEvent.getHideButton() == PredefinedButton.YES) {
            final ArrayList<GLRecord> recordList;
            recordList = new ArrayList<GLRecord>(selectedRecordList.size());
            for (final GLRecord record : selectedRecordList) {
              recordList.add(record);
            }
            _listStore.remove(recordList);
          }
        }
      });
      messageBox.show();
    }
  });
}
//--------------------------------------------------------------------------------------------------
private void createContentPanelNewButton() {
  _contentPanel.addButton(new TextButton("New", new SelectHandler() {
    @Override
    public void onSelect(final SelectEvent event) {
      GLUtil.createNewRecord(_listStore.getRecordDef(), new IGLCreateNewRecordCallback() {
        @Override
        public void onFailure(final Throwable t) {

        }
        @Override
        public void onSuccess(final GLRecord record) {
          _gridInlineEditing.cancelEditing();
          _listStore.add(0, record);
          final int row = _listStore.indexOf(record);
          _gridInlineEditing.startEditing(new GridCell(row, 0));
        }
      });
    }
  }));
}
//--------------------------------------------------------------------------------------------------
private void createGrid() {
  //  grid.setContextMenu(getSpreadsheetMenu());
  //  getGridInlineEditing().setEditableGrid(grid);
  //  grid.addResizeHandler(new ResizeHandler() {
  //    @Override
  //    public void onResize(final ResizeEvent event) {
  //      adjustColumnWidths();
  //    }
  //  });
  createCheckBoxSelectionModel();
  _gridInlineEditing = new GridInlineEditing<>(null);
  _grid = new Grid<>(_listStore, createColumnModel());
  _grid.addRowClickHandler(new RowClickEvent.RowClickHandler() {
    @Override
    public void onRowClick(final RowClickEvent event) {
      if (_listStore.getModifiedRecords().size() > 0) {
        _grid.setBorders(false);
      }
    }
  });
  _grid.setBorders(true);
  _grid.setColumnReordering(true);
  _grid.setLoadMask(true);
  _grid.setSelectionModel(_selectionModel);
  _grid.setView(createGridView());
  addHeaderContextMenuHandler();
  _gridInlineEditing.setEditableGrid(_grid);
  _contentPanel.add(_grid);
  _contentPanel.forceLayout();
}
//--------------------------------------------------------------------------------------------------
private void createGridColumnDefMap() {
  _gridColumnDefMap = new TreeMap<String, GLGridColumnDef>();
  for (final GLGridColumnDef gridColumnDef : _gridColumnDefList) {
    _gridColumnDefMap.put(gridColumnDef.getColumn().toString(), gridColumnDef);
  }
}
//--------------------------------------------------------------------------------------------------
private GridView<GLRecord> createGridView() {
  final GridView<GLRecord> result = new GridView<GLRecord>();
  result.setColumnLines(true);
  result.setEmptyText(_noRowsMessage);
  result.setForceFit(false);
  result.setStripeRows(true);
  return result;
}
//--------------------------------------------------------------------------------------------------
public GLListStore getListStore() {
  return _listStore;
}
//--------------------------------------------------------------------------------------------------
protected abstract void loadGridColumnDefList();
//--------------------------------------------------------------------------------------------------
private void resizeColumnToFit(final int columnIndex) {
  final ColumnConfig<GLRecord, ?> columnConfig = _grid.getColumnModel().getColumn(columnIndex);
  final TextMetrics textMetrics = TextMetrics.get();
  textMetrics.bind(_grid.getView().getHeader().getAppearance().styles().head());
  int maxWidth = textMetrics.getWidth(columnConfig.getHeader().asString()) + 6;
  if (_listStore.size() > 0) {
    final String className = GLUtil.getLowestLevelCSSClassName(_grid.getView().getCell(0, 1), //
                                                               "fontSize");
    textMetrics.bind(className);
    for (final GLRecord record : _listStore.getAll()) {
      final Object value = columnConfig.getValueProvider().getValue(record);
      if (value != null) {
        String valueAsString;
        int decoratorIconWidth;
        if (columnConfig.getCell() instanceof DateCell) {
          final DateCell dateCell = (DateCell)columnConfig.getCell();
          valueAsString = dateCell.getPropertyEditor().render((Date)value);
          decoratorIconWidth = 18;
        }
        else {
          valueAsString = value.toString();
          decoratorIconWidth = 0;
        }
        final int width = textMetrics.getWidth(valueAsString) + 2 + decoratorIconWidth;
        maxWidth = width > maxWidth ? width : maxWidth;
      }
    }
    for (final Store<GLRecord>.Record record : _listStore.getModifiedRecords()) {
      final int width = textMetrics.getWidth(record.getValue(columnConfig.getValueProvider()) //
                                                   .toString()) + 2;
      maxWidth = width > maxWidth ? width : maxWidth;
    }
  }
  columnConfig.setWidth(maxWidth);
  if (_checkBoxSet.contains(columnConfig)) {
    centerCheckBox(columnConfig);
  }
  else {
    applyColumnWidthToCell(columnConfig);
  }
}
//--------------------------------------------------------------------------------------------------
private void resizeNextColumn(final ProgressMessageBox messageBox, final int columnIndex,
                              final int lastColumnIndex) {
  Scheduler.get().scheduleDeferred(new ScheduledCommand() {
    @Override
    public void execute() {
      resizeColumnToFit(columnIndex);
      if (columnIndex == lastColumnIndex) {
        messageBox.hide();
        _grid.getView().refresh(true);
        return;
      }
      messageBox.updateProgress((double)columnIndex / (lastColumnIndex + 1), "{0}% Complete");
      resizeNextColumn(messageBox, columnIndex + 1, lastColumnIndex);
    }
  });
}
//--------------------------------------------------------------------------------------------------
private void waitForComboBoxData() {
  final HashSet<IGLTable> loadTableSet = new HashSet<>();
  for (final GLGridColumnDef gridColumnDef : _gridColumnDefList) {
    final IGLTable parentTable = gridColumnDef.getColumn().getParentTable();
    if (parentTable != null) {
      loadTableSet.add(parentTable);
      addLookupTableLoadedEventHandler(loadTableSet);
      GLUtil.getLookupTableCache().reload(parentTable, true);
    }
  }
  if (loadTableSet.size() == 0) {
    createGrid();
  }
}
//--------------------------------------------------------------------------------------------------
}