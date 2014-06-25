package org.greatlogic.glgwt.client.core;

import org.greatlogic.glgwt.shared.IGLEnums.EGLLogLevel;
import org.greatlogic.gxttestbed.shared.IRemoteServiceAsync;
import com.sencha.gxt.widget.core.client.info.DefaultInfoConfig;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.info.InfoConfig;

public class GLLog {
//--------------------------------------------------------------------------------------------------
private static IRemoteServiceAsync _remoteService;
//--------------------------------------------------------------------------------------------------
static {
  _remoteService = GLUtil.getRemoteService();
}
//--------------------------------------------------------------------------------------------------
public static void critical(final String location, final String message) {
  log(EGLLogLevel.Critical, location, message);
}
//--------------------------------------------------------------------------------------------------
public static void debug(final String location, final String message) {
  log(EGLLogLevel.Debug, location, message);
}
//--------------------------------------------------------------------------------------------------
public static void infoDetail(final String location, final String message) {
  log(EGLLogLevel.InfoDetail, location, message);
}
//--------------------------------------------------------------------------------------------------
public static void infoSummary(final String location, final String message) {
  log(EGLLogLevel.InfoSummary, location, message);
}
//--------------------------------------------------------------------------------------------------
public static void log(final EGLLogLevel logLevel, final String location, final String message) {
  _remoteService.log(logLevel.getPriority(), location, message, null);
}
//--------------------------------------------------------------------------------------------------
public static void major(final String location, final String message) {
  log(EGLLogLevel.Major, location, message);
}
//--------------------------------------------------------------------------------------------------
public static void minor(final String location, final String message) {
  log(EGLLogLevel.Minor, location, message);
}
//--------------------------------------------------------------------------------------------------
public static void popup(final int seconds, final String message) {
  final InfoConfig infoConfig = new DefaultInfoConfig("", message);
  infoConfig.setDisplay(seconds * 1000);
  final Info info = new Info();
  info.show(infoConfig);
}
//--------------------------------------------------------------------------------------------------
}