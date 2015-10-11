package de.dhbw.meetme.database.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 */
public class DbTestUtil {
  protected EntityManagerFactory emFactory;
  protected EntityManager em;
  protected Connection connection;

  def setup() {
    initDb()
  }

  def initDb() {
    Class.forName("org.h2.Driver");
    connection = DriverManager.getConnection("jdbc:h2:mem:unit-testing-jpa", "sa", ""); //In Memory Datenbak, die im RAM liegt
    emFactory = Persistence.createEntityManagerFactory("testPU");
    createEntityManager()
  }

  def createEntityManager() {
    em = emFactory.createEntityManager();
  }

  def cleanup() {
     closeDb()
  }

  def closeDb() {
    if (em != null) {
      em.close();
    }
    if (emFactory != null) {
      emFactory.close();
    }
    connection.createStatement().execute("SHUTDOWN");
  }


}
