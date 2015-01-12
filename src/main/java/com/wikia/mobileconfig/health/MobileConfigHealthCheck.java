package com.wikia.mobileconfig.health;

import com.codahale.metrics.health.HealthCheck;

public class MobileConfigHealthCheck extends HealthCheck {
    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
