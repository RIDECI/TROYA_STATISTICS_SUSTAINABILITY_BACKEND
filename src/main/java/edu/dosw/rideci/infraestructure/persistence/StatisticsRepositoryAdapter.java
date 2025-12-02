package edu.dosw.rideci.infraestructure.persistence;

import edu.dosw.rideci.application.port.out.PortStatisticsRepository;
import edu.dosw.rideci.domain.model.CommunityStats;
import edu.dosw.rideci.domain.model.DestinationStats;
import edu.dosw.rideci.domain.model.ReportCriteria;
import edu.dosw.rideci.domain.model.UserStats;
import edu.dosw.rideci.domain.model.enums.UserStatField;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Repository
public class StatisticsRepositoryAdapter implements PortStatisticsRepository {


    @Override
    public CommunityStats getGeneralPanel() {
        return null;
    }

    @Override
    public byte[] generateReport(ReportCriteria criteria) {
        return new byte[0];
    }

    @Override
    public byte[] generatePDFReport(ReportCriteria criteria) {
        return new byte[0];
    }

    @Override
    public byte[] genereateEXELReport(ReportCriteria criteria) {
        return new byte[0];
    }

    @Override
    public UserStats getUserStats(Long userId) {
        return null;
    }

    @Override
    public Map<UserStatField, Object> getFilterStats(UserStats stats, List<UserStatField> fields) {
        return Map.of();
    }

    @Override
    public DestinationStats getDestinationStats(String name) {
        return null;
    }


}
