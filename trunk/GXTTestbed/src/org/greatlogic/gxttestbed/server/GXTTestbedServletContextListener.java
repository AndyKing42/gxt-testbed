package org.greatlogic.gxttestbed.server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import com.google.appengine.api.utils.SystemProperty;
import com.greatlogic.glbase.gldb.GLDBType;
import com.greatlogic.glbase.gldb.GLDataSource;
import com.greatlogic.glbase.gldb.IGLDBEnums.EGLDBConfigAttribute;
import com.greatlogic.glbase.gldb.IGLDBEnums.EGLDBType;
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
  final GLXMLElement dsElement = new GLXMLElement("DataSources");
  final GLXMLElement fapElement = new GLXMLElement("FosterAPet", dsElement);
  fapElement.addAttribute(EGLDBConfigAttribute.Active, true);
  fapElement.addAttribute(EGLDBConfigAttribute.InitialConnections, 5);
  fapElement.addAttribute(EGLDBConfigAttribute.Default, true);
  fapElement.addAttribute(EGLDBConfigAttribute.MaxConnections, 20);
  fapElement.addAttribute(EGLDBConfigAttribute.Name, "FosterAPet");
  fapElement.addAttribute(EGLDBConfigAttribute.Type, EGLDBType.MySQL.name());
  if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
    fapElement.addAttribute(EGLDBConfigAttribute.Password, "root");
    fapElement.addAttribute(EGLDBConfigAttribute.User, "andy");
    GLDBType.getDBType(EGLDBType.MySQL).setDriverManagerClassName("com.mysql.jdbc.GoogleDriver");
    fapElement.addAttribute(EGLDBConfigAttribute.ConnectionURL,
                            "jdbc:google:mysql://your-project-id:your-instance-name/FosterAPet");
  }
  else {
    fapElement.addAttribute(EGLDBConfigAttribute.Password, "andy");
    fapElement.addAttribute(EGLDBConfigAttribute.ServerAddress, "localhost");
    fapElement.addAttribute(EGLDBConfigAttribute.User, "andy");
  }
  GLDataSource.initialize(dsElement);
  GLLog.infoSummary("Context initialized");
}
//--------------------------------------------------------------------------------------------------
}