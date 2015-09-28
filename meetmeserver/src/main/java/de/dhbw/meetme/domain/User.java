package de.dhbw.meetme.domain;


import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Felix:
 * User inherits PersistentObject as one of the main Objects for the database.
 * The constructor comes from PersistentObject and just creates a UUID and the object "User" itself.
 * Maybe the contructor of "User" should be updated to
 *          "User(String name):
 *                      super();
 *                      setName(name);"
 *
 *
 *
 *
 * ToDo:
 *      -Create new attributes for other fields in the database, like email etc.
 *      -Add new Getter/Setter methods
 *      -Probably change "name" to "username"
 *      - need to add the @Size and @NotNull annotations to the other variables
 *
 * DONE:
 *      -new variables added to the User class to represent the designed database -> unchecked if it works properly with all JPA functionality
 */


@Entity
@XmlRootElement // needed for REST JSON marshalling
public class User extends PersistentObject {

  /** --------------------VARIABLES--------------*/

    @NotNull
    @Size(max=20)
    private String name;
    private String email;
    private String team;
    private String password;
    private boolean active; //true if user is playing the game; false if not
    private int rank;    //Leaderboard rank

  /**----------------------------------------------------------*/

    /** Getter ----------------------- Setter **/

    public String getTeam() {return team;}

    public void setTeam(String team) {this.team = team;}

    public int getRank() {return rank;}

    public void setRank(int rank) {this.rank = rank;}

    public boolean isActive() {return active;}

    public void setActive(boolean active) {this.active = active;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

  //This method sets all values to the variables of one User Object
    public void setAllAttributes(String team, int rank, boolean active, String password, String email, String name){
      setActive(active);
      setName(name);
      setEmail(email);
      setPassword(password);
      setRank(rank);
      setTeam(team);
    }
    /**-------------------------------**/

  @Override
  public String toString() {
    return "User{" +
        "id='" + id + '\'' +   //", name='" + name + '\''
    '}';
  }
}
