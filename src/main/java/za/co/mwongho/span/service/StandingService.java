package za.co.mwongho.span.service;

import za.co.mwongho.span.domain.MatchResult;
import za.co.mwongho.span.domain.Standing;
import za.co.mwongho.span.domain.TeamStanding;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public enum StandingService {

    INSTANCE;

    private final Standing standing;

    private StandingService() {
        this.standing = new Standing();
    }

    public StandingService getInstance() {
        return INSTANCE;
    }

    public void addMatchResult(MatchResult matchResult) {
        this.standing.addMatchResult(matchResult);
    }

    public List<TeamStanding> getOrderedTeamStanding() {
        Comparator<TeamStanding> compareByName = Comparator.comparing(TeamStanding::getName);
        Comparator<TeamStanding> compareByPoints = Comparator.comparing(TeamStanding::getPoints);

        return this.standing.getTeamStandings().stream().sorted(compareByPoints.thenComparing(compareByName.reversed()).reversed()).collect(Collectors.toList());
    }

}
