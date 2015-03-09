package com.wikia.pandora.core.consul;

import com.orbitz.consul.Consul;
import com.orbitz.consul.model.agent.Registration;

import org.eclipse.jetty.server.ServerConnector;

import javax.annotation.Nonnull;

import io.dropwizard.util.Duration;

public class ConsulWrapper {

  private final ConsulConfiguration configuration;
  private final String applicationName;

  ConsulWrapper(@Nonnull ConsulConfiguration configuration, @Nonnull String appName) {
    this.configuration = configuration;
    this.applicationName = appName;
  }

  public static String formatDurationToMilliseconds(Duration duration) {
    // this notation is acceptable to pass to consul
    return String.format("%dms", duration.toMilliseconds());
  }

  public Consul getConnector() {
    return Consul.newClient(this.configuration.getConsulUri().getHost(),
                            configuration.getConsulUri().getPort());
  }

  protected Registration.Check heartbeatTTLCheck() {
    Registration.Check check = new Registration.Check();
    check.setTtl(formatDurationToMilliseconds(this.configuration.getHeartbeatTTL()));
    return check;
  }

  protected String makeServiceName(ServerConnector connector) {
    // 'application' is Dropwizard internal convention of naming default connectors
    if (connector.getName().equals("application")) {
      return applicationName;
    } else {
      return String.format("%s-%s", connector.getName(), applicationName);
    }
  }

  protected String getServiceId(ServerConnector connector) {
    return String
        .format("%s_%d", makeServiceName(connector), connector.getLocalPort());
  }

  public void registerConnector(@Nonnull ServerConnector connector) {
    Consul consul = this.getConnector();

    Registration registrationForm = new Registration();
    registrationForm.setId(getServiceId(connector));
    registrationForm.setName(applicationName);
    registrationForm.setTags(configuration.getTags().stream().toArray(String[]::new));
    registrationForm.setPort(connector.getLocalPort());

    registrationForm.setCheck(heartbeatTTLCheck());

    consul.agentClient().register(registrationForm);
  }
}
