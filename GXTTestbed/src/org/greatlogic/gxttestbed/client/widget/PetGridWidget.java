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
import org.greatlogic.glgwt.client.widget.GLGridWidget;
import org.greatlogic.glgwt.shared.GLRecordValidator;
import org.greatlogic.gxttestbed.shared.IDBEnums.Pet;

public class PetGridWidget extends GLGridWidget {
//--------------------------------------------------------------------------------------------------
public PetGridWidget(final GLRecordValidator recordValidator, final boolean inlineEditing,
                     final boolean useCheckBoxSelectionModel, final boolean rowLevelCommits,
                     final Pet... petColumns) {
  super(null, "There are no pets", recordValidator, inlineEditing, useCheckBoxSelectionModel,
        rowLevelCommits, petColumns);
}
//--------------------------------------------------------------------------------------------------
protected void addFilters() {
  //  typeStore.add("Auto");
  //  typeStore.add("Media");
  //  typeStore.add("Medical");
  //  typeStore.add("Tech");
  //
  //  NumericFilter<Stock, Double> lastFilter = new NumericFilter<Stock, Double>(props.last(), new DoublePropertyEditor());
  //  StringFilter<Stock> nameFilter = new StringFilter<Stock>(props.name());
  //  DateFilter<Stock> dateFilter = new DateFilter<Stock>(props.lastTrans());
  //  dateFilter.setMinDate(new DateWrapper().addDays(-5).asDate());
  //  dateFilter.setMaxDate(new DateWrapper().addMonths(2).asDate());
  //
  //  BooleanFilter<Stock> booleanFilter = new BooleanFilter<Stock>(props.split());
  //  ListFilter<Stock, String> listFilter = new ListFilter<Stock, String>(props.industry(), typeStore);
  //
  //  GridFilters<Stock> filters = new GridFilters<Stock>();
  //  filters.initPlugin(grid);
  //  filters.setLocal(true);
  //  filters.addFilter(lastFilter);
  //  filters.addFilter(nameFilter);
  //  filters.addFilter(dateFilter);
  //  filters.addFilter(booleanFilter);
  //  filters.addFilter(listFilter);
  //  NumericFilter<GLRecord, Double> lastFilter = new NumericFilter<GLRecord, Double>(props.last(), new DoublePropertyEditor());
  //  final ValueProvider<GLRecord, String> valueProvider;
  //  valueProvider = _columnModel.getStringValueProvider(Pet.PetName);
  //  final StringFilter<GLRecord> stringFilter = new StringFilter<GLRecord>(valueProvider);
  //  DateFilter<GLRecord> dateFilter = new DateFilter<GLRecord>(props.lastTrans());
  //  dateFilter.setMinDate(new DateWrapper().addDays(-5).asDate());
  //  dateFilter.setMaxDate(new DateWrapper().addMonths(2).asDate());
  //
  //  BooleanFilter<GLRecord> booleanFilter = new BooleanFilter<GLRecord>(props.split());
  //  ListFilter<GLRecord, String> listFilter = new ListFilter<GLRecord, String>(props.industry(), typeStore);
  //
}
//--------------------------------------------------------------------------------------------------
}