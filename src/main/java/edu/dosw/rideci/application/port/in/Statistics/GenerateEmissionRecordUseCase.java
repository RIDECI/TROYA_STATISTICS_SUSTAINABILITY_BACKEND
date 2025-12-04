package edu.dosw.rideci.application.port.in.Statistics;

import edu.dosw.rideci.application.events.TravelCompletedEvent;
import edu.dosw.rideci.domain.model.EmissionRecord;

public interface GenerateEmissionRecordUseCase {
    EmissionRecord generateEmissionRecord(long userId, TravelCompletedEvent event);
}
