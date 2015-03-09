package com.wikia.pandora.core.consul;

import com.codahale.metrics.health.HealthCheck;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ConsulBundle implements ConfiguredBundle<Configuration> {

  final static Logger logger = LoggerFactory.getLogger(ConsulBundle.class);

  private Provider<ConsulConfiguration> configurationProvider;

  @Inject
  public ConsulBundle(Provider<ConsulConfiguration> configurationProvider) {
    this.configurationProvider = configurationProvider;
  }

  protected boolean allHealtchecksPassed(Environment environment) {
    return environment
        .healthChecks()
        .runHealthChecks()
        .entrySet()
        .stream()
        .map((Map.Entry<String, HealthCheck.Result> entry) -> {
          // log all failed healthchecks
          if (!entry.getValue().isHealthy()) {
            logger.warn("Healthcheck failed", entry.getValue().getError());
          }
          return entry.getValue().isHealthy();
        }).allMatch((val) -> val); // all healthchecks resuts must be true
  }


  protected void scheduleHeartbeat(Environment environment, ConsulConfiguration config,
                                   Runnable heartbeat, Boolean runHealthChecks) {
    environment
        .lifecycle()
        .scheduledExecutorService("consul-heartbeat-scheduler")
        .threads(1)
        .build()
        .scheduleAtFixedRate(
            () -> {
              try {
                if (!runHealthChecks || allHealtchecksPassed(environment)) {
                  heartbeat.run();
                  logger.debug("Heartbeat sent");
                }
              } catch (RuntimeException ex) {
                logger.warn("Heartbeat failed", ex);
              }
            },
            0, // start delay
            config.getHeartbeatInterval().getQuantity(),
            config.getHeartbeatInterval().getUnit());
  }

  @Override
  public void run(Configuration configuration, Environment environment) throws Exception {
    if (this.configurationProvider.get() == null) {
      logger.warn("Missing Consul configuration - skipping Consul initialization");
      return;
    }
    ConsulWrapper
        consul =
        new ConsulWrapper(this.configurationProvider.get(), environment.getName());
    environment
        .lifecycle()
        .addServerLifecycleListener(
            (Server server) -> Arrays.stream(server.getConnectors())
                .filter((c) -> c instanceof ServerConnector)
                .filter((c) -> c.getName().equals("application"))
                .findFirst()
                .map((connector) -> (ServerConnector) connector)
                .ifPresent((connector) -> {
                  logger.info("Registering in consul connector");
                  consul.registerConnector(connector);
                  logger.info("Registered in consul");

                  // scheduling heartbeat
                  String serviceId = consul.getServiceId(connector);
                  ConsulHeartbeat
                      heartbeat =
                      new ConsulHeartbeat(consul.getConnector(), serviceId);
                  logger.info("Scheduling Consul heartbeats");
                  scheduleHeartbeat(environment, this.configurationProvider.get(), heartbeat::send,
                                    true);
                  logger.info("Heartbeats are scheduled");
                })
        );
  }

  @Override
  public void initialize(Bootstrap<?> bootstrap) {

  }
}
