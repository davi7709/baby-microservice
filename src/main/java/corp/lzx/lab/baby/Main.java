package corp.lzx.lab.baby;

import corp.lzx.lab.baby.core.server.Bootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  public static void main(String ... args) {
    Bootstrap.boot();
    logger.info("application started");
  }
}
