package corp.lzx.lab.baby.core.server;

import corp.lzx.lab.baby.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

public final class Bootstrap {
  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  public static void boot() {
    Spark.port(4567);
    Spark.threadPool(Runtime.getRuntime().availableProcessors());
    Router.route();
    Spark.awaitInitialization();
  }
}
