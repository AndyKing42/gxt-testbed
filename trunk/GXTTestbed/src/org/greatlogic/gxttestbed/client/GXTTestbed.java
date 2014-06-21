package org.greatlogic.gxttestbed.client;
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
import org.greatlogic.gxttestbed.client.glgwt.GLGridWidget;
import org.greatlogic.gxttestbed.client.glgwt.GLListStore;
import org.greatlogic.gxttestbed.client.glgwt.GLUtil;
import org.greatlogic.gxttestbed.client.widget.GridWidgetManager;
import org.greatlogic.gxttestbed.client.widget.MainLayoutWidget;
import org.greatlogic.gxttestbed.client.widget.PetGridWidget;
import org.greatlogic.gxttestbed.shared.TestData;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.sencha.gxt.widget.core.client.box.MessageBox;

public class GXTTestbed implements EntryPoint {
//--------------------------------------------------------------------------------------------------
private void login() {
  GLUtil.getRemoteService().login("name", "password", new AsyncCallback<Integer>() {
    @Override
    public void onFailure(final Throwable t) {

    }
    @Override
    public void onSuccess(final Integer userId) {
      final MessageBox messageBox = new MessageBox("login result:" + userId);
      messageBox.show();
    }
  });
}
//--------------------------------------------------------------------------------------------------
@Override
public void onModuleLoad() {
  GLUtil.initialize();
  final MainLayoutWidget mainLayoutWidget = new MainLayoutWidget();
  final boolean loadTestData = false;
  GXTTestbedCache.load();
  final PetGridWidget petGrid = GridWidgetManager.getPetGrid("Main");
  final GLGridWidget gridWidget = petGrid;
  if (loadTestData) {
    final GLListStore petTypeListStore = new GLListStore();
    TestData.loadPetTypeTestData(petTypeListStore);
    TestData.loadPetTestData(gridWidget.getListStore());
  }
  mainLayoutWidget.getCenterPanel().setWidget(gridWidget);
  RootPanel.get().add(mainLayoutWidget);
  GXTTestbedCache.loadPets(petGrid.getListStore());
}
//--------------------------------------------------------------------------------------------------
}