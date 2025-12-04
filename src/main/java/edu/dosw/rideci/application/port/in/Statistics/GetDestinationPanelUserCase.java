package edu.dosw.rideci.application.port.in.Statistics;

import edu.dosw.rideci.domain.model.DestinationStats;

public interface GetDestinationPanelUserCase {
    DestinationStats getDestinationStats(String name);
}
