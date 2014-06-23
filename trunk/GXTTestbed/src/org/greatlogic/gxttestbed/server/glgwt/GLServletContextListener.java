package org.greatlogic.gxttestbed.server.glgwt;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import com.greatlogic.glbase.gllib.GLGAE;
import com.greatlogic.glbase.gllib.GLLog;
import com.greatlogic.glbase.glxml.GLXMLElement;

public class GLServletContextListener implements ServletContextListener {
//--------------------------------------------------------------------------------------------------
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
}
//--------------------------------------------------------------------------------------------------
}