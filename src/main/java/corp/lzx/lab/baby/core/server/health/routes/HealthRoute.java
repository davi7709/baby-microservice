package corp.lzx.lab.baby.core.server.health.routes;

import corp.lzx.lab.baby.core.server.health.HealthMessage;
import corp.lzx.lab.baby.core.service.JsonSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.get;
import static spark.Spark.path;

public class HealthRoute {

  private static final Logger logger = LoggerFactory.getLogger(HealthRoute.class);

  public static void handle() {
    path("/health", () -> {
      get("",  (req, res) -> new HealthMessage("OK"), JsonSerializer::toJson);
    });
  }
}
