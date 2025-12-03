package edu.dosw.rideci.application.service;

import edu.dosw.rideci.application.mapper.InitialReportMapper;
import edu.dosw.rideci.application.port.in.Statistics.*;
import edu.dosw.rideci.application.port.out.PortStatisticsRepository;
import edu.dosw.rideci.domain.model.CommunityStats;
import edu.dosw.rideci.domain.model.DestinationStats;
import edu.dosw.rideci.domain.model.ReportCriteria;
import edu.dosw.rideci.domain.model.UserStats;
import edu.dosw.rideci.domain.model.enums.UserStatField;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService implements GenerateReportUseCase,
        GetFilteredUserStatsUseCase, GetCommunityStatslUseCase, GetUserPanelUseCase, GetDestinationPanelUserCase{

    private final PortStatisticsRepository portStatisticsRepository;



    @Override
    public byte[] generateReport(ReportCriteria criteria) {
        return portStatisticsRepository.generateReport(criteria);

    }

    @Override
    public byte[] generatePDFReport(ReportCriteria criteria) {
        return portStatisticsRepository.generatePDFReport(criteria);
    }

    @Override
    public byte[] generateEXELReport(ReportCriteria criteria) {
        return portStatisticsRepository.genereateEXELReport(criteria);
    }


    @Override
    public UserStats getUserStats(Long userId) {
        return portStatisticsRepository.getUserStats(userId);
    }

    @Override
    public Map<UserStatField, Object> filterStats(UserStats stats, List<UserStatField> fields) {
        return portStatisticsRepository.getFilterStats(stats, fields);
    }

    @Override
    public List<CommunityStats> getCommunityStats(int year) {
        return portStatisticsRepository.getGeneralPanel(year);
    }

    @Override
    public DestinationStats getDestinationStats(String name) {
        return portStatisticsRepository.getDestinationStats(name);
    }


}
