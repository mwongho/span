package za.co.mwongho.span.batch;

import org.jeasy.batch.core.processor.RecordProcessor;
import org.jeasy.batch.core.record.GenericRecord;
import org.jeasy.batch.core.record.Record;
import za.co.mwongho.span.domain.TeamStanding;

public class StandingsProcessor implements RecordProcessor<Record, Record> {
    @Override
    public Record<String> processRecord(Record record) throws Exception {
        StringBuilder sb = new StringBuilder();
        TeamStanding teamStanding = (TeamStanding) record.getPayload();
        sb.append(teamStanding.getName()).append(", ");
        sb.append(teamStanding.getPoints());
        if(teamStanding.getPoints() == 1){
            sb.append(" pt");
        } else {
            sb.append(" pts");
        }
        return new GenericRecord<>(record.getHeader(), sb.toString());
    }

}
