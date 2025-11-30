package edu.dosw.rideci.application.port.in;

import edu.dosw.rideci.domain.model.EmissionRecord;

public interface RecordEmissionUseCase {
    void recordEmission(EmissionRecord emissionRecord);
}
