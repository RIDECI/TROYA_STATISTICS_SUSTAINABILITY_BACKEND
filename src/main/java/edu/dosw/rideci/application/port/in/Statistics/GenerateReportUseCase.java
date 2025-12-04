package edu.dosw.rideci.application.port.in.Statistics;

import edu.dosw.rideci.domain.model.ReportCriteria;
import edu.dosw.rideci.infraestructure.controller.dto.request.ReportCriteriaRequestDTO;

public interface GenerateReportUseCase {
    byte[] generateReport(ReportCriteria criteria);
    byte[] generatePDFReport(ReportCriteria criteria);
    byte[] generateEXELReport(ReportCriteria criteria);
}

