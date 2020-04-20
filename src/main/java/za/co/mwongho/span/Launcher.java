package za.co.mwongho.span;

import org.jeasy.batch.core.job.Job;
import org.jeasy.batch.core.job.JobBuilder;
import org.jeasy.batch.core.job.JobExecutor;
import org.jeasy.batch.core.reader.StreamRecordReader;
import org.jeasy.batch.core.writer.FileRecordWriter;
import org.jeasy.batch.flatfile.FlatFileRecordReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import za.co.mwongho.span.batch.LineTokenizer;
import za.co.mwongho.span.batch.MatchProcessor;
import za.co.mwongho.span.batch.StandingsProcessor;
import za.co.mwongho.span.domain.TeamStanding;
import za.co.mwongho.span.service.StandingService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Launcher {
    private static Logger logger = LoggerFactory.getLogger(Launcher.class);

    private static final String DEFAULT_INPUT_FILE_PATH = "src/main/resources/matches.csv";
    private static final String DEFAULT_OUTPUT_FILE_PATH = "target/standings.csv";

    public static void main(String[] args) throws Exception {

        List<String> paths = Arrays.asList(args);

        Path matches;
        Path standings;

        if(paths.isEmpty()){
            matches = Paths.get(DEFAULT_INPUT_FILE_PATH);
            standings = Paths.get(DEFAULT_OUTPUT_FILE_PATH);
        } else {
            matches = Paths.get(paths.get(0));
            if(!Files.exists(matches)) {
                logger.warn("matches file does not exist, using default file");
                matches = Paths.get(DEFAULT_INPUT_FILE_PATH);
            }
            if(paths.size() == 1){
                standings = Paths.get(DEFAULT_OUTPUT_FILE_PATH);
            } else {
                standings = Paths.get(paths.get(1));
            }
        }
        logger.info("matches :"+matches);
        logger.info("standings :"+standings);
        executeMatchJob(matches);
        executeStandingJob(standings);
    }

    private static void executeMatchJob(Path filePath) {
        // Build a batch job
        MatchProcessor matchProcessor = new MatchProcessor();
        Job job1 = JobBuilder.aNewJob()
                .batchSize(2)
                .reader(new FlatFileRecordReader(filePath))
                .mapper(new LineTokenizer())
                .processor(matchProcessor)
                .build();

        // Execute the job
        JobExecutor jobExecutor1 = new JobExecutor();
        jobExecutor1.execute(job1);
        jobExecutor1.shutdown();
    }

    private static void executeStandingJob(Path filePath) {
        List<TeamStanding> teamStandings = StandingService.INSTANCE.getInstance().getOrderedTeamStanding();
        logger.info("teamStandings :"+teamStandings);
        Job job2 = JobBuilder.aNewJob()
                .batchSize(1)
                .reader(new StreamRecordReader<TeamStanding>(teamStandings.stream()))
                .processor(new StandingsProcessor())
                .writer(new FileRecordWriter(filePath))
                .build();

        JobExecutor jobExecutor2 = new JobExecutor();
        jobExecutor2.execute(job2);
        jobExecutor2.shutdown();
    }

}
