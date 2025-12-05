package edu.dosw.rideci.application.service;

import edu.dosw.rideci.application.events.TravelCompletedEvent;
import edu.dosw.rideci.application.port.in.Statistics.*;
import edu.dosw.rideci.application.port.out.PortStatisticsRepository;
import edu.dosw.rideci.domain.model.*;
import edu.dosw.rideci.domain.model.enums.ReportFormat;
import edu.dosw.rideci.domain.model.enums.UserStatField;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService implements GenerateReportUseCase,
        GetFilteredUserStatsUseCase, GetCommunityStatslUseCase, GetUserPanelUseCase, GetDestinationPanelUserCase,
        GenerateEmissionRecordUseCase, UpdateCommunityStatsUseCase, UpdateUserStatsUseCase {

    private final PortStatisticsRepository portStatisticsRepository;


    @Override
    public byte[] generateReport(ReportCriteria criteria, ReportFormat reportFormat) {
        return portStatisticsRepository.generateReport(criteria,reportFormat);

    }



    @Override
    public UserStats getUserStats(Long userId) {
        return portStatisticsRepository.getUserStats(userId);
    }

    @Override
    public Map<UserStatField, Object> filterStats(long userId, List<UserStatField> fields) {
        return portStatisticsRepository.getFilterStats(userId, fields);
    }

    @Override
    public List<CommunityStats> getCommunityStats(int year) {
        return portStatisticsRepository.getGeneralPanel(year);
    }

    @Override
    public DestinationStats getDestinationStats(String name) {
        return portStatisticsRepository.getDestinationStats(name);
    }


    @Override
    public EmissionRecord generateEmissionRecord(long userId, TravelCompletedEvent event) {
        return  portStatisticsRepository.getEmissionRecord(userId, event);
    }

    @Override
    public void updateCommunityStats(int co2Saved, TravelCompletedEvent event){
        portStatisticsRepository.updateCommunityStats(co2Saved, event);
    }

    @Override
    public void updateFromTravel(EmissionRecord emissionRecord, TravelCompletedEvent event) {
            portStatisticsRepository.updateUserStats(emissionRecord, event);
    }
}
