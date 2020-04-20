package za.co.mwongho.span.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class TeamStanding implements Serializable {

    private String name;
    private int points = 0;

    public TeamStanding(String name, int points) {
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int additionalPoints) {
        this.points = this.points + additionalPoints;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
