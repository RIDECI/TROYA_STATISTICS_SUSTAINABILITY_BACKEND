package edu.dosw.rideci.application.port.out;

import edu.dosw.rideci.domain.model.CommunityStats;
import edu.dosw.rideci.domain.model.EmissionRecord;
import edu.dosw.rideci.domain.model.ReportCriteria;
import edu.dosw.rideci.domain.model.UserStats;

import java.util.List;

public interface PortSustainabilityRepository {
    UserStats getUserStats(long userId);
    List<CommunityStats> getCommunityStats(int year);
    EmissionRecord getEmissionRecord(long userId);
}

