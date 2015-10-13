package de.dhbw.meetme.domain;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 */

@Entity
@XmlRootElement // needed for REST JSON marshalling
public class User extends PersistentObject {

    /**
     * --------------------VARIABLES----------------------------
     */

    private String username;
    private String lastname;
    private String firstname;
    private String sLocation;
    private String meetmecode;


    private String email;
    private String team;
    private Integer password;
    private boolean active; //true if user is playing the game; false if not
    private int rank;    //Leaderboard rank
    private String gender;

    /**
     * ----------------------------------------------------------
     */


    @OneToOne
    private GeoData geoData;
    @OneToOne
    private ScoreBoard scoreBoard;

    public GeoData getGeoData() {
        return geoData;
    }

    public void setGeoData(GeoData geoData) {
        this.geoData = geoData;
    }

    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }

    public void setScoreBoard(ScoreBoard scoreBoard) {
        this.scoreBoard = scoreBoard;
    }


    /**
     * -------------------Getter/Setter-----------------
     */


    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Integer getPassword() {
        return password;
    }

    public void setPassword(Integer password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getsLocation() {
        return sLocation;
    }

    public void setsLocation(String sLocation) {
        this.sLocation = sLocation;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMeetmecode() {
        return meetmecode;
    }

    public void setMeetmecode(String meetmecode) {
        this.meetmecode = meetmecode;
    }

    //This method sets all values to the variables of one User Object
    public void setAllAttributes(String team, int rank, boolean active, Integer password, String email, String username, String firstname, String lastname, String sLocation, String gender, String meetmecode) {
        setActive(active);
        setTeam(team);
        setRank(rank);
        setUsername(username);
        setPassword(password);
        setFirstname(firstname);
        setLastname(lastname);
        setEmail(email);
        setsLocation(sLocation);
        setGender(gender);
        setMeetmecode(meetmecode);
    }

    /**
     * ----------------------------------------------------------
     */


    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' + ", Username='" + username + '\'' +
                '}';
    }
}
