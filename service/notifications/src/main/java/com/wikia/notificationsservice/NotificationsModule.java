package com.wikia.notificationsservice;

import com.google.inject.AbstractModule;
import com.wikia.notificationsservice.configuration.NotificationsConfiguration;
import com.wikia.pandora.core.consul.ConsulModule;
import com.wikia.pandora.core.consul.ProvidesConsulConfiguration;

public class NotificationsModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(ProvidesConsulConfiguration.class)
            .to(NotificationsConfiguration.class);
    binder().install(new ConsulModule());

  }
}
