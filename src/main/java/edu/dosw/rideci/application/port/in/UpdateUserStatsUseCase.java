package edu.dosw.rideci.application.port.in;

import edu.dosw.rideci.domain.model.EmissionRecord;
import edu.dosw.rideci.domain.model.UserStats;

public interface UpdateUserStatsUseCase {
    UserStats updateUserStats(EmissionRecord emissionRecord);
}

