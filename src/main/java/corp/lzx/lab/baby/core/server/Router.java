package corp.lzx.lab.baby.core.server;

import corp.lzx.lab.baby.core.server.health.routes.HealthRoute;
import corp.lzx.lab.baby.core.service.JsonSerializer;
import corp.lzx.lab.baby.domain.person.routes.PersonRoute;
import corp.lzx.lab.baby.domain.product.routes.ProductRoute;
import corp.lzx.lab.baby.core.util.Message;
import corp.lzx.lab.baby.domain.product.Product;
import corp.lzx.lab.baby.domain.product.routes.ProductRoute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static corp.lzx.lab.baby.core.service.JsonSerializer.fromJson;
import static corp.lzx.lab.baby.core.service.JsonSerializer.toJson;
import static corp.lzx.lab.baby.domain.product.routes.ProductRoute.service;
import static spark.Spark.*;

public class Router {
  private static final Logger logger = LoggerFactory.getLogger(Router.class);
  public static void route() {
    staticFiles.location("/public");

    before((req, res) -> {
      logger.info(" ==> request: {} {}", req.requestMethod(), req.pathInfo());
    });

    path("/api", () -> {
      /// additional routes
      HealthRoute.handle();
      ProductRoute.handle();
      ProductRoute.handle();
      PersonRoute.handle();
      PersonRoute.handle();
    });

    after((req, res) -> {
      res.type("application/json");
      if(res.status() == 200 && (res.body() == null || res.body().equals("null"))) {
        res.status(404);
        res.body(toJson(new Message("resource not found")));
      }
      logger.info(res.body());
      logger.info(" <== response: {}:{}", req.userAgent(), req.raw().getRemotePort());
    });
  }
}
