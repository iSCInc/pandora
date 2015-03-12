package com.wikia.notificationsservice;

import com.google.inject.AbstractModule;
import com.wikia.dropwizard.consul.bundle.ConsulModule;
import com.wikia.dropwizard.consul.bundle.ProvidesConsulConfiguration;
import com.wikia.notificationsservice.configuration.NotificationsConfiguration;

public class NotificationsModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(ProvidesConsulConfiguration.class)
            .to(NotificationsConfiguration.class);
    binder().install(new ConsulModule());

  }
}
