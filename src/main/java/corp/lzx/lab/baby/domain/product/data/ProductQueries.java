package corp.lzx.lab.baby.domain.product.data;

import corp.lzx.lab.baby.core.service.DatabaseService;
import corp.lzx.lab.baby.domain.product.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductQueries {

  private final Logger logger = LoggerFactory.getLogger(ProductQueries.class);
  private final DataSource dataSource = DatabaseService.getDataSource();

  public List<Product> findAll() {
    List<Product> products = new ArrayList<>();
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement("select * from tb_product")) {
        try (ResultSet resultSet = statement.executeQuery()) {
          while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            String description = resultSet.getString("description");
            int current_quantity = resultSet.getInt("quantity");
            BigDecimal price = resultSet.getBigDecimal("price");
            products.add(new Product(id, description, current_quantity, price));
          }
        }
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return products;
  }

  public Product findById(Long id) {
    Product product = null;
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement("select * from tb_product where id = ?")) {
        statement.setLong(1, id);
        try (ResultSet resultSet = statement.executeQuery()) {
          while (resultSet.next()) {
            //Long id = resultSet.getLong("id_product");
            String description = resultSet.getString("description");
            int current_quantity = resultSet.getInt("quantity");
            BigDecimal price = resultSet.getBigDecimal("price");
            product = new Product(id, description, current_quantity, price);
          }
        }
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return product;
  }

  public String findByDescription(String description) {
    String productDescription = null;
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement("select * from tb_product where description = ?")) {
        statement.setString(1, description);
        try (ResultSet resultSet = statement.executeQuery()) {
          while (resultSet.next()) {
            productDescription = resultSet.getString("description");
          }
        }
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return productDescription;
  }

  public Product insertProduct(Product product) {
    Long newId = null;
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement("insert into tb_product (description, quantity, price) values (?, ?, ?)",Statement.RETURN_GENERATED_KEYS)) {
        statement.setString(1, product.description());
        statement.setInt(2, product.quantity());
        statement.setBigDecimal(3, product.price());
        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
          throw new SQLException("Creating product failed, no rows affected.");
        }
        try (ResultSet resultSet = statement.getGeneratedKeys()) {
          while (resultSet.next()) {
            newId = resultSet.getLong("id");
          }
        }
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return findById(newId);
  }

  public Product updateProduct(Long id, Product product) {

    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement("update tb_product set description = ?, quantity = ?, price = ? where id = ?")) {
        statement.setString(1, product.description());
        statement.setInt(2, product.quantity());
        statement.setBigDecimal(3, product.price());
        statement.setLong(4, id);

        statement.executeUpdate();
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return findById(id);
  }

  public boolean deleteProduct(Long id) {
    boolean rowDeleted = false;
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement("DELETE FROM tb_product WHERE id = ?")) {
        statement.setLong(1, id);

        rowDeleted = statement.executeUpdate() > 0;
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return rowDeleted;
  }
}
