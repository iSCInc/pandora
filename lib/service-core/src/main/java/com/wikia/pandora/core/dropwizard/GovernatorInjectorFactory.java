package com.wikia.pandora.core.dropwizard;

import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Stage;

import com.hubspot.dropwizard.guice.InjectorFactory;
import com.netflix.governator.guice.LifecycleInjector;

import java.util.List;

public class GovernatorInjectorFactory implements InjectorFactory {

  @Override
  public Injector create(final Stage stage, final List<Module> modules) {
    return LifecycleInjector.builder().inStage(stage).withModules(modules).build()
        .createInjector();
  }
}
