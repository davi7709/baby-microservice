package corp.lzx.lab.baby.domain.product.routes;

import corp.lzx.lab.baby.core.service.JsonSerializer;
import corp.lzx.lab.baby.core.util.Message;
import corp.lzx.lab.baby.domain.product.Product;
import corp.lzx.lab.baby.domain.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static corp.lzx.lab.baby.core.service.JsonSerializer.fromJson;
import static corp.lzx.lab.baby.core.service.JsonSerializer.toJson;
import static spark.Spark.*;

public class ProductRoute {

  private static final Logger logger = LoggerFactory.getLogger(ProductRoute.class);
  public static final ProductService service = new ProductService();

  public static void handle() {


    path("/product", () -> {

      get("", (req, res) -> service.products(), JsonSerializer::toJson);

      get("/:id", (req, res) -> service.product(req.params(":id")), JsonSerializer::toJson);

      //post("", (req, res) -> service.createProduct(fromJson(req.body(), Product.class)), JsonSerializer::toJson);

      post("", (req, res) -> {
        try{
          return service.createProduct(fromJson(req.body(), Product.class));
        } catch(Exception e){
          res.status(500);
          return toJson(new Message(e.getMessage()));
        }
      }, JsonSerializer::toJson);

      put("/:id", (req, res) -> service.updateProduct(req.params(":id"),fromJson(req.body(), Product.class)), JsonSerializer::toJson);

      delete("/:id", (req, res) -> service.deleteProduct(req.params(":id")), JsonSerializer::toJson);

    });

    path("/products", () ->{
      get("", (req, res) -> service.products(), JsonSerializer::toJson);
    });
  }
}
