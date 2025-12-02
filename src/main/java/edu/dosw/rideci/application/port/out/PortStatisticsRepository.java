package edu.dosw.rideci.application.port.out;

import edu.dosw.rideci.domain.model.CommunityStats;
import edu.dosw.rideci.domain.model.DestinationStats;
import edu.dosw.rideci.domain.model.ReportCriteria;
import edu.dosw.rideci.domain.model.UserStats;
import edu.dosw.rideci.domain.model.enums.UserStatField;

import java.util.*;

public interface PortStatisticsRepository {
    CommunityStats getGeneralPanel();
    byte[] generateReport(ReportCriteria criteria);
    byte[] generatePDFReport(ReportCriteria criteria);
    byte[] genereateEXELReport(ReportCriteria criteria);
    UserStats getUserStats(Long userId);
    Map<UserStatField, Object> getFilterStats(UserStats stats, List<UserStatField> fields);
    DestinationStats getDestinationStats(String name);
}
