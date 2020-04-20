package za.co.mwongho.span.batch;

import org.jeasy.batch.core.mapper.RecordMapper;
import org.jeasy.batch.core.record.GenericRecord;
import org.jeasy.batch.core.record.Record;
import org.jeasy.batch.core.record.StringRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import za.co.mwongho.span.domain.Match;
import za.co.mwongho.span.domain.Team;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LineTokenizer implements RecordMapper<StringRecord, Record<Match>> {

    private static Logger logger = LoggerFactory.getLogger(LineTokenizer.class);

    public Record<Match> processRecord(StringRecord record) {
        String payload = record.getPayload();

        List<Team> matchTeams = new ArrayList<>();
        List<String> teams = Arrays.asList(payload.split(","));
        logger.info("teams :"+teams);
        Team homeTeam = processTeam(teams.get(0));
        Team awayTeam = processTeam(teams.get(1));

        Match match = new Match(homeTeam, awayTeam);

        return new GenericRecord<>(record.getHeader(), match);
    }

    private Team processTeam(final String teamRecord) {
        String formattedRecord = teamRecord.trim();
        int index = formattedRecord.lastIndexOf(" ");
        String score = formattedRecord.substring(index).trim();
        logger.info("score :"+score);
        String teamName = formattedRecord.substring(0, index).trim();
        logger.info("teamName :"+teamName);
        Team team = new Team(teamName, Integer.parseInt(score));
        return team;
    }

}
