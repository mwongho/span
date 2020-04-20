package za.co.mwongho.span.batch;

import org.jeasy.batch.core.processor.RecordProcessor;
import org.jeasy.batch.core.record.GenericRecord;
import org.jeasy.batch.core.record.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import za.co.mwongho.span.domain.Match;
import za.co.mwongho.span.domain.MatchResult;
import za.co.mwongho.span.service.StandingService;

public class MatchProcessor implements RecordProcessor<Record, Record> {

    private static Logger logger = LoggerFactory.getLogger(MatchProcessor.class);

    @SuppressWarnings("unchecked")
    public Record<MatchResult> processRecord(Record record) {
        Match match = (Match) record.getPayload();
        logger.info("match :"+match);
        // Apply rule
        MatchResult matchResult = new MatchResult(match);
        logger.info("matchResult :"+matchResult);
        StandingService.INSTANCE.getInstance().addMatchResult(matchResult);
        //
        return new GenericRecord<>(record.getHeader(), matchResult);
    }
}