package edu.dosw.rideci.application.port.out;

public interface PortUserStatsRepository {
    void addCo2Saved(Long userId, double amountKg);
}

