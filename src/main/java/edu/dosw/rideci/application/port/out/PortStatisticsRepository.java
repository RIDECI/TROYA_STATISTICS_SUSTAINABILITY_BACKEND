package edu.dosw.rideci.application.port.out;

import edu.dosw.rideci.domain.model.DestinationStats;
import edu.dosw.rideci.domain.model.ReportCriteria;
import edu.dosw.rideci.domain.model.UserStats;

public interface PortStatisticsRepository {
    UserStats getByUserId(Long userId);
    Byte[] generateReport(ReportCriteria criteria);
    DestinationStats getDestinationStats(String name);

}
