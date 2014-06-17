package org.greatlogic.gxttestbed.client.glgwt;

@SuppressWarnings("serial")
public class GLInvalidFieldOrColumnException extends Exception {
//--------------------------------------------------------------------------------------------------
public GLInvalidFieldOrColumnException(final String fieldName) {
  super(fieldName);
}
//--------------------------------------------------------------------------------------------------
}