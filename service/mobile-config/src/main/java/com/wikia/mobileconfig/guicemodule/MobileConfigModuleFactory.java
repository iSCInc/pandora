package com.wikia.mobileconfig.guicemodule;

import com.wikia.mobileconfig.exceptions.MobileConfigModuleNotSetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MobileConfigModuleFactory {

  public static final String MOBILE_CONFIG_SERVICE_ENVIRONMENT = "MOBILE_CONFIG_SERVICE_ENVIRONMENT";
  public static final String STAGE_PROD = "prod";
  public static final String STAGE_QA = "qa";
  public static final String STAGE_TESTING = "testing";
  private static final Logger LOGGER = LoggerFactory.getLogger(MobileConfigModuleFactory.class);

  public static MobileConfigModuleBase CreateMobileConfigModule() {
    MobileConfigModuleBase module;
    String stage = System.getenv(MOBILE_CONFIG_SERVICE_ENVIRONMENT);

    switch (stage) {
      case STAGE_PROD:
        module = new ProdMobileConfigModule();
        break;
      case STAGE_QA:
        module = new QaMobileConfigModule();
        break;
      case STAGE_TESTING:
        module = new ProdMobileConfigModule();
        break;
      default:
        MobileConfigModuleNotSetException cause = new MobileConfigModuleNotSetException(stage);
        LOGGER.error("MOBILE_CONFIG_SERVICE_ENVIRONMENT not set", cause);
        throw new RuntimeException(cause);
    }

    return module;
  }
}

