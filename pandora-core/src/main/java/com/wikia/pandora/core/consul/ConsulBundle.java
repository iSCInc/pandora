package com.wikia.pandora.core.consul;

import com.codahale.metrics.health.HealthCheck;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;

import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public abstract class ConsulBundle<T> implements ConfiguredBundle<T> {

  final static Logger logger = LoggerFactory.getLogger(ConsulBundle.class);

  abstract protected ConsulConfig narrowConfig(T config);

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


  protected void scheduleHeartbeat(Environment environment, ConsulConfig config,
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
  public void run(T appConfiguration, Environment environment) throws Exception {
    ConsulConfig configuration = narrowConfig(appConfiguration);
    ConsulWrapper consul = new ConsulWrapper(configuration, environment.getName());
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

                  // scheduling heartbeat
                  String serviceId = consul.getServiceId(connector);
                  ConsulHeartbeat
                      heartbeat =
                      new ConsulHeartbeat(consul.getConnector(), serviceId);
                  logger.info("Scheduling Consul heartbeats");
                  scheduleHeartbeat(environment, configuration, heartbeat::send, true);
                })
        );
  }

  @Override
  public void initialize(Bootstrap<?> bootstrap) {
  }
}
