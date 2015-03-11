package com.wikia.dropwizard.consul.bundle;

import com.orbitz.consul.Consul;
import com.orbitz.consul.NotRegisteredException;

public class ConsulHeartbeat {

  private final Consul consul;
  private final String heartbeatId;


  ConsulHeartbeat(Consul consul, String heartbeatId) {
    this.consul = consul;
    this.heartbeatId = heartbeatId;
  }

  public void send() {
    try {
      consul.agentClient().pass(heartbeatId);
    } catch (NotRegisteredException e) {
      throw new RuntimeException(e);
    }
  }
}