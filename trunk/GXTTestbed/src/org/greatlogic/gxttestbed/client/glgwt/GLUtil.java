package org.greatlogic.gxttestbed.client.glgwt;
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
import java.util.Date;
import java.util.Random;
import org.greatlogic.gxttestbed.shared.IRemoteService;
import org.greatlogic.gxttestbed.shared.IRemoteServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.sencha.gxt.widget.core.client.info.DefaultInfoConfig;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.info.InfoConfig;

public class GLUtil {
//--------------------------------------------------------------------------------------------------
private static Random              _random;
private static IRemoteServiceAsync _remoteService;
private static DateTimeFormat      _yyyymmddDateTimeFormat;
//--------------------------------------------------------------------------------------------------
static {
  _random = new Random(System.currentTimeMillis());
  _yyyymmddDateTimeFormat = DateTimeFormat.getFormat("yyyyMMdd");
}
//--------------------------------------------------------------------------------------------------
public static String dateAddDays(final String originalDate, final int numberOfDays) {
  final Date newDate = _yyyymmddDateTimeFormat.parseStrict(originalDate);
  CalendarUtil.addDaysToDate(newDate, numberOfDays);
  return _yyyymmddDateTimeFormat.format(newDate);
}
//--------------------------------------------------------------------------------------------------
/**
 * Formats a <code>Date</code>, <code>Calendar</code>, or milliseconds value returning a value in
 * the format YYYYMMDDHHMMSS.
 * @param dateOrMillis A <code>Date</code>, <code>Calendar</code>, or millisecond value.
 * @return The date in the format YYYYMMDDHHMMSS.
 */
public static String formatDateYYYYMMDDHHMMSS(final Object dateOrMillis) {
  String result;
  try {
    //    if (dateOrMillis instanceof java.util.Date) {
    //      result = DateFormatUtils.format((java.util.Date)dateOrMillis, "yyyyMMddHHmmss");
    //    }
    //    else if (dateOrMillis instanceof Calendar) {
    //      final Calendar calendar = (Calendar)dateOrMillis;
    //      final int year = calendar.get(Calendar.YEAR);
    //      final int month = calendar.get(Calendar.MONTH) + 1;
    //      final int day = calendar.get(Calendar.DAY_OF_MONTH);
    //      final int hour = calendar.get(Calendar.HOUR_OF_DAY);
    //      final int minute = calendar.get(Calendar.MINUTE);
    //      final int second = calendar.get(Calendar.SECOND);
    //      result = year + (month < 10 ? "0" : "") + month + (day < 10 ? "0" : "") + day +
    //               (hour < 10 ? "0" : "") + hour + (minute < 10 ? "0" : "") + minute +
    //               (second < 10 ? "0" : "") + second;
    //      //      result = DateFormatUtils.format((Calendar)dateOrMillis, "yyyyMMddHHmmss");
    //    }
    //    else {
    //      result = DateFormatUtils.format((Long)dateOrMillis, "yyyyMMddHHmmss");
    //    }
    result = "";
  }
  catch (final Exception e) {
    result = "";
  }
  return result;
} // formatDateYYYYMMDDHHMMSS()
//--------------------------------------------------------------------------------------------------
/**
 * Returns an <code>Object</code> formatted as a <code>String</code> in a format that is consistent
 * with the GL "normal" formatting.
 * @param value The value to be formatted.
 * @return The original value formatted according to GL expectations.
 */
public static String formatObjectSpecial(final Object value) {
  return formatObjectSpecial(value, "");
} // formatObjectSpecial()
//--------------------------------------------------------------------------------------------------
/**
 * Returns an <code>Object</code> formatted as a <code>String</code> in a format that is consistent
 * with the GL "normal" formatting.
 * @param value The value to be formatted.
 * @param defaultValue A default value to be returned in the value is null.
 * @return The original value formatted according to GL expectations.
 */
public static String formatObjectSpecial(final Object value, final String defaultValue) {
  String result;
  if (value == null) {
    result = defaultValue;
  }
  else if (value instanceof String) {
    result = (String)value;
  }
  else if (value instanceof Boolean) {
    result = (Boolean)value ? "Y" : "N";
  }
  else if (value instanceof java.util.Date) {
    result = GLUtil.formatDateYYYYMMDDHHMMSS(value);
  }
  else if (value instanceof BigDecimal) {
    result = ((BigDecimal)value).toPlainString();
  }
  else {
    result = value.toString();
  }
  return result;
} // formatObjectSpecial()
//--------------------------------------------------------------------------------------------------
/**
 * Returns a random value between 0 and the maxValue minus one.
 * @param maxValue The maximum value plus one for the random value.
 * @return A random integer between 0 (inclusive) and the specified value minus one.
 */
public static int getRandomInt(final int maxValue) {
  return _random.nextInt(maxValue);
}
//--------------------------------------------------------------------------------------------------
/**
 * Returns a random value between a minimum and maximum value minus one.
 * @param minValue The minimum value for the random value.
 * @param maxValue The maximum value plus one for the random value.
 * @return A random integer between the minimum and maximum value minus one.
 */
public static int getRandomInt(final int minValue, final int maxValue) {
  return _random.nextInt(maxValue - minValue) + minValue;
}
//--------------------------------------------------------------------------------------------------
public static IRemoteServiceAsync getRemoteService() {
  return _remoteService;
}
//--------------------------------------------------------------------------------------------------
public static void info(final int seconds, final String message) {
  final InfoConfig infoConfig = new DefaultInfoConfig("", message);
  infoConfig.setDisplay(seconds * 1000);
  final Info info = new Info();
  info.show(infoConfig);
}
//--------------------------------------------------------------------------------------------------
public static void initialize() {
  _remoteService = GWT.create(IRemoteService.class);
}
//--------------------------------------------------------------------------------------------------
public static boolean isBlank(final CharSequence s) {
  return s == null || s.toString().trim().length() == 0;
}
//--------------------------------------------------------------------------------------------------
/**
 * Converts a string value to its equivalent boolean value. If the string does not represent a
 * "true" value then the result will be set to <code>false</code>.
 * @param stringValue The string that will be converted.
 * @return true if the string begins with "y" or equals "true" (ignoring case).
 */
public static boolean stringToBoolean(final String stringValue) {
  return stringToBoolean(stringValue, false);
}
//--------------------------------------------------------------------------------------------------
/**
 * Converts a string value to its equivalent boolean value. The values "y" and "true", which are
 * compared with case ignored, are interpreted as true. If the string is null or is zero length then
 * the default value is returned.
 * @param stringValue The string that will be converted.
 * @param defaultValue The value that will be returned if the string is null or zero length.
 * @return true if the string begins with "y" or equals "true" (ignoring case).
 */
public static boolean stringToBoolean(final CharSequence stringValue, final boolean defaultValue) {
  boolean result;
  if (isBlank(stringValue)) {
    result = defaultValue;
  }
  else {
    result =
             stringValue.charAt(0) == 'y' || stringValue.charAt(0) == 'Y' ||
                     stringValue.toString().equalsIgnoreCase("true");
  }
  return result;
}
//--------------------------------------------------------------------------------------------------
/**
 * Converts a string value to its equivalent BigDecimal value. If the string is not a valid numeric
 * value then BigDecimal.ZERO is returned. If there are commas in the value then they will be
 * removed before converting to a decimal (this makes this non-portable to some locations).
 * @param stringValue The string that will be converted.
 * @return the BigDecimal value or BigDecimal.ZERO if the value is not valid.
 */
public static BigDecimal stringToDec(final String stringValue) {
  return stringToDec(stringValue, BigDecimal.ZERO);
} // stringToDec()
//--------------------------------------------------------------------------------------------------
/**
 * Converts a string value to its equivalent BigDecimal value. If the string is not a valid numeric
 * value then the default value is returned. If there are commas in the value then they will be
 * removed before converting to a decimal (this makes this non-portable to some locations).
 * @param stringValue The string that will be converted.
 * @param defaultValue The value that will be returned if the string is invalid.
 * @return the BigDecimal value (using the BigDecimal constructor or the defaultValue if the string
 * is not a numeric value).
 */
public static BigDecimal stringToDec(final CharSequence stringValue, final BigDecimal defaultValue) {
  BigDecimal result;
  try {
    result = new BigDecimal(stringToNumericAdjust(stringValue, false));
  }
  catch (final Exception e) {
    result = defaultValue;
  }
  return result;
} // stringToDec()
//--------------------------------------------------------------------------------------------------
/**
 * Converts a string value to its equivalent double value. If the string is not a valid numeric
 * value then zero is returned. If there are commas in the value then they will be removed before
 * converting to a double (this makes this non-portable to some locations).
 * @param stringValue The string that will be converted.
 * @return The double value.
 */
public static double stringToDouble(final CharSequence stringValue) {
  return stringToDouble(stringValue, 0);
} // stringToDouble()
//--------------------------------------------------------------------------------------------------
/**
 * Converts a string value to its equivalent double value. If the string is not a valid numeric
 * value then the default value is returned. If there are commas in the value then they will be
 * removed before converting to a double (this makes this non-portable to some locations).
 * @param stringValue The string that will be converted.
 * @param defaultValue The value that will be returned if the string is invalid.
 * @return The double value.
 */
public static double stringToDouble(final CharSequence stringValue, final double defaultValue) {
  double result;
  try {
    result = Double.parseDouble(stringToNumericAdjust(stringValue, false));
  }
  catch (final Exception e) {
    result = defaultValue;
  }
  return result;
} // stringToDouble()
//--------------------------------------------------------------------------------------------------
/**
 * Converts a string value to its equivalent int value. If the string is not a valid int value then
 * zero is returned. If there are commas in the value then they will be removed before converting to
 * an integer (this makes this non-portable to some locations).
 * @param stringValue The string that will be converted.
 * @return The int value or zero if the value is not valid.
 */
public static int stringToInt(final CharSequence stringValue) {
  return stringToInt(stringValue, 0);
} // stringToInt()
//--------------------------------------------------------------------------------------------------
/**
 * Converts a string value to its equivalent int value. If the string is not a valid int value then
 * the default value is returned. If there are commas in the value then they will be removed before
 * converting to an integer (this makes this non-portable to some locations).
 * @param stringValue The string that will be converted.
 * @param defaultValue The value that will be returned if the string is invalid.
 * @return The int value (using Integer.parseInt or the defaultValue if the string is not an int
 * value).
 */
public static int stringToInt(final CharSequence stringValue, final long defaultValue) {
  int result;
  try {
    result = Integer.parseInt(stringToNumericAdjust(stringValue, true));
  }
  catch (final Exception e) {
    result = (int)defaultValue;
  }
  return result;
} // stringToInt()
//--------------------------------------------------------------------------------------------------
/**
 * Converts a string value to its equivalent long value. If the string is not a valid long value
 * then zero is returned. If there are commas in the value then they will be removed before
 * converting to a long (this makes this non-portable to some locations).
 * @param stringValue The string that will be converted.
 * @return The long value or zero if the value is not valid.
 */
public static long stringToLong(final CharSequence stringValue) {
  return stringToLong(stringValue, 0);
} // stringToLong()
//--------------------------------------------------------------------------------------------------
/**
 * Converts a string value to its equivalent long value. If the string is not a valid long value
 * then the default value is returned. If there are commas in the value then they will be removed
 * before converting to a long (this makes this non-portable to some locations).
 * @param stringValue The string that will be converted.
 * @param defaultValue The value that will be returned if the string is invalid.
 * @return The long value (using <code>Long.parseLong</code> or the defaultValue if the string is
 * not a valid <code>long</code> value).
 */
public static long stringToLong(final CharSequence stringValue, final long defaultValue) {
  long result;
  try {
    result = Long.parseLong(stringToNumericAdjust(stringValue, true));
  }
  catch (final Exception e) {
    result = defaultValue;
  }
  return result;
} // stringToLong()
//--------------------------------------------------------------------------------------------------
private static String stringToNumericAdjust(final CharSequence stringValue,
                                            final boolean removeDecimals) {
  String result = stringValue.toString();
  if (result.indexOf(',') > 0) {
    result = result.replace(",", "");
  }
  if (result.length() > 0 && (result.charAt(0) == ' ' || result.charAt(result.length() - 1) == ' ')) {
    result = result.trim();
  }
  if (result.endsWith("-") && result.length() > 1) {
    result = "-" + result.substring(0, result.length() - 1);
  }
  if (result.startsWith("+") && result.length() > 1) {
    result = result.substring(1);
  }
  if (result.length() > 0 && (result.charAt(0) == ' ' || result.charAt(result.length() - 1) == ' ')) {
    result = result.trim();
  }
  if (removeDecimals) {
    final int dotIndex = result.indexOf('.');
    boolean validDecimal = true;
    if (dotIndex >= 0 && result.length() > dotIndex + 1) {
      final String decimal = result.substring(dotIndex + 1);
      try {
        Long.parseLong(decimal);
      }
      catch (final Exception e) {
        validDecimal = false;
      }
    }
    if (validDecimal) {
      if (dotIndex == 0) {
        result = "0";
      }
      else if (dotIndex > 0) {
        result = result.substring(0, dotIndex);
      }
    }
  }
  return result;
}
//--------------------------------------------------------------------------------------------------
}