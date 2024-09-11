package corp.lzx.lab.baby.domain.person.routes;

import corp.lzx.lab.baby.core.service.JsonSerializer;
import corp.lzx.lab.baby.core.util.Message;
import corp.lzx.lab.baby.domain.person.Person;
import corp.lzx.lab.baby.domain.person.service.PersonService;
import corp.lzx.lab.baby.domain.product.Product;
import corp.lzx.lab.baby.domain.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static corp.lzx.lab.baby.core.service.JsonSerializer.fromJson;
import static corp.lzx.lab.baby.core.service.JsonSerializer.toJson;
import static spark.Spark.*;

public class PersonRoute {

  private static final Logger logger = LoggerFactory.getLogger(PersonRoute.class);
  public static final PersonService service = new PersonService();

  public static void handle() {


    path("/person", () -> {

      get("", (req, res) -> service.persons(), JsonSerializer::toJson);

      get("/:id", (req, res) -> service.person(req.params(":id")), JsonSerializer::toJson);

      //post("", (req, res) -> service.createPerson(fromJson(req.body(), Product.class)), JsonSerializer::toJson);

      post("", (req, res) -> {
        try{
          return service.createPerson(fromJson(req.body(), Person.class));
        } catch(Exception e){
          res.status(500);
          return toJson(new Message(e.getMessage()));
        }
      }, JsonSerializer::toJson);

      put("/:id", (req, res) -> service.updatePerson(req.params(":id"),fromJson(req.body(), Person.class)), JsonSerializer::toJson);

      delete("/:id", (req, res) -> service.deletePerson(req.params(":id")), JsonSerializer::toJson);

    });

    path("/persons", () ->{
      get("", (req, res) -> service.persons(), JsonSerializer::toJson);
    });
  }
}
