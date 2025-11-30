package edu.dosw.rideci.application.port.out;

import edu.dosw.rideci.domain.model.CommunityStats;

public interface PortCommunityStatsRepository {
    CommunityStats getCommunityStats();
    CommunityStats save(CommunityStats communityStats);
}

