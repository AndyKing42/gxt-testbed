package org.greatlogic.gxttestbed.client;

import org.greatlogic.glgwt.client.core.GLLog;
import org.greatlogic.gxttestbed.client.widget.GridWidgetManager;
import org.greatlogic.gxttestbed.client.widget.PetGridWidget;
import org.greatlogic.gxttestbed.shared.IGXTTestbedEnums.ETestDataOption;
import org.greatlogic.gxttestbed.shared.IRemoteServiceAsync;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class DBUtil {
//--------------------------------------------------------------------------------------------------
public static void recreateTables() {
  ClientFactory.Instance.getRemoteService().recreateTables(new AsyncCallback<Void>() {
    @Override
    public void onFailure(final Throwable t) {
      GLLog.popup(10, "Database table creation failed:" + t.getMessage());
    }
    @Override
    public void onSuccess(final Void result) {
      GLLog.popup(10, "Database table creation is complete");
      final PetGridWidget petGrid = GridWidgetManager.getPetGrid("Main");
      ClientFactory.Instance.getLookupTableCache().reload(null);
      GXTTestbedCache.loadPets(petGrid.getListStore());
    }
  });
}
//--------------------------------------------------------------------------------------------------
public static void reloadTestData() {
  final IRemoteServiceAsync remoteService = ClientFactory.Instance.getRemoteService();
  remoteService.loadTestData(ETestDataOption.Reload.name(), new AsyncCallback<Void>() {
    @Override
    public void onFailure(final Throwable t) {
      GLLog.popup(10, "Test data reload failed:" + t.getMessage());
    }
    @Override
    public void onSuccess(final Void result) {
      GLLog.popup(10, "Test data reload is complete");
      final PetGridWidget petGrid = GridWidgetManager.getPetGrid("Main");
      ClientFactory.Instance.getLookupTableCache().reload(null);
      GXTTestbedCache.loadPets(petGrid.getListStore());
    }
  });
}
//--------------------------------------------------------------------------------------------------
}