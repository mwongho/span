package za.co.mwongho.span.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Team implements Serializable {

    private String name;
    private int score = 0;

    public Team(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
