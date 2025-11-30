package edu.dosw.rideci.application.port.out;

import edu.dosw.rideci.domain.model.UserStats;

public interface PortUserStatsRepository {
    UserStats findByUserId(Long userId);
    UserStats save(UserStats userStats);
}
