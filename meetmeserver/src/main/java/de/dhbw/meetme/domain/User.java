package de.dhbw.meetme.domain;


import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Felix:
 * User inherits PersistentObject as one of the main Objects for the database.
 * The constructor comes from PersistentObject and just creates a UUID and the object "User" itself.
 *
 *
 * ToDo:
 *      -Create new attributes for other fields in the database, like email etc.
 *      -Add new Getter/Setter methods
 *      -Probably change "name" to "username"
 *
 */

@Entity // also add to persistence.xml !!
@XmlRootElement // needed for REST JSON marshalling
public class User extends PersistentObject {

    @NotNull
    @Size(max=20)
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "User{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        '}';
  }
}
