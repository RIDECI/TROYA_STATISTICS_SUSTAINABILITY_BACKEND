package edu.dosw.rideci.application.port.out;

import edu.dosw.rideci.application.events.TravelCompletedEvent;
import edu.dosw.rideci.domain.model.*;
import edu.dosw.rideci.domain.model.enums.ReportFormat;
import edu.dosw.rideci.domain.model.enums.UserStatField;

import java.util.*;

public interface PortStatisticsRepository {
    List<CommunityStats> getGeneralPanel(int year);
    byte[] generateReport(ReportCriteria criteria, ReportFormat reportFormat);
    UserStats getUserStats(Long userId);
    Map<UserStatField, Object> getFilterStats(long id, List<UserStatField> fields);
    DestinationStats getDestinationStats(String name);

    EmissionRecord getEmissionRecord(Long userId, TravelCompletedEvent event);
    void updateCommunityStats(int co2Saved, TravelCompletedEvent event);
    void updateUserStats(EmissionRecord emissionRecord, TravelCompletedEvent event);
}
