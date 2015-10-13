package de.dhbw.meetme.domain;


import javax.persistence.Entity;
import javax.persistence.OneToOne;
import de.dhbw.meetme.domain.User;



@Entity
public class ScoreBoard extends PersistentObject {

    private int rank;
    private int score;


    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }



}
