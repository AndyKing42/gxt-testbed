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
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.TreeSet;
import org.greatlogic.glgwt.client.core.GLListStore;
import org.greatlogic.glgwt.client.core.GLRecord;
import org.greatlogic.glgwt.client.core.GLUtil;
import org.greatlogic.glgwt.client.event.GLLookupTableLoadedEvent;
import org.greatlogic.glgwt.client.event.GLLookupTableLoadedEvent.IGLLookupTableLoadedEventHandler;
import org.greatlogic.glgwt.shared.IGLColumn;
import org.greatlogic.glgwt.shared.IGLLookupType;
import org.greatlogic.glgwt.shared.IGLRecordValidator;
import org.greatlogic.glgwt.shared.IGLTable;
import org.greatlogic.gxttestbed.shared.IDBEnums.Pet;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.Event.Type;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.util.TextMetrics;
import com.sencha.gxt.data.shared.Store;
import com.sencha.gxt.widget.core.client.box.ProgressMessageBox;
import com.sencha.gxt.widget.core.client.event.HeaderContextMenuEvent;
import com.sencha.gxt.widget.core.client.event.HeaderContextMenuEvent.HeaderContextMenuHandler;
import com.sencha.gxt.widget.core.client.event.RowClickEvent;
import com.sencha.gxt.widget.core.client.grid.CellSelectionModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GridView;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.grid.filters.StringFilter;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

public abstract class GLGridWidget implements IsWidget {
//--------------------------------------------------------------------------------------------------
private static final int             _resizeColumnExtraPadding;
private static final TextMetrics     _textMetrics;
private GLGridColumnModel            _columnModel;
private final IGLColumn[]            _columns;
private final GLGridContentPanel     _contentPanel;
protected Grid<GLRecord>             _grid;
private GLGridEditingWrapper         _gridEditingWrapper;
private final boolean                _inlineEditing;
protected GLListStore                _listStore;
private HandlerRegistration          _lookupTableLoadedHandlerRegistration;
private final String                 _noRowsMessage;
private final IGLRecordValidator     _recordValidator;
private final boolean                _rowLevelCommits;
private final TreeSet<GLRecord>      _selectedRecordSet;
private CellSelectionModel<GLRecord> _selectionModel;
private final boolean                _useCheckBoxSelection;
//--------------------------------------------------------------------------------------------------
static {
  _resizeColumnExtraPadding = 10;
  _textMetrics = TextMetrics.get();
}
//--------------------------------------------------------------------------------------------------
protected GLGridWidget(final String headingText, final String noRowsMessage,
                       final IGLRecordValidator recordValidator, final boolean inlineEditing,
                       final boolean useCheckBoxSelection, final boolean rowLevelCommits,
                       final IGLColumn[] columns) {
  super();
  _noRowsMessage = noRowsMessage == null ? "There are no results to display" : noRowsMessage;
  _recordValidator = recordValidator;
  _inlineEditing = inlineEditing;
  _useCheckBoxSelection = useCheckBoxSelection;
  _rowLevelCommits = rowLevelCommits;
  _columns = columns;
  _listStore = new GLListStore();
  _selectedRecordSet = new TreeSet<>();
  _contentPanel = new GLGridContentPanel(this, headingText);
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
          resizeNextColumn(messageBox, _useCheckBoxSelection ? 1 : 0, _grid.getColumnModel()
                                                                           .getColumnCount() - 1);
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
 * lookup tables are added to the lookupTableSet; when the LookupLoadedEvent is fired the table
 * that's been loaded is removed from the set; when all tables have been loaded the grid is created.
 * @param lookupTableSet The set that contains the list of tables that need to be loaded prior to
 * creating the grid.
 */
private void addLookupLoadedEventHandler(final HashSet<IGLTable> lookupTableSet) {
  if (_lookupTableLoadedHandlerRegistration == null) {
    final IGLLookupTableLoadedEventHandler handler = new IGLLookupTableLoadedEventHandler() {
      @Override
      public void onLookupTableLoadedEvent(final GLLookupTableLoadedEvent lookupTableLoadedEvent) {
        lookupTableSet.remove(lookupTableLoadedEvent.getTable());
        if (lookupTableSet.size() == 0) {
          _lookupTableLoadedHandlerRegistration.removeHandler();
          _lookupTableLoadedHandlerRegistration = null;
          createGrid();
        }
      }
    };
    final Type<IGLLookupTableLoadedEventHandler> eventType;
    eventType = GLLookupTableLoadedEvent.LookupTableLoadedEventType;
    _lookupTableLoadedHandlerRegistration = GLUtil.getEventBus().addHandler(eventType, handler);
  }
}
//--------------------------------------------------------------------------------------------------
@Override
public Widget asWidget() {
  return _contentPanel;
}
//--------------------------------------------------------------------------------------------------
private void createGrid() {
  _selectionModel = new CellSelectionModel<>();
  _columnModel = new GLGridColumnModel(this, _inlineEditing, _useCheckBoxSelection);
  _grid = new Grid<>(_listStore, _columnModel);
  _grid.addRowClickHandler(new RowClickEvent.RowClickHandler() {
    @Override
    public void onRowClick(final RowClickEvent event) {
      final Collection<Store<GLRecord>.Record> records = _listStore.getModifiedRecords();
      if (records.size() > 0) {
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
  addFilters();
  _gridEditingWrapper = new GLGridEditingWrapper(this, _inlineEditing, _recordValidator);
  _contentPanel.add(_grid);
  _contentPanel.forceLayout();
}
//--------------------------------------------------------------------------------------------------
private void addFilters() {
  //  NumericFilter<GLRecord, Double> lastFilter = new NumericFilter<GLRecord, Double>(props.last(), new DoublePropertyEditor());
  final ValueProvider<GLRecord, String> valueProvider;
  valueProvider = _columnModel.getStringValueProvider(Pet.PetName);
  final StringFilter<GLRecord> stringFilter = new StringFilter<GLRecord>(valueProvider);
  //  DateFilter<GLRecord> dateFilter = new DateFilter<GLRecord>(props.lastTrans());
  //  dateFilter.setMinDate(new DateWrapper().addDays(-5).asDate());
  //  dateFilter.setMaxDate(new DateWrapper().addMonths(2).asDate());
  //
  //  BooleanFilter<GLRecord> booleanFilter = new BooleanFilter<GLRecord>(props.split());
  //  ListFilter<GLRecord, String> listFilter = new ListFilter<GLRecord, String>(props.industry(), typeStore);
  //
  final GridFilters<GLRecord> gridFilters = new GridFilters<GLRecord>();
  gridFilters.initPlugin(_grid);
  gridFilters.setLocal(true);
  gridFilters.addFilter(stringFilter);
}
//--------------------------------------------------------------------------------------------------
private GridView<GLRecord> createGridView() {
  final GridView<GLRecord> result = new GridView<>();
  result.setColumnLines(true);
  result.setEmptyText(_noRowsMessage);
  result.setForceFit(false);
  result.setStripeRows(true);
  return result;
}
//--------------------------------------------------------------------------------------------------
GLGridColumnModel getColumnModel() {
  return _columnModel;
}
//--------------------------------------------------------------------------------------------------
IGLColumn[] getColumns() {
  return _columns;
}
//--------------------------------------------------------------------------------------------------
Grid<GLRecord> getGrid() {
  return _grid;
}
//--------------------------------------------------------------------------------------------------
GLGridEditingWrapper getGridEditingWrapper() {
  return _gridEditingWrapper;
}
//--------------------------------------------------------------------------------------------------
public GLListStore getListStore() {
  return _listStore;
}
//--------------------------------------------------------------------------------------------------
boolean getRowLevelCommits() {
  return _rowLevelCommits;
}
//--------------------------------------------------------------------------------------------------
TreeSet<GLRecord> getSelectedRecordSet() {
  return _selectedRecordSet;
}
//--------------------------------------------------------------------------------------------------
CellSelectionModel<GLRecord> getSelectionModel() {
  return _selectionModel;
}
//--------------------------------------------------------------------------------------------------
private int resizeColumnGetWidth(final Object value, final DateTimeFormat dateTimeFormat) {
  if (value == null) {
    return 0;
  }
  final String valueAsString = dateTimeFormat == null ? value.toString() //
                                                     : dateTimeFormat.format((Date)value);
  return _textMetrics.getWidth(valueAsString);
}
//--------------------------------------------------------------------------------------------------
private void resizeColumnToFit(final int columnIndex) {
  final String columnName = _columns[columnIndex - (_useCheckBoxSelection ? 1 : 0)].toString();
  final GLColumnConfig<?> columnConfig = _columnModel.getColumnConfig(columnName);
  final DateTimeFormat dateTimeFormat = columnConfig.getDateTimeFormat();
  _textMetrics.bind(_grid.getView().getHeader().getAppearance().styles().head());
  int maxWidth = _textMetrics.getWidth(columnConfig.getHeader().asString()) + 6;
  if (_listStore.size() > 0) {
    final String className = _grid.getView().getCell(1, 1).getClassName();
    _textMetrics.bind(className);
    for (final GLRecord record : _listStore.getAll()) {
      final Object value = columnConfig.getValueProvider().getValue(record);
      final int width = resizeColumnGetWidth(value, dateTimeFormat) + _resizeColumnExtraPadding;
      maxWidth = width > maxWidth ? width : maxWidth;
    }
    for (final Store<GLRecord>.Record record : _listStore.getModifiedRecords()) {
      final Object value = record.getValue(columnConfig.getValueProvider());
      final int width = resizeColumnGetWidth(value, dateTimeFormat) + _resizeColumnExtraPadding;
      maxWidth = width > maxWidth ? width : maxWidth;
    }
  }
  columnConfig.setWidth(maxWidth < 40 ? 40 : maxWidth);
  if (_columnModel.isCheckbox(columnConfig)) {
    _columnModel.centerCheckBox(columnConfig);
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
  final HashSet<IGLTable> lookupTableSet = new HashSet<>();
  for (final IGLColumn column : _columns) {
    final IGLLookupType lookupType = column.getLookupType();
    if (lookupType != null) {
      final IGLTable table = lookupType.getTable();
      if (table != null) {
        lookupTableSet.add(table);
        addLookupLoadedEventHandler(lookupTableSet);
        GLUtil.getLookupCache().reload(table, true);
      }
    }
  }
  if (lookupTableSet.size() == 0) {
    createGrid();
  }
}
//--------------------------------------------------------------------------------------------------
}