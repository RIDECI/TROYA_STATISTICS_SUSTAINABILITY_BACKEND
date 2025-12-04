package edu.dosw.rideci.infraestructure.persistence;

import edu.dosw.rideci.application.events.TravelCompletedEvent;
import edu.dosw.rideci.application.port.out.PortStatisticsRepository;
import edu.dosw.rideci.domain.model.*;
import edu.dosw.rideci.domain.model.enums.UserStatField;
import edu.dosw.rideci.exceptions.UserNotFoundException;
import edu.dosw.rideci.infraestructure.StatsInfraService;
import edu.dosw.rideci.infraestructure.persistence.entity.CommunityStatsDocument;
import edu.dosw.rideci.infraestructure.persistence.entity.DestinationsStatsDocument;
import edu.dosw.rideci.infraestructure.persistence.entity.UserStatsDocument;
import edu.dosw.rideci.infraestructure.persistence.repository.Stats.CommunityStats.CommunityStatsRepository;
import edu.dosw.rideci.infraestructure.persistence.repository.Stats.DestinationsStats.DestinationStatsRepository;
import edu.dosw.rideci.infraestructure.persistence.repository.Stats.UserStats.UserStatsRepository;
import edu.dosw.rideci.infraestructure.persistence.repository.mapper.CommunityStatsMapper;
import edu.dosw.rideci.infraestructure.persistence.repository.mapper.DestinationStatsMapper;
import edu.dosw.rideci.infraestructure.persistence.repository.mapper.UserStatsMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Date;
import java.util.HashMap;
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

    @Override
    public EmissionRecord getEmissionRecord(Long userId, TravelCompletedEvent event) {
        return null;
    }

    @Override
    public void updateCommunityStats(double co2Saved, TravelCompletedEvent event) {
        int currentYear = Year.now().getValue();
        CommunityStatsDocument lastMonthStats = communityStatsRepository.findFirstByYearOrderByMonthDesc(currentYear);

        int t = lastMonthStats.getTotalSharedTrips();
        lastMonthStats.setTotalSharedTrips(t + 1);
        //toca lo de ver cantidad de perfiles para saber si cambio o no

        communityStatsRepository.save(lastMonthStats);


    }

    @Override
    public void updateUserStats(EmissionRecord emissionRecord, TravelCompletedEvent event) {
        UserStatsDocument uDoc = userStatsRepository.findById(emissionRecord.getUserId()).orElseThrow(()-> new UserNotFoundException("user not found"));
        int t = uDoc.getTotalTrips();
        double d = uDoc.getTotalCO2Saved();
        Date date = event.getDepartureDateAndTime();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String hour = sdf.format(date);
        String typeKey = emissionRecord.getUserRol().name();


        uDoc.setTotalTrips(t + 1);
        uDoc.setTotalCO2Saved(d + emissionRecord.getTotalCO2Saved());

        Map<String, Integer> freq = uDoc.getFrequentDepartureTime();
        if (freq == null) {
            freq = new HashMap<>();
            uDoc.setFrequentDepartureTime(freq);
        }

        freq.merge(hour, 1, Integer::sum);

        if (uDoc.getUserTypeCount() == null) {
            uDoc.setUserTypeCount(new HashMap<>());
        }

        uDoc.getUserTypeCount().merge(typeKey, 1, Integer::sum);


        userStatsRepository.save(uDoc);

    }


    //falta el de geo y el de pagos para el de usuarios


}
