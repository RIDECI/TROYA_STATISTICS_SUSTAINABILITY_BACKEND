package edu.dosw.rideci.application.service;

import edu.dosw.rideci.application.port.in.Statistics.*;
import edu.dosw.rideci.domain.model.CommunityStats;
import edu.dosw.rideci.domain.model.ReportCriteria;
import edu.dosw.rideci.domain.model.UserStats;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService implements GenerateReportUseCase,GetCollectiveSavingsUseCase,
                        GetDestinationPanelUserCase, GetParticipationUseCase, GetPublicPanelUseCase, GetUserPanelUseCase {

    @Override
    public byte[] generateReport(ReportCriteria criteria) {
        return new byte[0];
    }

    @Override
    public CommunityStats getCommunityStats() {
        return null;
    }

    @Override
    public UserStats getUserStats(Long userId) {
        return null;
    }
}
