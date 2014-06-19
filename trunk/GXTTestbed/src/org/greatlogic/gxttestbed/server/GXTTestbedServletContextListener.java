package org.greatlogic.gxttestbed.server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import com.greatlogic.glbase.gllib.GLGAE;
import com.greatlogic.glbase.gllib.GLLog;
import com.greatlogic.glbase.gllib.IGLProgram;
import com.greatlogic.glbase.glxml.GLXMLElement;

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
  GLGAE.setUsingGAE(true);
  GLLog.initialize(true);
  final GLXMLElement logElement = new GLXMLElement("Log");
  logElement.addAttribute("Level", "Debug");
  logElement.addAttribute("LoggerName", "GXTTestbed");
  GLLog.setLoggingOptions(logElement, null, null);
  //  GLDataSource.initialize(dsElement);
  GLLog.infoSummary("Context initialized");
}
//--------------------------------------------------------------------------------------------------
}