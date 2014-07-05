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
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;
import org.greatlogic.glgwt.client.core.GLListStore;
import org.greatlogic.glgwt.client.core.GLLog;
import org.greatlogic.glgwt.client.core.GLRecord;
import org.greatlogic.glgwt.client.core.GLUtil;
import org.greatlogic.glgwt.client.core.IGLCreateNewRecordCallback;
import org.greatlogic.glgwt.client.core.IGLGridRowEditingValidator;
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
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.safecss.shared.SafeStylesUtils;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.text.shared.AbstractSafeHtmlRenderer;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.Event.Type;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.sencha.gxt.cell.core.client.NumberCell;
import com.sencha.gxt.cell.core.client.SimpleSafeHtmlCell;
import com.sencha.gxt.cell.core.client.form.CheckBoxCell;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.cell.core.client.form.NumberInputCell;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.util.TextMetrics;
import com.sencha.gxt.data.shared.Converter;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.Store;
import com.sencha.gxt.data.shared.StringLabelProvider;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.box.ProgressMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.event.BeforeSelectEvent;
import com.sencha.gxt.widget.core.client.event.BeforeSelectEvent.BeforeSelectHandler;
import com.sencha.gxt.widget.core.client.event.ColumnWidthChangeEvent;
import com.sencha.gxt.widget.core.client.event.ColumnWidthChangeEvent.ColumnWidthChangeHandler;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent.CompleteEditHandler;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.event.HeaderContextMenuEvent;
import com.sencha.gxt.widget.core.client.event.HeaderContextMenuEvent.HeaderContextMenuHandler;
import com.sencha.gxt.widget.core.client.event.RefreshEvent;
import com.sencha.gxt.widget.core.client.event.RowClickEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.BigDecimalField;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.DateTimePropertyEditor;
import com.sencha.gxt.widget.core.client.form.IntegerField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor.BigDecimalPropertyEditor;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.Validator;
import com.sencha.gxt.widget.core.client.form.error.DefaultEditorError;
import com.sencha.gxt.widget.core.client.form.validator.EmptyValidator;
import com.sencha.gxt.widget.core.client.grid.CellSelectionModel;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.Grid.GridCell;
import com.sencha.gxt.widget.core.client.grid.GridSelectionModel;
import com.sencha.gxt.widget.core.client.grid.GridView;
import com.sencha.gxt.widget.core.client.grid.editing.GridEditing;
import com.sencha.gxt.widget.core.client.grid.editing.GridInlineEditing;
import com.sencha.gxt.widget.core.client.grid.editing.GridRowEditing;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

public abstract class GLGridWidget implements IsWidget {
//--------------------------------------------------------------------------------------------------
private static final String                      Zeroes = "00000000000000000000000000";
private final HashSet<ColumnConfig<GLRecord, ?>> _checkBoxSet;
private ContentPanel                             _contentPanel;
protected Grid<GLRecord>                         _grid;
protected ArrayList<GLGridColumnDef>             _gridColumnDefList;
private TreeMap<String, GLGridColumnDef>         _gridColumnDefMap;                    // column name -> GLGridColumnDef
private GridEditing<GLRecord>                    _gridEditing;
private IGLGridRowEditingValidator               _gridRowEditingValidator;
private final boolean                            _inlineEditing;
protected GLListStore                            _listStore;
private HandlerRegistration                      _lookupTableLoadedHandlerRegistration;
private final String                             _noRowsMessage;
private GridSelectionModel<GLRecord>             _selectionModel;
private final boolean                            _useCheckBoxSelectionModel;
//--------------------------------------------------------------------------------------------------
protected GLGridWidget(final String headingText, final String noRowsMessage,
                       final boolean inlineEditing, final boolean useCheckBoxSelectionModel) {
  super();
  _noRowsMessage = noRowsMessage == null ? "There are no results to display" : noRowsMessage;
  _inlineEditing = inlineEditing;
  _useCheckBoxSelectionModel = useCheckBoxSelectionModel;
  _listStore = new GLListStore();
  _checkBoxSet = new HashSet<>();
  _gridColumnDefList = new ArrayList<>();
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
          resizeNextColumn(messageBox, _useCheckBoxSelectionModel ? 1 : 0,
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
@Override
public Widget asWidget() {
  return _contentPanel;
}
//--------------------------------------------------------------------------------------------------
private void centerCheckBox(final ColumnConfig<GLRecord, ?> columnConfig) {
  if (_inlineEditing) {
    final int leftPadding = (columnConfig.getWidth() - 12) / 2;
    columnConfig.setColumnTextStyle(SafeStylesUtils.fromTrustedString("padding: 3px 0px 0px " +
                                                                      leftPadding + "px;"));
  }
}
//--------------------------------------------------------------------------------------------------
private void createSelectionModel() {
  if (_useCheckBoxSelectionModel) {
    final IdentityValueProvider<GLRecord> identityValueProvider = new IdentityValueProvider<>();
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
  else {
    _selectionModel = new CellSelectionModel<>();
  }
}
//--------------------------------------------------------------------------------------------------
private ColumnConfig<GLRecord, BigDecimal> createColumnConfigBigDecimal(final GLGridColumnDef gridColumnDef,
                                                                        final IGLColumn column) {
  final ColumnConfig<GLRecord, BigDecimal> result;
  final ValueProvider<GLRecord, BigDecimal> valueProvider;
  valueProvider = new GLBigDecimalValueProvider(column, column.getNumberOfDecimalPlaces());
  result = new ColumnConfig<>(valueProvider, gridColumnDef.getWidth(), column.getTitle());
  result.setHorizontalAlignment(gridColumnDef.getHorizontalAlignment());
  NumberFormat numberFormat;
  if (column.getDataType() == EGLColumnDataType.Currency) {
    numberFormat = NumberFormat.getSimpleCurrencyFormat();
  }
  else {
    numberFormat = NumberFormat.getDecimalFormat();
  }
  result.setCell(new NumberCell<BigDecimal>(numberFormat));
  return result;
}
//--------------------------------------------------------------------------------------------------
private ColumnConfig<GLRecord, Boolean> createColumnConfigBoolean(final GLGridColumnDef gridColumnDef,
                                                                  final IGLColumn column) {
  final ColumnConfig<GLRecord, Boolean> result;
  final ValueProvider<GLRecord, Boolean> valueProvider = new GLBooleanValueProvider(column);
  result = new ColumnConfig<>(valueProvider, gridColumnDef.getWidth(), column.getTitle());
  result.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
  if (_inlineEditing) {
    result.setCell(new CheckBoxCell());
  }
  else {
    result.setCell(new SimpleSafeHtmlCell<Boolean>(new AbstractSafeHtmlRenderer<Boolean>() {
      @Override
      public SafeHtml render(final Boolean object) {
        // TODO: these need to be drawn (use AbstractCell#render?)
        return SafeHtmlUtils.fromTrustedString(object ? "&#x2713;" : "&#x2717");
      }
    }));
  }
  result.setSortable(false);
  _checkBoxSet.add(result);
  return result;
}
//--------------------------------------------------------------------------------------------------
private ColumnConfig<GLRecord, Date> createColumnConfigDateTime(final GLGridColumnDef gridColumnDef,
                                                                final IGLColumn column,
                                                                final String dateTimeFormatString) {
  final ColumnConfig<GLRecord, Date> result;
  result = new ColumnConfig<>(new GLDateValueProvider(column), gridColumnDef.getWidth(), //
                              column.getTitle());
  result.setHorizontalAlignment(gridColumnDef.getHorizontalAlignment());
  final DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat(dateTimeFormatString);
  gridColumnDef.setDateTimeFormat(dateTimeFormat);
  final DateCell dateCell = new DateCell(dateTimeFormat);
  result.setCell(dateCell);
  return result;
}
//--------------------------------------------------------------------------------------------------
private ColumnConfig<GLRecord, String> createColumnConfigForeignKey(final GLGridColumnDef gridColumnDef,
                                                                    final IGLTable lookupTable,
                                                                    final IGLColumn column) {
  final ColumnConfig<GLRecord, String> result;
  final ValueProvider<GLRecord, String> valueProvider = new GLForeignKeyValueProvider(lookupTable, //
                                                                                      column);
  result = new ColumnConfig<>(valueProvider, gridColumnDef.getWidth(), column.getTitle());
  result.setHorizontalAlignment(gridColumnDef.getHorizontalAlignment());
  result.setCell(new TextCell());
  return result;
}
//--------------------------------------------------------------------------------------------------
private ColumnConfig<GLRecord, Integer> createColumnConfigInteger(final GLGridColumnDef gridColumnDef,
                                                                  final IGLColumn column) {
  final ColumnConfig<GLRecord, Integer> result;
  result = new ColumnConfig<>(new GLIntegerValueProvider(column), gridColumnDef.getWidth(), //
                              column.getTitle());
  result.setHorizontalAlignment(gridColumnDef.getHorizontalAlignment());
  result.setCell(new NumberCell<Integer>());
  return result;
}
//--------------------------------------------------------------------------------------------------
private ColumnConfig<GLRecord, String> createColumnConfigString(final GLGridColumnDef gridColumnDef,
                                                                final IGLColumn column) {
  final ColumnConfig<GLRecord, String> result;
  result = new ColumnConfig<>(new GLStringValueProvider(column), gridColumnDef.getWidth(), //
                              column.getTitle());
  result.setHorizontalAlignment(gridColumnDef.getHorizontalAlignment());
  result.setCell(new TextCell());
  return result;
}
//--------------------------------------------------------------------------------------------------
private ColumnModel<GLRecord> createColumnModel() {
  ColumnModel<GLRecord> result;
  final ArrayList<ColumnConfig<GLRecord, ?>> columnConfigList = new ArrayList<>();
  if (_useCheckBoxSelectionModel) {
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
        columnConfig = createColumnConfigDateTime(gridColumnDef, column, "MM/dd/yy");
        break;
      case DateTime:
        columnConfig = createColumnConfigDateTime(gridColumnDef, column, "MM/dd/yy hh:mma");
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
      gridColumnDef.setColumnConfig(columnConfig, columnConfigList.size());
      columnConfigList.add(columnConfig);
    }
  }
  result = new ColumnModel<>(columnConfigList);
  result.addColumnWidthChangeHandler(new ColumnWidthChangeHandler() {
    @Override
    public void onColumnWidthChange(final ColumnWidthChangeEvent event) {
      final ColumnConfig<GLRecord, ?> columnConfig = columnConfigList.get(event.getIndex());
      if (_checkBoxSet.contains(columnConfig)) {
        centerCheckBox(columnConfig);
        _grid.getView().refresh(true);
      }
    }
  });
  for (final ColumnConfig<GLRecord, ?> columnConfig : _checkBoxSet) {
    centerCheckBox(columnConfig);
  }
  return result;
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
            final ArrayList<GLRecord> recordList = new ArrayList<>(selectedRecordList.size());
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
          _gridEditing.cancelEditing();
          _listStore.add(0, record);
          final int row = _listStore.indexOf(record);
          _gridEditing.startEditing(new GridCell(row, 0));
        }
      });
    }
  }));
}
//--------------------------------------------------------------------------------------------------
@SuppressWarnings("unchecked")
private void createEditors() {
  if (_inlineEditing) {
    _gridEditing = new GridInlineEditing<>(_grid);
  }
  else {
    final GridRowEditing<GLRecord> gridRowEditing = new GridRowEditing<>(_grid);
    _gridEditing = gridRowEditing;
    gridRowEditing.getSaveButton().addBeforeSelectHandler(new BeforeSelectHandler() {
      @Override
      public void onBeforeSelect(final BeforeSelectEvent event) {
        if (_gridRowEditingValidator != null &&
            !_gridRowEditingValidator.validate(new GLValidationRecord(_gridColumnDefMap,
                                                                      _gridEditing))) {
          event.setCancelled(true);
        }
      }
    });
  }
  _gridEditing.addCompleteEditHandler(new CompleteEditHandler<GLRecord>() {
    @Override
    public void onCompleteEdit(final CompleteEditEvent<GLRecord> event) {
      GLLog.popup(30, "CompleteEditEvent");
    }
  });
  for (final GLGridColumnDef gridColumnDef : _gridColumnDefList) {
    final ColumnConfig<GLRecord, ?> columnConfig = gridColumnDef.getColumnConfig();
    final IGLColumn column = gridColumnDef.getColumn();
    if (column.getChoiceList() != null) {
      createEditorsFixedCombobox(column, columnConfig);
    }
    else {
      switch (column.getDataType()) {
        case Boolean:
          if (!_inlineEditing) {
            _gridEditing.addEditor((ColumnConfig<GLRecord, Boolean>)columnConfig, new CheckBox());
          }
          break;
        case Currency:
          createEditorsDecimal(columnConfig, 2);
          break;
        case Date: {
          createEditorsDate(columnConfig);
          break;
        }
        case DateTime: {
          createEditorsDateTime(columnConfig);
          break;
        }
        case Decimal:
          createEditorsDecimal(columnConfig, column.getNumberOfDecimalPlaces());
          break;
        case Int:
          if (column.getParentTable() == null) {
            _gridEditing.addEditor((ColumnConfig<GLRecord, Integer>)columnConfig,
                                   new IntegerField());
          }
          else {
            createEditorsForeignKeyCombobox(gridColumnDef);
          }
          break;
        case String:
          _gridEditing.addEditor((ColumnConfig<GLRecord, String>)columnConfig, new TextField());
          break;
      }
    }
  }
}
//--------------------------------------------------------------------------------------------------
@SuppressWarnings("unchecked")
private void createEditorsDate(final ColumnConfig<GLRecord, ?> columnConfig) {
  final DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("MM/dd/yy");
  final DateTimePropertyEditor propertyEditor = new DateTimePropertyEditor(dateTimeFormat);
  final DateField dateField = new DateField(propertyEditor);
  dateField.setClearValueOnParseError(false);
  dateField.addValidator(new EmptyValidator<Date>()); // or textField.setAllowBlank(false);
  dateField.addValidator(new Validator<Date>() {
    @SuppressWarnings("deprecation")
    @Override
    public List<EditorError> validate(final Editor<Date> editor, final Date date) {
      final List<EditorError> result;
      if (date == null) {
        return null;
      }
      if (date.getMonth() == 1 || date.getDate() == 15) {
        result = new ArrayList<>();
        result.add(new DefaultEditorError(editor, "Nothing in February", ""));
        result.add(new DefaultEditorError(editor, "No ides!", ""));
        return result;
      }
      return null;
    }
  });
  _gridEditing.addEditor((ColumnConfig<GLRecord, Date>)columnConfig, dateField);
}
//--------------------------------------------------------------------------------------------------
@SuppressWarnings("unchecked")
private void createEditorsDateTime(final ColumnConfig<GLRecord, ?> columnConfig) {
  /*
   * In 3, I'd probably start by making an Editor instance with two sub-editors, one DateField and
   * one TimeField, each using @Path("") to have them bind to the same value.
   * 
   * Or make the new class implement IsField, and use setValue() and getValue() to modify/read both
   * sub-editors.
   * 
   * IsField is what is being used in 3 to replace most MultiField cases - it allows a widget to
   * supply methods that are helpful for most fields, and as it extends LeafValueEditor, it can be
   * used in GWT Editor framework, and subfields will be ignored, leaving the dev to write their own
   * logic for binding the values.
   */
  final DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("MM/dd/yy hh:mma");
  final DateTimePropertyEditor propertyEditor = new DateTimePropertyEditor(dateTimeFormat);
  final DateField dateField = new DateField(propertyEditor);
  dateField.setClearValueOnParseError(false);
  _gridEditing.addEditor((ColumnConfig<GLRecord, Date>)columnConfig, dateField);
}
//--------------------------------------------------------------------------------------------------
@SuppressWarnings("unchecked")
private void createEditorsDecimal(final ColumnConfig<GLRecord, ?> columnConfig,
                                  final int numberOfDecimalPlaces) {
  final NumberFormat format = NumberFormat.getFormat("#0." + //
                                                     Zeroes.substring(0, numberOfDecimalPlaces));
  final BigDecimalPropertyEditor propertyEditor;
  propertyEditor = new BigDecimalPropertyEditor(format);
  final NumberInputCell<BigDecimal> cell = new NumberInputCell<>(propertyEditor);
  final BigDecimalField bigDecimalField = new BigDecimalField(cell);
  bigDecimalField.setFormat(format);
  _gridEditing.addEditor((ColumnConfig<GLRecord, BigDecimal>)columnConfig, bigDecimalField);
}
//--------------------------------------------------------------------------------------------------
@SuppressWarnings("unchecked")
private void createEditorsFixedCombobox(final IGLColumn column,
                                        final ColumnConfig<GLRecord, ?> columnConfig) {
  final SimpleComboBox<String> combobox = new SimpleComboBox<>(new StringLabelProvider<>());
  combobox.setClearValueOnParseError(false);
  combobox.setTriggerAction(TriggerAction.ALL);
  combobox.add(column.getChoiceList());
  combobox.setForceSelection(true);
  _gridEditing.addEditor((ColumnConfig<GLRecord, String>)columnConfig, combobox);
}
//--------------------------------------------------------------------------------------------------
@SuppressWarnings("unchecked")
private void createEditorsForeignKeyCombobox(final GLGridColumnDef gridColumnDef) {
  final IGLColumn column = gridColumnDef.getColumn();
  final IGLTable parentTable = column.getParentTable();
  final GLListStore lookupListStore = GLUtil.getLookupTableCache().getListStore(parentTable);
  if (lookupListStore == null) {
    GLLog.popup(10, "Lookup list store not found for column:" + column);
    return;
  }
  final LabelProvider<GLRecord> labelProvider = new LabelProvider<GLRecord>() {
    @Override
    public String getLabel(final GLRecord record) {
      return record.asString(parentTable.getComboboxDisplayColumn());
    }
  };
  final ComboBox<GLRecord> comboBox = new ComboBox<>(lookupListStore, labelProvider);
  comboBox.setForceSelection(true);
  comboBox.setTriggerAction(TriggerAction.ALL);
  comboBox.setTypeAhead(true);
  final Converter<String, GLRecord> converter = new Converter<String, GLRecord>() {
    @Override
    public GLRecord convertModelValue(final String displayValue) {
      return GLUtil.getLookupTableCache().lookupRecord(parentTable, displayValue);
    }
    @Override
    public String convertFieldValue(final GLRecord record) {
      return record == null ? "" : record.asString(parentTable.getComboboxDisplayColumn());
    }
  };
  _gridEditing.addEditor((ColumnConfig<GLRecord, String>)gridColumnDef.getColumnConfig(),
                         converter, comboBox);
}
//--------------------------------------------------------------------------------------------------
private void createGrid() {
  createSelectionModel();
  final ColumnModel<GLRecord> columnModel = createColumnModel();
  _grid = new Grid<>(_listStore, columnModel);
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
  createEditors();
  _contentPanel.add(_grid);
  _contentPanel.forceLayout();
}
//--------------------------------------------------------------------------------------------------
private void createGridColumnDefMap() {
  _gridColumnDefMap = new TreeMap<>();
  for (final GLGridColumnDef gridColumnDef : _gridColumnDefList) {
    _gridColumnDefMap.put(gridColumnDef.getColumn().toString(), gridColumnDef);
  }
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
public GLListStore getListStore() {
  return _listStore;
}
//--------------------------------------------------------------------------------------------------
protected abstract void loadGridColumnDefList();
//--------------------------------------------------------------------------------------------------
private void resizeColumnToFit(final int columnIndex) {
  final ColumnConfig<GLRecord, ?> columnConfig = _grid.getColumnModel().getColumn(columnIndex);
  final GLGridColumnDef gridColumnDef = _gridColumnDefMap.get(columnConfig.getPath());
  final DateTimeFormat dateTimeFormat = gridColumnDef.getDateTimeFormat();
  final TextMetrics textMetrics = TextMetrics.get();
  textMetrics.bind(_grid.getView().getHeader().getAppearance().styles().head());
  int maxWidth = textMetrics.getWidth(columnConfig.getHeader().asString()) + 6;
  if (_listStore.size() > 0) {
    final int extraPadding = 10;
    final String className = _grid.getView().getCell(1, 1).getClassName();
    textMetrics.bind(className);
    for (final GLRecord record : _listStore.getAll()) {
      final Object value = columnConfig.getValueProvider().getValue(record);
      if (value != null) {
        String valueAsString;
        if (dateTimeFormat == null) {
          valueAsString = value.toString();
        }
        else {
          valueAsString = dateTimeFormat.format((Date)value);
        }
        final int width = textMetrics.getWidth(valueAsString) + extraPadding;
        maxWidth = width > maxWidth ? width : maxWidth;
      }
    }
    for (final Store<GLRecord>.Record record : _listStore.getModifiedRecords()) {
      final String valueAsString = record.getValue(columnConfig.getValueProvider()).toString();
      final int width = textMetrics.getWidth(valueAsString) + extraPadding;
      maxWidth = width > maxWidth ? width : maxWidth;
    }
  }
  columnConfig.setWidth(maxWidth < 40 ? 40 : maxWidth);
  if (_checkBoxSet.contains(columnConfig)) {
    centerCheckBox(columnConfig);
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
public void setGridRowEditingValidator(final IGLGridRowEditingValidator gridRowEditingValidator) {
  _gridRowEditingValidator = gridRowEditingValidator;
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