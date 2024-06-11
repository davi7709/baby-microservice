package corp.lzx.lab.baby.core.service;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseService {

  private final Logger logger = LoggerFactory.getLogger(DatabaseService.class);
  private static DatabaseService instance = new DatabaseService();
  private final DataSource dataSource;

  private DatabaseService() {
    var config = new HikariConfig();
    //config.setJdbcUrl("jdbc:postgresql://postgres.lzx.corp:5432/test");
    config.setJdbcUrl("jdbc:h2:mem:test");
    config.setUsername("postgres");
    config.setPassword("postgres");
    config.setAutoCommit(true);
    dataSource = new HikariDataSource(config);

    try(Connection connection = dataSource.getConnection()) {
      try(Statement statement = connection.createStatement()) {
        if(connection.getMetaData().getDatabaseProductName().equals("PostgreSQL")) {
          statement.execute(
            """
                CREATE SEQUENCE IF NOT EXISTS seq_person_id;
                CREATE TABLE IF NOT EXISTS tb_person (
                  id bigint NOT NULL PRIMARY KEY DEFAULT nextval('seq_person_id'),
                  name varchar(255) NOT NULL

                );

                CREATE SEQUENCE IF NOT EXISTS seq_product_id;
                CREATE TABLE IF NOT EXISTS tb_product (
                  id bigint NOT NULL PRIMARY KEY DEFAULT nextval('seq_product_id'),
                  description varchar(255) NOT NULL,
                  quantity int NOT NULL,
                  price numeric(20,2) NOT NULL
                );

                truncate table tb_person;
                truncate table tb_product;

                INSERT INTO tb_person(name) values ('Davi Alves');
                INSERT INTO tb_product(description,quantity, price) values ('iPhone 15 Pro Max', 10, 7000.00);
                """
          );
        }

        if(connection.getMetaData().getDatabaseProductName().equals("H2")) {
          statement.execute(
            """
                CREATE TABLE IF NOT EXISTS tb_person (
                  id bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
                  name varchar(255) NOT NULL
                );

                CREATE TABLE IF NOT EXISTS tb_product (
                  id bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
                  description varchar(255) NOT NULL,
                  quantity int NOT NULL,
                  price numeric(20,2) NOT NULL
                );

                truncate table tb_person;
                //truncate table tb_product;

                INSERT INTO tb_person(name) values ('Davi Alves');
                //INSERT INTO tb_product(description,quantity, price) values ('iPhone 15 Pro Max', 10, 7000.00);
                """
          );
        }

      }
    } catch(SQLException e) {
      logger.error(e.getMessage());
    }
  }

  private static synchronized DatabaseService instance() {
    if (instance == null) {
      instance = new DatabaseService();
    }
    return instance;
  }

  public static DataSource getDataSource() {
    return instance().dataSource;
  }


}
