package edu.dosw.rideci.infraestructure.persistence;

import edu.dosw.rideci.application.port.out.PortStatisticsRepository;
import edu.dosw.rideci.domain.model.CommunityStats;
import edu.dosw.rideci.domain.model.DestinationStats;
import edu.dosw.rideci.domain.model.ReportCriteria;
import edu.dosw.rideci.domain.model.UserStats;
import edu.dosw.rideci.domain.model.enums.UserStatField;
import edu.dosw.rideci.infraestructure.StatsInfraService;
import edu.dosw.rideci.infraestructure.persistence.entity.DestinationsStatsDocument;
import edu.dosw.rideci.infraestructure.persistence.repository.Stats.CommunityStats.CommunityStatsRepository;
import edu.dosw.rideci.infraestructure.persistence.repository.Stats.DestinationsStats.DestinationStatsRepository;
import edu.dosw.rideci.infraestructure.persistence.repository.Stats.UserStats.UserStatsRepository;
import edu.dosw.rideci.infraestructure.persistence.repository.mapper.CommunityStatsMapper;
import edu.dosw.rideci.infraestructure.persistence.repository.mapper.DestinationStatsMapper;
import edu.dosw.rideci.infraestructure.persistence.repository.mapper.UserStatsMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class portStatisticsRepositoryAdapter implements PortStatisticsRepository {

    private final CommunityStatsRepository communityStatsRepository;
    private final UserStatsRepository userStatsRepository;
    private final DestinationStatsRepository destinationStatsRepository;
    private final CommunityStatsMapper communityStatsMapper;
    private final UserStatsMapper userStatsMapper;
    private final DestinationStatsMapper destinationStatsMapper;


    private final StatsInfraService statsInfraService;

    @Override
    public List<CommunityStats> getGeneralPanel(int year) {
        return statsInfraService.getCommunityStatsByYear(year);
    }

    @Override
    public UserStats getUserStats(Long userId) {
        return statsInfraService.getUserStats(userId);
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
    public Map<UserStatField, Object> getFilterStats(UserStats stats, List<UserStatField> fields) {
        return Map.of();
    }

    @Override
    public DestinationStats getDestinationStats(String name) {
        DestinationsStatsDocument d = destinationStatsRepository.findByName(name);
        return destinationStatsMapper.toDomain(d);
    }


}
