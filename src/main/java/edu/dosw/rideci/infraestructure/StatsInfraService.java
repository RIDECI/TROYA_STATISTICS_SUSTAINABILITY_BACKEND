package edu.dosw.rideci.infraestructure;

import edu.dosw.rideci.domain.model.CommunityStats;
import edu.dosw.rideci.domain.model.UserStats;
import edu.dosw.rideci.exceptions.InvalidYearException;
import edu.dosw.rideci.exceptions.UserNotFoundException;
import edu.dosw.rideci.infraestructure.persistence.entity.CommunityStatsDocument;
import edu.dosw.rideci.infraestructure.persistence.entity.UserStatsDocument;
import edu.dosw.rideci.infraestructure.persistence.repository.Stats.CommunityStats.CommunityStatsRepository;
import edu.dosw.rideci.infraestructure.persistence.repository.Stats.UserStats.UserStatsRepository;
import edu.dosw.rideci.infraestructure.persistence.repository.mapper.CommunityStatsMapper;
import edu.dosw.rideci.infraestructure.persistence.repository.mapper.UserStatsMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.Collections;
import java.util.List;

@Repository
@AllArgsConstructor
public class StatsInfraService {

    private final CommunityStatsRepository communityStatsRepository;
    private final UserStatsRepository userStatsRepository;
    private final CommunityStatsMapper communityStatsMapper;
    private final UserStatsMapper userStatsMapper;

    public List<CommunityStats> getCommunityStatsByYear(int year) {
        List<CommunityStatsDocument> stats = communityStatsRepository.findByYear(year);

        int currentYear = Year.now().getValue();

        if(year > currentYear ){
            throw new InvalidYearException("The year provided is invalid");
        }


        if (stats.isEmpty()) {
            CommunityStatsDocument newStats = CommunityStatsDocument.builder()
                    .year(year)
                    .month(1)
                    .totalSharedTrips(0)
                    .totalActiveUsers(Collections.emptyMap())
                    .build();

            communityStatsRepository.save(newStats);
            stats.add(newStats);
        }

        return communityStatsMapper.toDomainList(stats);
    }


    public UserStats getUserStats(Long userId) {
        UserStatsDocument u = userStatsRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return userStatsMapper.toDomain(u);
    }

}
