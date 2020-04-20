package za.co.mwongho.span.domain;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class Standing implements Serializable {
    private static Logger logger = LoggerFactory.getLogger(Standing.class);
    private Map<String, TeamStanding> standings;


    public void addMatchResult(MatchResult matchResult){
        if(standings == null){
            standings = new HashMap();
        }

        if(!matchResult.getWinningTeam().isPresent()) {
            Match match = matchResult.getMatch();
            addTeamResult(match.getHomeTeam(), 1);
            addTeamResult(match.getAwayTeam(), 1);
        } else {
            addTeamResult(matchResult.getWinningTeam().get(), 3);
            addTeamResult(matchResult.getLoosingTeam().get(), 0);
        }
    }

    private void addTeamResult(Team team, int points){

        if(!standings.containsKey(team.getName())){
            standings.put(team.getName(), new TeamStanding(team.getName(), points));
        } else {
            TeamStanding currentTeamStanding = this.standings.get(team.getName());
            currentTeamStanding.addPoints(points);
            standings.replace(team.getName(), currentTeamStanding);
        }
    }

    public List<TeamStanding> getTeamStandings() {
        return this.standings.values().stream().collect(Collectors.toList());
    }
}
