package corp.lzx.lab.baby.domain.person.data;

import corp.lzx.lab.baby.core.service.DatabaseService;
import corp.lzx.lab.baby.domain.person.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonQueries {

  private final Logger logger = LoggerFactory.getLogger(PersonQueries.class);
  private final DataSource dataSource = DatabaseService.getDataSource();

  public List<Person> findAll() {
    List<Person> persons = new ArrayList<>();
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement("select * from tb_person")) {
        try (ResultSet resultSet = statement.executeQuery()) {
          while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            int registry = resultSet.getInt("registry");
            String password = resultSet.getString("password");
            persons.add(new Person(id, name, registry, password));
          }
        }
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return persons;
  }

  public Person findById(Long id) {
    Person person = null;
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement("select * from tb_person where id = ?")) {
        statement.setLong(1, id);
        try (ResultSet resultSet = statement.executeQuery()) {
          while (resultSet.next()) {
            //Long id = resultSet.getLong("id_product");
            String name = resultSet.getString("name");
            int registry = resultSet.getInt("registry");
            String password = resultSet.getString("password");
            person = new Person(id, name, registry, password);
          }
        }
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return person;
  }

  public String findByName(String name) {
    String personName = null;
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement("select * from tb_person where name = ?")) {
        statement.setString(1, name);
        try (ResultSet resultSet = statement.executeQuery()) {
          while (resultSet.next()) {
            personName = resultSet.getString("name");
          }
        }
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return personName;
  }

  public Person insertPerson(Person person) {
    Long newId = null;
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement("insert into tb_person (name, registry, password) values (?, ?, ?)",Statement.RETURN_GENERATED_KEYS)) {
        statement.setString(1, person.name());
        statement.setInt(2, person.registry());
        statement.setString(3, person.password());
        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
          throw new SQLException("Creating person failed, no rows affected.");
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

  public Person updatePerson(Long id, Person person) {

    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement("update tb_person set name = ?, registry = ?, password = ? where id = ?")) {
        statement.setString(1, person.name());
        statement.setInt(2, person.registry());
        statement.setString(3, person.password());
        statement.setLong(4, id);

        statement.executeUpdate();
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return findById(id);
  }

  public boolean deletePerson(Long id) {
    boolean rowDeleted = false;
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement("DELETE FROM tb_person WHERE id = ?")) {
        statement.setLong(1, id);

        rowDeleted = statement.executeUpdate() > 0;
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return rowDeleted;
  }
}
