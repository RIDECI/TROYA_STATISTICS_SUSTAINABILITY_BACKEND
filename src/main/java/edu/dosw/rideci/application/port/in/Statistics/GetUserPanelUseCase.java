package edu.dosw.rideci.application.port.in.Statistics;

import edu.dosw.rideci.domain.model.UserStats;

public interface GetUserPanelUseCase {
    UserStats getUserStats(Long userId);
}
