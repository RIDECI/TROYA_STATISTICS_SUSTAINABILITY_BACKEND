package edu.dosw.rideci.application.port.in;

import edu.dosw.rideci.domain.model.UserStats;

public interface GetUserStatsUseCase {
    UserStats getUserStats(Long userId);
}
