package org.greatlogic.gxttestbed.shared;

public interface IGXTTestbedEnums {
//--------------------------------------------------------------------------------------------------
public enum ETestDataOption {
Reload,
Unknown;
public static ETestDataOption lookup(final String testDataOptionString) {
  try {
    return ETestDataOption.valueOf(testDataOptionString);
  }
  catch (final Exception e) {
    return ETestDataOption.Unknown;
  }
}
}
//--------------------------------------------------------------------------------------------------
}