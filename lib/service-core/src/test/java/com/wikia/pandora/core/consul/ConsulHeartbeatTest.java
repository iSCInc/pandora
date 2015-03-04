package com.wikia.pandora.core.consul;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.NotRegisteredException;
import org.junit.Test;

public class ConsulHeartbeatTest {

  @Test
  public void heartbeatCallsAgentPass() throws NotRegisteredException {
    Consul consul = mock(Consul.class);
    AgentClient agentClient = mock(AgentClient.class);
    when(consul.agentClient()).thenReturn(agentClient);

    ConsulHeartbeat heartbeat = new ConsulHeartbeat(consul, "id");
    heartbeat.send();

    verify(agentClient, times(1)).pass(any());
  }
}
