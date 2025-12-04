package edu.dosw.rideci.application.port.in.Statistics;

import edu.dosw.rideci.application.events.TravelCompletedEvent;

public interface UpdateCommunityStatsUseCase {
    void updateCommunityStats(int co2Saved, TravelCompletedEvent event);
}
