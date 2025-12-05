package edu.dosw.rideci.infraestructure;

import edu.dosw.rideci.domain.model.*;
import edu.dosw.rideci.domain.model.enums.ReportPeriod;
import edu.dosw.rideci.exceptions.InvalidYearException;
import edu.dosw.rideci.exceptions.UserNotFoundException;
import edu.dosw.rideci.infraestructure.persistence.entity.CommunityStatsDocument;
import edu.dosw.rideci.infraestructure.persistence.entity.DestinationsStatsDocument;
import edu.dosw.rideci.infraestructure.persistence.entity.UserStatsDocument;
import edu.dosw.rideci.infraestructure.persistence.entity.EmissionRecordDocument;

import edu.dosw.rideci.infraestructure.persistence.repository.Stats.CommunityStats.CommunityStatsRepository;
import edu.dosw.rideci.infraestructure.persistence.repository.Stats.DestinationsStats.DestinationStatsRepository;
import edu.dosw.rideci.infraestructure.persistence.repository.Stats.UserStats.UserStatsRepository;
import edu.dosw.rideci.infraestructure.persistence.repository.Sustainability.EmissionRecords.EmissionRecordRepository;
import edu.dosw.rideci.infraestructure.persistence.repository.mapper.CommunityStatsMapper;
import edu.dosw.rideci.infraestructure.persistence.repository.mapper.DestinationStatsMapper;
import edu.dosw.rideci.infraestructure.persistence.repository.mapper.EmissionRecordMapper;
import edu.dosw.rideci.infraestructure.persistence.repository.mapper.UserStatsMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
import java.util.Collections;
import java.util.List;

import static edu.dosw.rideci.domain.model.enums.ReportPeriod.MONTHLY;
import static edu.dosw.rideci.domain.model.enums.ReportPeriod.QUARTERLY;

@Repository
@AllArgsConstructor
public class StatsInfraService {

    private final CommunityStatsRepository communityStatsRepository;
    private final UserStatsRepository userStatsRepository;
    private final CommunityStatsMapper communityStatsMapper;
    private final UserStatsMapper userStatsMapper;
    private final DestinationStatsRepository destinationStatsRepository;
    private final DestinationStatsMapper destinationStatsMapper;
    private final EmissionRecordMapper  emissionRecordMapper;
    private final EmissionRecordRepository emissionRecordRepository;

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
        UserStatsDocument u = userStatsRepository.findByUserId(userId);
        if (u == null) {
            throw new UserNotFoundException("User not found");
        }
        return userStatsMapper.toDomain(u);
    }


    public List<CommunityStats> getCommunityStatsByPeriod(ReportPeriod period) {
        LocalDate now = LocalDate.now();
        List<CommunityStatsDocument> statsDocuments;

        switch (period) {
            case WEEKLY:
                //  semana
                LocalDate weekAgo = now.minusWeeks(1);
                statsDocuments = communityStatsRepository.findByYearAndMonth(
                        now.getYear(),
                        now.getMonthValue()
                );
                break;

            case MONTHLY:
                //  mes
                statsDocuments = communityStatsRepository.findByYearAndMonth(
                        now.getYear(),
                        now.getMonthValue()
                );
                break;

            case QUARTERLY:
                // trimestr
                int currentQuarter = (now.getMonthValue() - 1) / 3 + 1;
                int startMonth = (currentQuarter - 1) * 3 + 1;
                int endMonth = startMonth + 2;

                statsDocuments = communityStatsRepository.findByYearAndMonthBetween(
                        now.getYear(),
                        startMonth,
                        endMonth
                );
                break;

            case SEMESTER:
                // semestre
                int currentSemester = (now.getMonthValue() - 1) / 6 + 1;
                int semesterStartMonth = (currentSemester - 1) * 6 + 1;
                int semesterEndMonth = semesterStartMonth + 5;

                statsDocuments = communityStatsRepository.findByYearAndMonthBetween(
                        now.getYear(),
                        semesterStartMonth,
                        semesterEndMonth
                );
                break;

            default:
                throw new IllegalArgumentException("Invalid period: " + period);
        }

        if (statsDocuments.isEmpty()) {
            return Collections.emptyList();
        }

        return communityStatsMapper.toDomainList(statsDocuments);
    }


    public List<DestinationStats> getDestinationStats(Long userId, ReportPeriod period) {

        List<DestinationsStatsDocument> statsDocuments = destinationStatsRepository.findAll();

        if (statsDocuments.isEmpty()) {
            return Collections.emptyList();
        }

        return destinationStatsMapper.toDomainList(statsDocuments);
    }


    public List<EmissionRecord> getEmissionRecords(ReportCriteria criteria) {
        Long userId = criteria.getUserId();
        ReportPeriod period = criteria.getPeriod();

        LocalDate now = LocalDate.now();
        LocalDate startDate;

        switch (period) {
            case WEEKLY:
                startDate = now.minusWeeks(1);
                break;
            case MONTHLY:
                startDate = now.minusMonths(1);
                break;
            case QUARTERLY:
                startDate = now.minusMonths(3);
                break;
            case SEMESTER:
                startDate = now.minusMonths(6);
                break;
            default:
                throw new IllegalArgumentException("Invalid period: " + period);
        }

        List<EmissionRecordDocument> recordDocuments =
                emissionRecordRepository.findByUserIdAndDateBetween(
                        userId,
                        startDate,
                        now
                );

        if (recordDocuments.isEmpty()) {
            return Collections.emptyList();
        }

        return emissionRecordMapper.toDomainList(recordDocuments);
    }
}
