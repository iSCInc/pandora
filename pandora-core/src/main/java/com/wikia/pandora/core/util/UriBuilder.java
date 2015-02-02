package com.wikia.pandora.core.util;

import java.lang.reflect.Method;

public class UriBuilder {


  /* This is a hack. Waiting for opinion. I will polish it if this will be accepted.
   *
   * If we do not use this, getSelfUriBuilder will be replaced by:
   * 
   * uri = javax.ws.rs.core.UriBuilder.fromResource(ArticleResource.Class)
   *      .path(ArticleResource.Class.getMethod("NameOfTheMethod", String.Class, String.Class))
   *      .build(wikia, title)
   *      .getPath();
   * 
   * We can also make a live template in InteliJ, that will filling it for us.
  */


  public static javax.ws.rs.core.UriBuilder getSelfUriBuilder(Object... path) {
    javax.ws.rs.core.UriBuilder uri = null;
    Class type = null;
    Method method = null;

    StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

    String className = stackTraceElements[2].getClassName();
    String methodName = stackTraceElements[2].getMethodName();

    try {
      type = Class.forName(className);
      Class[] parameterTypes = new Class[path.length];
      for (int i = 0; i < path.length; i++) {
        parameterTypes[i] = path[i].getClass();
      }

      method = type.getMethod(methodName, parameterTypes);

    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }
    try {
      uri = javax.ws.rs.core.UriBuilder.fromResource(type)
          .path(method);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return uri;
  }


}
