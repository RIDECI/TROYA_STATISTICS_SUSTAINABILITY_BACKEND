package edu.dosw.rideci.application.port.in;

import edu.dosw.rideci.domain.model.ReportCriteria;

public interface GenerateReportUseCase {
    byte[] generateReport(ReportCriteria criteria);
}

