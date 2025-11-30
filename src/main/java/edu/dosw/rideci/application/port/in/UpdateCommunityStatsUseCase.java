package edu.dosw.rideci.application.port.in;

import edu.dosw.rideci.domain.model.EmissionRecord;
import edu.dosw.rideci.domain.model.CommunityStats;

public interface UpdateCommunityStatsUseCase {
    CommunityStats updateCommunityStats(EmissionRecord emissionRecord);
}

