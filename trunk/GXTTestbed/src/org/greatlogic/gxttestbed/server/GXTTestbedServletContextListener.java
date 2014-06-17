package org.greatlogic.gxttestbed.server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.commons.lang3.StringUtils;
import com.greatlogic.glbase.gllib.GLUtil;
import com.greatlogic.glbase.gllib.IGLProgram;

public class GXTTestbedServletContextListener implements ServletContextListener {
//==================================================================================================
private static class GXTTestbedProgram implements IGLProgram {
@Override
public boolean displayCommandLineHelp() {
  return false;
}
}
//==================================================================================================
@Override
public void contextDestroyed(final ServletContextEvent event) {
  //
}
//--------------------------------------------------------------------------------------------------
@Override
public void contextInitialized(final ServletContextEvent event) {
  String configFilename = System.getenv("GXTTestbedConfigFilename");
  if (StringUtils.isEmpty(configFilename)) {
    configFilename = event.getServletContext().getInitParameter("ConfigFilename");
  }
  GLUtil.initializeProgram(new GXTTestbedProgram(), null, null, true, //
                           "<args ConfigFilename='" + configFilename + "'/>");
}
//--------------------------------------------------------------------------------------------------
}