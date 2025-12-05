package edu.dosw.rideci.application.port.out;

import edu.dosw.rideci.domain.model.Report;
import edu.dosw.rideci.domain.model.ReportCriteria;

import java.util.List;

public interface ReportGenerator {
    byte[] generate(Report r) throws Exception;
}
