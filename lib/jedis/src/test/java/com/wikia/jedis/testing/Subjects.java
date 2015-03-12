package com.wikia.jedis.testing;

import com.codahale.metrics.health.HealthCheck;
import com.google.common.truth.FailureStrategy;
import com.google.common.truth.SubjectFactory;
import com.wikia.jedis.JedisFactory;

public class Subjects {
  public static SubjectFactory<JedisFactorySubject, JedisFactory> jedisFactory() {
    return new SubjectFactory<JedisFactorySubject, JedisFactory>() {
      @Override
      public JedisFactorySubject getSubject(FailureStrategy fs, JedisFactory that) {
        return new JedisFactorySubject(fs, that);
      }
    };
  }

  public static SubjectFactory<HealthCheckResultSubject, HealthCheck.Result> healthCheck() {
    return new SubjectFactory<HealthCheckResultSubject, HealthCheck.Result>() {
      @Override
      public HealthCheckResultSubject getSubject(FailureStrategy fs, HealthCheck.Result that) {
        return new HealthCheckResultSubject(fs, that);
      }
    };
  }
}
