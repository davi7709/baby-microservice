package corp.lzx.lab.baby.domain.product.service;

import corp.lzx.lab.baby.domain.product.Product;
import corp.lzx.lab.baby.domain.product.data.ProductQueries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.route.ServletRoutes;

import java.util.List;

public class ProductService {

  private final Logger logger = LoggerFactory.getLogger(ProductService.class);
  private final ProductQueries queries = new ProductQueries();

  public List<Product> products() {
    return queries.findAll();
  }

  public Product product(String id) {
    // @TODO: sanitize request param first
    return product(Long.valueOf(id));
  }

  public Product product(Long id) {
    return queries.findById(id);
  }


  public Product createProduct(Product product) throws IllegalStateException {
    if(queries.findByDescription(product.description()) != null){
      throw new IllegalStateException("Product already exists");
    }else{
      return queries.insertProduct(product);
    }
  }

  public Product createProduct(String id, Product product){
    return createProduct(product);
  }


  public Product updateProduct(Long id, Product product){
    if(product(id) != null){
      return queries.updateProduct(id, product);
    }else{
      return queries.insertProduct(product);
    }
  }

  public Product updateProduct(String id, Product product){
    return updateProduct(Long.valueOf(id), product);
  }

  public boolean deleteProduct(String id){
    return deleteProduct(Long.valueOf(id));
  }

  public boolean deleteProduct(Long id){
    return queries.deleteProduct(id);
  }

}

