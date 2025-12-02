package edu.dosw.rideci.application.port.out;

import edu.dosw.rideci.domain.model.ReportCriteria;

public interface PortSustainabilityRepository {
    byte[] generatePdfReport(ReportCriteria criteria);
    byte[] generateExcelReport(ReportCriteria criteria);
}

