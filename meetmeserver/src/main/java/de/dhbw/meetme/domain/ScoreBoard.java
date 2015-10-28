package de.dhbw.meetme.domain;


import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

import de.dhbw.meetme.domain.User;


@Entity
@XmlRootElement
public class ScoreBoard extends PersistentObject {

    private String username;
    private int score;
    private ScoreBoard scoreBoard;

    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }

    public void setScoreBoard(GeoData geoData) {
        this.scoreBoard = scoreBoard;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "GeoData{" + "username" + getUsername() + "Score='" + getScore() + '\'' +
                '}';
    }


}
