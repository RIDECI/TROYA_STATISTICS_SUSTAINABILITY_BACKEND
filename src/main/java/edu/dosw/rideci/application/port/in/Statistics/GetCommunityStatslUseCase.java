package edu.dosw.rideci.application.port.in.Statistics;

import edu.dosw.rideci.domain.model.CommunityStats;

import java.util.List;

public interface GetCommunityStatslUseCase {
    List<CommunityStats> getCommunityStats(int year);
}
