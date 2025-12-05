package edu.dosw.rideci.application.port.in.Statistics;

import edu.dosw.rideci.domain.model.ReportCriteria;
import edu.dosw.rideci.domain.model.enums.ReportFormat;
import edu.dosw.rideci.infraestructure.controller.dto.request.ReportCriteriaRequestDTO;

public interface GenerateReportUseCase {
    byte[] generateReport(ReportCriteria criteria, ReportFormat  format);

}

