package edu.dosw.rideci.application.port.out;

import edu.dosw.rideci.domain.model.EmissionRecord;

public interface PortEmissionRecordRepository {
    EmissionRecord save(EmissionRecord record);
}
