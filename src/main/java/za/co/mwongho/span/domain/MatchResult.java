package za.co.mwongho.span.domain;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Optional;

@Data
public class MatchResult implements Serializable {
    private static Logger logger = LoggerFactory.getLogger(MatchResult.class);

    private Match match;
    private Optional<Team> winningTeam = Optional.empty();
    private Optional<Team> loosingTeam = Optional.empty();

    public MatchResult(final Match match) {
        setOutcome(match);
    }

    private void setOutcome(final Match match) {
        this.match = match;
        Team homeTeam = match.getHomeTeam();
        Team awayTeam = match.getAwayTeam();

        if(homeTeam.getScore() != awayTeam.getScore()) {
            if(homeTeam.getScore() > awayTeam.getScore()){
                winningTeam = Optional.ofNullable(homeTeam);
                loosingTeam = Optional.ofNullable(awayTeam);
            } else {
                winningTeam = Optional.ofNullable(awayTeam);
                loosingTeam = Optional.ofNullable(homeTeam);
            }
        }

    }

    public Match getMatch() {
        return match;
    }

    public Optional<Team> getWinningTeam() {
        return winningTeam;
    }

    public Optional<Team> getLoosingTeam() {
        return loosingTeam;
    }
}
