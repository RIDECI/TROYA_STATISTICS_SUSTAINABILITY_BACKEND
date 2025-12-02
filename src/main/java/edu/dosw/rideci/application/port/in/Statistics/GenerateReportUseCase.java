package edu.dosw.rideci.application.port.in.Statistics;

import edu.dosw.rideci.domain.model.ReportCriteria;

public interface GenerateReportUseCase {
    byte[] generateReport(ReportCriteria criteria);
}

