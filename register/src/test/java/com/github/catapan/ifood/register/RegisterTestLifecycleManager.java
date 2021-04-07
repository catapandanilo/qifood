package com.github.catapan.ifood.register;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import java.util.HashMap;
import java.util.Map;
import org.testcontainers.containers.PostgreSQLContainer;

public class RegisterTestLifecycleManager implements QuarkusTestResourceLifecycleManager {

  public static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:12.2");

  @Override
  public Map<String, String> start() {
    POSTGRES.start();
    Map<String, String> properties = new HashMap<String, String>();

    properties.put("quarkus.datasource.jdbc.url", POSTGRES.getJdbcUrl());
    properties.put("quarkus.datasource.username", POSTGRES.getUsername());
    properties.put("quarkus.datasource.password", POSTGRES.getPassword());

    return properties;
  }

  @Override
  public void stop() {
    if (POSTGRES.isRunning()) {
      POSTGRES.stop();
    }
  }
}
