package com.wikia.pandora;

import com.codahale.metrics.health.HealthCheck;

/**
 * Created by damonsnyder on 1/8/15.
 */
public class PandoraHealthCheck extends HealthCheck {

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}

