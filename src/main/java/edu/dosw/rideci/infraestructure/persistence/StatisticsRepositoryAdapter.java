package edu.dosw.rideci.infraestructure.persistence;

import edu.dosw.rideci.application.port.out.PortStatisticsRepository;
import edu.dosw.rideci.domain.model.DestinationStats;
import edu.dosw.rideci.domain.model.ReportCriteria;
import edu.dosw.rideci.domain.model.UserStats;

public class StatisticsRepositoryAdapter implements PortStatisticsRepository {
    @Override
    public UserStats getByUserId(Long userId) {
        return null;
    }

    @Override
    public Byte[] generateReport(ReportCriteria criteria) {
        return new Byte[0];
    }

    @Override
    public DestinationStats getDestinationStats(String name) {
        return null;
    }
}
