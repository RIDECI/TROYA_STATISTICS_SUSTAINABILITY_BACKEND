package edu.dosw.rideci.infraestructure.persistence;

import edu.dosw.rideci.application.port.out.PortSustainabilityRepository;
import edu.dosw.rideci.domain.model.CommunityStats;
import edu.dosw.rideci.domain.model.EmissionRecord;
import edu.dosw.rideci.domain.model.UserStats;
import edu.dosw.rideci.infraestructure.StatsInfraService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class portSustainabilityRepositoryAdapter implements PortSustainabilityRepository {


    private final StatsInfraService statsInfraService;


    @Override
    public UserStats getUserStats(long userId) {
        return statsInfraService.getUserStats(userId);
    }

    @Override
    public List<CommunityStats> getCommunityStats(int year) {
        return statsInfraService.getCommunityStatsByYear(year);
    }

    @Override
    public EmissionRecord getEmissionRecord(long userId) {
        return null;
    }
}
